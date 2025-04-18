# HMCTS Case Management System API

This API allows its consumer to create, update, delete and retrieve tasks by caseworkers to manage their tasks. The is built with `Java` and `PostgreSQL` running in Docker container for data persistence.

## Setup the database
Before you are able to spin up the database you will need to install [docker](https://docs.docker.com/desktop/) and [docker-compose](https://docs.docker.com/compose/install/) as the database will be running in a docker container. 

Create a `.env` file in the root of this directory, which will be used to create a password for the database, containing `POSTGRES_PASSWORD=xxx`. You can hardcode this value in `application.properties` or run `export DBUSER=postgres DBPASS=xxx` which are environment variables that your application will use to connect to the database.

Create the database with the provided [docker-compose.yml](./docker-compose.yml) file by running:
```bash
docker-compose up -d
```
or
```bash
docker-compose up
```
to follow the container logs.

## Run API
Once you have the database running in a docker container, run `./gradlew bootRun` to run the application and use a tool like `Postman` to test the API or visit the [docs](http://localhost:8080/swagger-ui/index.html) and test out the available endpoints. The API comes with tests written and can be ran with `./gradlew test`.
