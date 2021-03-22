# Desafio Técnico API de gerenciamento de sessões de votação

API de sessão de votação, orientada pela abertura de uma pauta para ser votada durante um determinado período, onde somente pode haver um voto por associado.

 Objetivos
  - Cadastrar uma nova pauta;
  - Abrir uma sessão de votação em uma pauta (a sessão  de votação deve ficar aberta por um tempo
determinado na chamada de abertura ou 1 minuto por default);
  - Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado  é
identificado por um id único e pode votar apenas uma vez por pauta);
  - Contabilizar os votos e dar o resultado da votação na pauta.
### Tecnologias

* Java 8
* Spring Boot Web; JPA; Data; Cloud;
* Swagger 2
* PostgreSql
* Lombok
* Plataforma em nuvem heroku

### Instalação

```sh
$ git clone https://github.com/reawmarco/api-votacao.git
$ cd api-votacao
$ mvn package
$ cd target
$ java -jar api_votacao.jar
```
#### Swagger
Produção Heroku:
```
https://api-desafio-back.herokuapp.com/swagger-ui.html#/
```
Desenvolvimento:
```
http://localhost:8080/swagger-ui.html#/
```
### Heroku

```
URL:
https://api-desafio-back.herokuapp.com/

Pauta:
https://api-desafio-back.herokuapp.com/api/v1/pauta/{id}

Resultado da Sessão:
https://api-desafio-back.herokuapp.com/api/v1/votacao/resultado/{idPauta}/{idSessaoVotacao}
```

### Versionamento
    - O versionamento da api foi desenvolvido pelo número major da versão(v1) diretamente na URL de acesso.

