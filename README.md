# Desafio T�cnico API de gerenciamento de sess�es de vota��o

API de sess�o de vota��o, orientada pela abertura de uma pauta para ser votada durante um determinado per�odo, onde somente pode haver um voto por associado.

 Objetivos
  - Cadastrar uma nova pauta;
  - Abrir uma sess�o de vota��o em uma pauta (a sess�o de vota��o deve ficar aberta por um tempo
determinado na chamada de abertura ou 1 minuto por default);
  - Receber votos dos associados em pautas (os votos s�o apenas 'Sim'/'N�o'. Cada associado �
identificado por um id �nico e pode votar apenas uma vez por pauta);
  - Contabilizar os votos e dar o resultado da vota��o na pauta.
### Tecnologias

* Java 8
* Spring Boot Web; JPA; Data; Cloud;
* Swagger 2
* PostgreSql
* Lombok
* Plataforma em nuvem heroku

### Instala��o

```sh
$ git clone https://github.com/reawmarco/api-votacao.git
$ cd api-votacao
$ mvn package
$ cd target
$ java -jar api_votacao.jar
```
#### Swagger
Produ��o Heroku:
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

Resultado da Sess�o:
https://api-desafio-back.herokuapp.com/api/v1/votacao/resultado/{idPauta}/{idSessaoVotacao}
```

### Versionamento
    - O versionamento da api foi desenvolvido pelo n�mero major da vers�o (v1) diretamente na URL de acesso.

