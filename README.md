
# Desafio Votação 
Autor : Henrique De Paula Leite

## Tecnologias utilizadas
- Java 11
- Spring boot 2.7
- Docker compose 
- PostgreSQL
- Documentação : Swagger
- Teste: JUnit 4.12, Mockito, H2

## Como rodar o projeto

***Build***

``` mvn clean install ```

Após buildar a aplicação é necessário subir o Postgres e o Kafka através do docker-compose, com o comando abaixo:

``` docker-compose up ```

***Run***

Comando para rodar a aplicação:

``` mvn spring-boot:run ```


***Swagger***

localhost:8080/swagger-ui.html
