# Spring Boot REST API Demo

### This demo was developed and tested on Ubuntu Linux, so all commands and dependencies reflect this further down this documentation.

**Note:**
Since this is only a proof of concept, not all models have `POST` and `PUT` endpoints.

## Requirements (used during development):
`Apache Maven 3.6.3`

`Java version: 15.0.1`

## How to use:

`cd` into the folder named `initial` and execute the following command:

`./mvnw clean package`

This compiles the code, runs the tests and builds the `.jar` file we need to run. If you got the message `BUILD SUCCESS`, you can run the following command which will start the local server:

`java -jar target/rest-service-0.0.1-SNAPSHOT.jar`

If you can see the console output `Started RestServiceApplication`, you can start hitting the endpoints.

## Endpoints:

### Get all levels:

`GET localhost:8080/levels`

<br />

### Get all games:

`GET localhost:8080/games`

<br />


### Get all genders:

`GET localhost:8080/genders`

<br />


### Get all gamers:

`GET localhost:8080/gamers`

<br />


### Get gamer by gamerId:

`GET localhost:8080/gamer/:gamerId`

<br />


### Get all gamer levels by gamerId:

`GET localhost:8080/gamer/:gamerId/gamerlevel`

<br />


### Get all locations:

`GET localhost:8080/geography`

<br />


### Get location by geographyId:

`GET localhost:8080/geography/:geographyId`

<br />


### Get all gamer levels:

`GET localhost:8080/gamerlevels`

<br />


### Get gamer level by gamerLevelId:

`GET localhost:8080/gamerlevel/:gamerLevelId`

<br />


### Make match based on gamerId and gameId:

`GET localhost:8080/gamer/:gamerId/make-match/:gameId`

<br />


### Get gamers with maximum credit for all games based on level:

`GET localhost:8080/max-credit/:levelId`

<br />

### Get gamers with credits summed based on levelId and gameId:

`GET localhost:8080/credits-summed/:levelId/:gameId`

<br />

### Get credits based on gamerId, gameId and levelId:

`GET localhost:8080/user-credits/:gamerId/:gameId/:levelId`

<br />

### Create a gamer:

`POST localhost:8080/gamer`

**Payload:**

```
{
    "name": "Random Person",
    "genderId": 1,
    "nickname": "random.person",
    "geographyId": 1
}
```

<br />

### Create a gamer level:

`POST localhost:8080/gamerlevel`

**Payload:**

```
{
    "gamerId": 1,
    "levelId": 1,
    "gameId": 1
}
```

<br />

### Give credit to a gamer:

`POST localhost:8080/credit`

**Payload:**

```
{
    "gamerId": 1,
    "gameId": 1,
    "credit": 100
}
```

<br />

### Create a new location:

`POST localhost:8080/geography`

**Payload:**

```
{
    "name": "Antarctica"
}
```
<br />

### Update a gamer:

`PUT localhost:8080/gamer/:gamerId`

**Payload:**

```
{
    "name": "Random Person 2",
    "genderId": 2,
    "nickname": "random.person.2",
    "geographyId": 2
}
```