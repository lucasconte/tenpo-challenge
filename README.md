# Tenpo-challenge

## Overview
The following repo contains Tenpo Challenge Application, an implementation
for Tenpo Challenge.

## Requirements
- Java 11 or later
- Docker and Docker compose
- PostgreSQL
- Maven

## Local environment
In order to get the app running locally at port 8080

-  Clone this repo.
- Generate a new jar file. At project directory, run in terminal: 
  ```console
  mvn clean install
  ```
- When jar file is generated, in order to create a docker container, at the root of the project you can run
  ```console
  docker compose up
  ```
- Once container is running , you get access to api at http://localhost:8080/

- To stop container execution: 
  ```console
  docker compose down
  ```

If you want to run application trough an IDE like Intellij:

- Import project in your IDE.
- You need a postgresql instance running. You can pull docker image 
by executing in terminal:
  ```console
  docker pull postgres:apine
  ```
- And then run the image:
  ```console
  docker run --name postgre-1 -e POSTGRES_PASSWORD=password -e POSTGRES_DB=tenpo_challenge -p 5432:5432 -d postgres:alpine
  ```
- Once postgre instance is running you should be able to run the application

## Prod environment

The app is running in an AWS ec2 instance.

You can get access at:
 
 http://ec2-52-87-191-13.compute-1.amazonaws.com/

## API

 Postman collection:
 
 https://go.postman.co/workspace/My-Workspace~d718df3c-9517-4489-8df1-ec861e169167/collection/12919683-52ad05f7-8683-46c5-a5ad-5ce631abc522
 

#### API Guideline
Sign up
```console
curl --location --request POST 'http://ec2-52-87-191-13.compute-1.amazonaws.com/signUp' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "user0",
    "password": "password0"
}'
```

Login
```console
 curl --location --request POST 'http://ec2-52-87-191-13.compute-1.amazonaws.com/login' \
 --header 'Content-Type: application/x-www-form-urlencoded' \
 --data-urlencode 'username=user0' \
 --data-urlencode 'password=password0'
```

Sum operation
```console
curl --location --request GET 'http://ec2-52-87-191-13.compute-1.amazonaws.com/api/operation/sum?number0=23&number1=23' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE2MzgzODYwODEsImV4cCI6MTYzODM4Nzg4MSwiaWF0IjoxNjM4Mzg2MDgxLCJ1c2VybmFtZSI6InVzZXIwIn0.qWwqviR6LW3r2udS41zbmapbbO3sf5jwULVIEE-7my0'
```

History
```console
curl --location --request GET 'http://ec2-52-87-191-13.compute-1.amazonaws.com/history?page=0&size=10&sort=createdAt,DESC' \
--header 'Content-Type: application/json'
```

Logout
```console
curl --location --request POST 'http://ec2-52-87-191-13.compute-1.amazonaws.com/logout' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE2MzgzODYwODEsImV4cCI6MTYzODM4Nzg4MSwiaWF0IjoxNjM4Mzg2MDgxLCJ1c2VybmFtZSI6InVzZXIwIn0.qWwqviR6LW3r2udS41zbmapbbO3sf5jwULVIEE-7my0'
```

## Docker Repository

https://hub.docker.com/repository/docker/lconte92/tenpo-challenge


## Support
Please contact at contelucas.lc@gmail.com