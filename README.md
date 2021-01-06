# desafio-idtrust
<h3>Breve descrição</h3>
<p>A API possui o endpoint <b>/api/cotacao/{cultura}/{dataCotacao}</b> que requisita as APIs de cotações das culturas
e logo converte o valor de USD a BRL usando outra API de cotação de moedas. O sistema salva o histórico das cotações
para serem retornadas no futuro e desse jeito não ter que requisitar as APIs para obter valores que já foram calculados
anteriormente.</p>
<p>Exemplo de chamada ao endpoint: <a href="http://localhost:8080/api/cotacao/CORN/2020-12-21" 
                                   target="_blank">http://localhost:8080/api/cotacao/CORN/2020-12-21</a></p>


<h3>Rodar os testes de integração</h3>
<b>$ mvnw clean verify</b><br>
<p>Pode ser que no final aconteça um erro 401. Isto acontece porque o script tenta atualizar a imagem no DockerHub,
porém não estão configuradas as credenciais.</p>
<p>O resultado dos testes fica disponível no diretório: <b>target/jacoco</b></p>
<small>Code coverage: <b>93%</b></small>

<h3>Rodar o Sistema</h4>

<h5>- Usando Docker</h4>

O Sistema possui uma imagem pública no DockerHub: <b>"janierj/desafio-idtrust".</b> A imagem já tem a variavel de 
ambiente QUANDL_API_KEY injetada, você não precisa se preocupar com isso

1- Rodar o comando <b>$ docker-compose -f src/main/docker/app.yml up</b>

<h5>- Usando Maven</h5>
Neste caso precisa ser pasada a variavel de ambiente <b>QUANDL_API_KEY</b>.

<p>1- Configurar as credenciais do seu banco de dados Postgres no arquivo: <b>src/main/resources/application.properties</b>
<p>2- Rodar o comando:</p>
<p><b>$ mvnw spring-boot:run -Dspring-boot.run.arguments=--QUANDL_API_KEY=A_SUA_QUANDL_API_KEY</b><p>

<h3>Testar a API manulamente</h3>
<p>Está disponivel <a href="http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/" 
target="_blank">Swagger</a></p>