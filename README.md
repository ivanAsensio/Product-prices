# Product Prices API

## Description

Backend application developed with Java and Spring Boot that provides an API to retrieve the applicable price for a product at a given date and time.

The application exposes a REST endpoint that determines which price should be applied based on the product, brand, application date and price priority.

## API

### Get product price

GET /productprices?datetime={datetime}&productId={productId}&brandId={brandId}

#### Parameters

| Parameter | Type | Description |
|---|---|---|
| `datetime` | ISO 8601 DateTime | Date and time for which the price is requested |
| `productId` | Long | Product identifier |
| `brandId` | Long | Brand identifier |

#### Example

GET /productprices?datetime=2020-06-14T16:00:00&productId=35455&brandId=1

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

The application follows a **Hexagonal Architecture (Ports and Adapters)** approach, keeping the business logic isolated from external technologies and infrastructure concerns.

The project is divided into the following modules:

#### `model`

Contains the core business logic and domain models.

- Defines the business entities and domain rules.
- Has no dependency on frameworks or infrastructure.
- Represents the core of the application.

#### `domain`

Contains the application use cases and orchestration logic.

- Defines the application use cases.
- Coordinates the domain logic.
- Defines the ports required to interact with external systems.
- Does not depend directly on infrastructure implementations.

#### `infrastructure`

Contains the implementations of the ports required to interact with external technologies.

- Provides persistence implementations.
- Contains infrastructure-specific components and configuration.
- Implements the output ports defined by the application layer.

#### `rest`

Contains the REST API adapter and exposes the application's use cases through HTTP.

- Defines the REST controllers and API models.
- Acts as an inbound adapter in the Hexagonal Architecture.
- Depends on the application layer to execute use cases.

Although the REST adapter conceptually belongs to the infrastructure side of the Hexagonal Architecture, it is kept in a dedicated `rest` module as a technical decision. This separation allows the API to be generated and managed independently for each module.

### Database Access

The application uses **JdbcTemplate** for database access instead of an ORM such as JPA/Hibernate.

This approach keeps the persistence layer lightweight and provides explicit control over SQL queries and database interactions.

It also avoids introducing unnecessary ORM complexity for a simple read-oriented use case such as retrieving the applicable product price.

### Price Selection Strategy

The price selection logic is implemented in the domain layer, keeping the business rules independent from the REST API and persistence implementation.

For a given product, brand and date-time, the application retrieves the prices applicable to the requested date and selects the one with the highest priority.

This ensures that the price selection rule is handled as part of the business logic rather than being coupled to the database or the REST adapter.