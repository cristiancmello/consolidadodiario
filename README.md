# ConsolidadoDiario

Serviço do Consolidado Diário.

Tecnologias utilizadas:

* Spring Boot 3
* Apache Maven 3

## Build

```sh
mvn clean install
```

## Testing

```sh
mvn clean verify
```

## Container

### Build

```sh
docker build -t consolidadodiario .
```

### Run

```sh
docker run --name servico-consolidadodiario --network cashflower_network -p 8081:8081 consolidadodiario
```