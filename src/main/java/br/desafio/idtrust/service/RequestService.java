package br.desafio.idtrust.service;

import br.desafio.idtrust.config.Constants;
import br.desafio.idtrust.entity.Request;
import br.desafio.idtrust.entity.enumeration.Culture;
import br.desafio.idtrust.entity.typesafe.CotacaoCEPEA;
import br.desafio.idtrust.entity.typesafe.CotacaoMoeda;
import br.desafio.idtrust.repository.RequestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestService.class);

    private final Environment env;

    private final RequestRepository requestRepository;

    private final ObjectWriter objectWriter;

    public RequestService(RequestRepository requestRepository, Environment env) {
        this.requestRepository = requestRepository;
        this.env = env;
        this.objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    private CotacaoMoeda[] queryCotacaoMoeda(LocalDate dataCotacao) {
        String dataCotacaoStr = dataCotacao.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return new RestTemplate().getForObject(Constants.COTACAO_USD_BLR_API_URL, CotacaoMoeda[].class, dataCotacaoStr,
                dataCotacaoStr);
    }

    private CotacaoCEPEA queryCotacaoCEPEA(Culture culture, LocalDate dataCotacao) {
        return new RestTemplate().getForObject(Constants.COTACAO_CEPEA_API_URL, CotacaoCEPEA.class, culture,
                dataCotacao, dataCotacao, env.getProperty(Constants.ENV_QUANDL_API_KEY));
    }

    /**
     * Função que ira verificar se a cotação ja foi calculada anteriormente, para não ter que fazer a requisição nas APIs
     *
     * @param culture     A {@link br.desafio.idtrust.entity.enumeration.Culture} usada na requisição.
     * @param dataCotacao A data da cotação usada na requisição.
     * @return O valor da cotação no caso de esta já existir no banco de dados, null em caso contrario
     */
    @Transactional(readOnly = true)
    public BigDecimal findInDatabaseCache(Culture culture, LocalDate dataCotacao) {
        return requestRepository.findOneByCultureAndDate(culture, dataCotacao)
                .map(Request::getValor)
                .orElse(null);
    }


    /**
     * Função que ira a calcular a cotação de uma {@link br.desafio.idtrust.entity.enumeration.Culture} na data
     * espeficicada, primeiramente vai ser pesquisado no banco de dados se já foi calculada essa cultura nessa data,
     * isso para não ter que requisitar as APIs e ganhar em tempo e recursos. No caso de não ter sido pesquisada antes,
     * então ira fazer as requisições as APIs.
     *
     * @param culture A cultura a ser requisitada na API da CEPEA
     * @param dataCotacao A data da cotação
     * @return O valor da cotação expresado em BRL
     */
    @Transactional
    public BigDecimal findCotacao(Culture culture, LocalDate dataCotacao) {
        BigDecimal databaseValorCache = findInDatabaseCache(culture, dataCotacao);

        // Se o resultado não foi calculado antes, então fazer as consultas nas APIs para calcular a cotação.
        if (databaseValorCache == null) {
            CotacaoMoeda[] cotacaoMoedas = this.queryCotacaoMoeda(dataCotacao);
            CotacaoCEPEA cotacaoCEPEA = this.queryCotacaoCEPEA(culture, dataCotacao);

            //Salvar a resposta no banco de dados para consultas futuras
            Request request = new Request();
            request.setCulture(culture);
            request.setDate(dataCotacao);
            try {
                request.setJsonResponseMoeda(objectWriter.writeValueAsString(cotacaoMoedas));
                request.setJsonResponseCEPEA(objectWriter.writeValueAsString(cotacaoCEPEA));
            } catch (JsonProcessingException e) {
                log.error("Ouve um erro no parseamento de um objeto: {}", e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ouve um erro interno no servidor");
            }

            String[][] dataCEPEA = cotacaoCEPEA.getDataset().getData();
            if (cotacaoMoedas.length == 0 || dataCEPEA.length == 0) {
                request.setValor(BigDecimal.ZERO);
                requestRepository.save(request);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existem dados suficientes para fazer o " +
                        "calculo da cotação");
            } else {
                BigDecimal precoVendaUSDBRL = cotacaoMoedas[0].getAsk();
                //A primeira coluna possui o preço da cultura em USD
                BigDecimal preco = BigDecimal.valueOf(Double.parseDouble(dataCEPEA[0][1]));
                BigDecimal valorCotacao = preco.multiply(precoVendaUSDBRL);
                request.setValor(valorCotacao);
                requestRepository.save(request);
                return valorCotacao;
            }
        }
        /*
         * Pesquisar se a cotação já foi requisitada antes para retornar o mesmo valor
         *
         * Se o valor salvo for 0, então não há dados suficientes para calcular a cotação
         * */
        else if (databaseValorCache.equals(BigDecimal.ZERO)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existem dados suficientes para fazer o " +
                    "calculo da cotação");
        } else {
            // Retorna o valor salvo no banco.
            return databaseValorCache;
        }
    }

}
