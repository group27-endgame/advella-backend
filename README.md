# Advella-Backend
Backend application for Advella application as part of bachelor project at VIA University College.

## Structure
This project is divided into 3 main parts. Controllers, which expose the REST API endpoints. Services that apply all the needed logic and Repositories which are calling the database. In this context it is the middleware a bridge between all the client applications and database.

![Middleware](https://user-images.githubusercontent.com/62397372/203567811-0fda64db-b5aa-406b-9377-b28cd257f89f.png)

## Rest api documentation
To increase the efficiency communicating between backend and frontend Swagger documentation was exposed to specify which endpoints are available. All the forms of models, descriptions, error codes and examples are also present.
https://api.advella.popal.dev/swagger-ui.html#/

## Testing
### Unit tests
Each service is unit tested, with sunny scenarios as well as failure setups. All external things are mocked to test each specific cases.
### Integration tests
More complex tests, which start the whole application and are mocking API calls. In these scenarios it is important how the API controllers work with Services and Repositories. Main objective of these is to test the flow throughout the backend, in a sense of how do the different components behave together.

## Github Actions
There are two actions present. First one build the project and runs all the tests when some new code is merged onto main branch. After the first action is successful, it will proceed with second action in form of deploying the app onto the server, after which it will be hosted and accesible from all the client apps on this URL: https://advella.popal.dev/
