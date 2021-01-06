package br.desafio.idtrust.config;

public final class Constants {

    /**
     * URL do serviço usado para calcular a cotação USD-BRL
     * https://docs.awesomeapi.com.br/api-de-moedas#retorna-o-fechamento-de-um-periodo-especifico
     */
    public static final String COTACAO_USD_BLR_API_URL = "https://economia.awesomeapi.com.br/json/daily/USD-BRL/?start_date={startDate}&end_date={endDate}";

    /**
     * URL do serviço usado para calcular a cotação das culturas da CEPEA
     * https://www.quandl.com/data/CEPEA-Center-for-Applied-Studies-on-Applied-Economics-Brazil/usage/quickstart/api
     */
    public static final String COTACAO_CEPEA_API_URL = "https://www.quandl.com/api/v3/datasets/CEPEA/{cultura}?start_date={date}&end_date={date}&api_key={apiKey}";

    /**
     * Variavel de ambiente referente à API_KEY do site https://www.quandl.com/
     * */
    public static final String ENV_QUANDL_API_KEY = "QUANDL_API_KEY";

    private Constants() {
    }
}
