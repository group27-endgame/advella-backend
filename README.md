# Advella-Backend
Backend application for Advella application as part of bachelor project at VIA University College.

## Structure
This project is divided into 3 main parts. Controllers, which expose the REST API endpoints. Services that apply all the needed logic and Repositories which are calling the database.

## Rest api documentation
To increase the efficiency communicating between backend and frontend Swagger documentation was exposed to specify which endpoints are available. All the forms of models, descriptions, error codes and examples are also present.
https://api.advella.popal.dev/swagger-ui.html#/

## Testing
### Unit tests
Each service is unit tested, with sunny scenarios as well as failure setups. All external things are mocked to test each specific cases.
### Integration tests
More complex tests, which start the whole application and are mocking API calls. In these scenarios it is important how the API controllers work with Services and Repositories. Main objective of these is to test the flow throughout the backend, in a sense of how do the different components behave together.
