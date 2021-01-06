package br.desafio.idtrust.rest;

import br.desafio.idtrust.IdtrustApplication;
import br.desafio.idtrust.entity.Request;
import br.desafio.idtrust.entity.enumeration.Culture;
import br.desafio.idtrust.entity.enumeration.Currency;
import br.desafio.idtrust.repository.RequestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = IdtrustApplication.class)
@AutoConfigureMockMvc
class IdtrustApplicationTests {

    private static final Culture DEFAULT_CULTURE = Culture.CORN;
    private static final LocalDate DEFAULT_DATE = LocalDate.parse("2020-12-23");
    private static final LocalDate DEFAULT_DATE_ZERO = LocalDate.parse("2020-12-20");
    private static final BigDecimal DEFAULT_VALOR = BigDecimal.valueOf(77.892796);
    private static final String DEFAULT_JSON_RESPONSE_MOEDA = "[ { \"code\": \"USD\", \"codein\": \"BRL\", \"name\": " +
            "\"Dólar Comercial\", \"high\": \"5.2168\", \"low\": \"5.2168\", \"varBid\": \"0.0004\", \"pctChange\":" +
            " \"0.01\", \"bid\": \"5.2164\", \"ask\": \"5.2172\", \"timestamp\": \"1608758991\", \"create_date\":" +
            " \"2020-12-23 19:00:04\" } ]";
    private static final String DEFAULT_JSON_RESPONSE_CEPAE = "{ \"dataset\": { \"id\": 8535894, \"dataset_code\": \"CORN\", " +
            "\"database_code\": \"CEPEA\", \"name\": \"Brazilian Agribusiness Price Indices - Corn\", " +
            "\"description\": \"ESALQ/BM&F CORN PRICE INDEX\", \"refreshed_at\": \"2020-12-29 23:02:43 UTC\", " +
            "\"newest_available_date\": \"2020-12-29\", \"oldest_available_date\": \"2004-08-02\", \"column_names\":" +
            " [ \"Date\", \"Cash Price US$\", \"Daily %\", \"Monthly %\" ], \"frequency\": \"daily\", \"type\": " +
            "\"Time Series\", \"premium\": false, \"limit\": null, \"transform\": null, \"column_index\": null, " +
            "\"start_date\": \"2020-12-23\", \"end_date\": \"2020-12-23\", \"data\": [ [ \"2020-12-23\", 14.93, " +
            "-0.27, 2.05 ] ], \"collapse\": null, \"order\": null, \"database_id\": 1935 } }";

    private Request request;

    @Autowired
    private MockMvc restItemMockMvc;

    @Autowired
    private RequestRepository requestRepository;

    public Request createEntity() {
        return new Request()
                .culture(DEFAULT_CULTURE)
                .date(DEFAULT_DATE)
                .jsonResponseCEPEA(DEFAULT_JSON_RESPONSE_CEPAE)
                .jsonResponseMoeda(DEFAULT_JSON_RESPONSE_MOEDA)
                .valor(DEFAULT_VALOR);
    }

    @BeforeEach
    public void initTest() {
        request = createEntity();
    }

    /**
     * Verificar que o valor da cotação vem do que já foi calculado anteriormente.
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getValorCotacaoFromDatabaseCache() throws Exception {
        Optional<Request> cotacaoOptional = requestRepository.findOneByCultureAndDate(DEFAULT_CULTURE, DEFAULT_DATE);
        assertThat(cotacaoOptional).isEmpty();

        requestRepository.saveAndFlush(request);
        restItemMockMvc.perform(get("/api/cotacao/" + DEFAULT_CULTURE + "/" + DEFAULT_DATE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string(Currency.BRL.toString() + " " + request.getValor()));
    }

    /**
     * Verificar quando uma cotação não pode ser calculada por falta de informação em alguma das APIs de consulta.
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getValorCotacaoIsNotAvailable() throws Exception {
        restItemMockMvc.perform(get("/api/cotacao/" + DEFAULT_CULTURE + "/" + DEFAULT_DATE_ZERO))
                .andExpect(status().isNotFound());
    }

    /**
     * Verificar que o valor é calculado fazendo as requisções as APIs e que a cotação ficou salva no banco
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getValorCotacaoFromAPIs() throws Exception {
        restItemMockMvc.perform(get("/api/cotacao/" + DEFAULT_CULTURE + "/" + DEFAULT_DATE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string(Currency.BRL.toString() + " " + request.getValor()));

        Optional<Request> cotacaoOptional = requestRepository.findOneByCultureAndDate(DEFAULT_CULTURE, DEFAULT_DATE);
        assertThat(cotacaoOptional).isNotEmpty();
        Request request = cotacaoOptional.get();
        assertThat(request.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(request.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(request.getCulture()).isEqualTo(DEFAULT_CULTURE);
    }
}
