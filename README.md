### ![Build](https://api.travis-ci.org/arieldossantos/QRPoint-API-Java.svg?branch=master)
# QRPoint Java API

API de integração com o sistema QRPoint feita em Java

Para o uso desta api, faça download do jar contido na pasta dist

Documentação geral no site do [QRPoint](https://api.qrpoint.com.br/docs/)

***Utilizamos a biblioteca java-json.jar***

# Docs da Java API


## Criar objeto API Key

Crie uma instância da classe **APIKey** existente no pacote _br.com.qrpoint.api_ com a chave da sua API como parâmetro em String.

```java
public APIKey createAPIKey(String YOUR_API_KEY) throws ApiKeyException {
    APIKey apiKey = new br.com.qrpoint.api.ApiKey(YOUR_API_KEY);
}
```
Este método pode lançar uma ApiKeyException.

## Criar objeto API

Crie uma instância da classe **QRPointAPI** existente no pacote _br.com.qrpoint.api_ com um objeto APIKey como parâmetro.

```java
QRPointAPI api = new QRPointAPI(YOUR_API_KEY_OBJECT);
```
___
Os métodos abaixo retornam um JSON com base na API [QRPoint](https://api.qrpoint.com.br/docs/)

### Obter empresas

Utilize o método **obterEmpresas()** do seu objeto _QRPointAPI_.

```java
api.obterEmpresas();
```

### Obter empregadores

Utilize o método **obterEmpregadores()** do seu objeto _QRPointAPI_ com uma String com o código da empresa como parâmetro.

```java
api.obterEmpregadores("EMPRESA_COD");
```

### Obter empregadores

Utilize o método **obterColaboradores()** do seu objeto _QRPointAPI_ com uma String com o código do empregador como parâmetro. 

```java
api.obterColaboradores("EMPREGADOR_COD");
```

### Obter empregadores

Utilize o método **obterRegistrosDePonto()** do seu objeto _QRPointAPI_ com uma String com o código do empregador como parâmetro. 

```java
api.obterRegistrosDePonto("EMPREGADOR_COD", ID_FUNCIONARIO, "DATA_INICIO", "DATA_FIM");
```

