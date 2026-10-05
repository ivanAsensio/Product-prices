# Product Prices

## Description

Backend application developed with Java and Spring Boot that provides an API to retrieve the applicable price for a product at a given date and time.

The application exposes a REST endpoint that determines which price should be applied based on the product, brand, application date and price priority.

## How to Run

### Requirements

- Java 21
- Maven

### Run the application

Navigate to the `boot` module:

```bash
cd product-prices-boot
```

Then start the Spring Boot application:

```bash
mvn spring-boot:run
```

Once the application is running, the REST API is available at:

```text
http://localhost:8080/product-prices
```

## API

### Get product price

GET /product-prices?datetime={datetime}&productId={productId}&brandId={brandId}

#### Parameters

| Parameter | Type | Description |
|---|---|---|
| `datetime` | ISO 8601 DateTime | Date and time for which the price is requested |
| `productId` | Long | Product identifier |
| `brandId` | Long | Brand identifier |

#### Example

GET /product-prices?datetime=2020-06-14T16:00:00&productId=35455&brandId=1

#### Response

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "price": 25.45,
  "currency": "EUR"
}
```

## Technical Decisions

### Technologies

* Java 21
* Spring Boot
* Maven
* H2

### Architecture

The project follows a Hexagonal Architecture (Ports & Adapters).

The modules are organized as follows:

- model:
  Contains the domain model: entities, value objects and business concepts.
  It has no dependency on infrastructure or frameworks.

- domain:
  Contains the application/domain logic and use cases.
  It defines the ports required by the domain/application layer.
  It does not depend on infrastructure implementations.

- infrastructure:
  Contains infrastructure adapters and technical implementations,
  such as database persistence.

- rest:
  Contains the REST API adapter and OpenAPI-generated contracts/models.
  Although REST is conceptually an inbound adapter in Hexagonal Architecture,
  it is kept as a separate module to isolate the API contract and generated
  code from the rest of the infrastructure implementation.

- boot
  Contains application bootstrap and dependency wiring.


### Database Access

The application uses **JdbcTemplate** for database access instead of an ORM such as JPA/Hibernate.

This approach keeps the persistence layer lightweight and provides explicit control over SQL queries and database interactions.

It also avoids introducing unnecessary ORM complexity for a simple read-oriented use case such as retrieving the applicable product price.

### Price Selection Strategy

The price selection logic is implemented in the domain layer, keeping the business rules independent from the REST API and persistence implementation.

For a given product, brand and date-time, the application retrieves the prices applicable to the requested date and selects the one with the highest priority.

This ensures that the price selection rule is handled as part of the business logic rather than being coupled to the database or the REST adapter.

### Error Handling

The REST API uses a centralized exception handling mechanism through `@RestControllerAdvice`.

Specific application exceptions are mapped to the corresponding HTTP status codes:

- `InvalidProductPriceRequestException` → `400 Bad Request`
- `ProductPriceNotFoundException` → `404 Not Found`
- Unexpected `Exception` → `500 Internal Server Error`

All errors follow a consistent `ErrorResponse` structure containing:

- `status`: HTTP status code.
- `code`: application-specific error code.
- `message`: human-readable error message.

This approach keeps error handling centralized and avoids duplicating exception-to-HTTP mapping logic across the REST controllers.

Unexpected internal errors return a generic message to avoid exposing implementation details to API consumers.

```json
{
  "status": 404,
  "code": "PRODUCT_PRICE_NOT_FOUND",
  "message": "There is no product price for the given criteria"
}
```

### API Design

The REST API follows an **API First** approach, using **OpenAPI 3.0.3** as the contract between the API and its consumers.

The API contract is defined in:

`product-prices-rest/src/main/resources/api/rest-productprices.yaml`

The REST API interfaces and models are generated from the OpenAPI specification. This ensures that the implementation remains aligned with the documented API contract and reduces boilerplate code.

All request parameters are defined as **required**:

- `datetime`: Date and time used to determine the applicable product price.
- `productId`: Product identifier.
- `brandId`: Brand identifier.

The API exposes the following endpoint:

- `GET /product-prices` → Retrieves the applicable product price.

The endpoint follows standard HTTP semantics:

- `200 OK` → Product price successfully retrieved.
- `400 Bad Request` → Invalid request data.
- `404 Not Found` → No product price matches the requested criteria.
- `500 Internal Server Error` → Unexpected internal error.

The API also defines a consistent `ErrorResponse` model for error responses.

The `rest` module is kept separate from `infrastructure` as a technical decision derived from the **API First** approach. It contains the API contract and the generated API interfaces and models, allowing the API to be managed independently from the infrastructure implementations.

### Testing Strategy

The project follows a layered testing strategy, combining unit tests and integration tests.

#### Unit Tests

Unit tests are located in the `domain` module and use **JUnit 5** and **Mockito**.

Mockito is used to mock the repository port, allowing the application logic and price selection rules to be tested independently from the persistence layer.

The unit tests cover the main business scenarios, including:

- Valid product price retrieval.
- Price selection based on priority when multiple prices match.
- Invalid request data.
- No matching product price.

#### Integration Tests

Integration tests are located in the `boot` module and validate the application behavior through the REST API.

These tests start the Spring Boot application and verify the complete flow, including the REST layer, application logic and persistence integration.

The integration tests cover the scenarios defined in the exercise requirements, validating the responses indicated on the statement.

### Product Price Retrieval Flow

The following diagram illustrates the flow used to retrieve the applicable product price, from the REST API to the persistence layer.

![Product Price Retrieval Flow](docs/ProductPriceRetrieve.drawio.png)

