# Spring Boot REST API Demo

### This demo was developed and tested on Ubuntu Linux, so all commands and dependencies reflect this further down this documentation.

**Note:**
Since this is only a proof of concept, not all models have `POST` and `PUT` endpoints.

## Requirements (used during development):
`Apache Maven 3.6.3`

`Java version: 15.0.1`

The variable `JAVA_HOME` needs to be set to the JDK in use. Such as:

`export JAVA_HOME=/usr/lib/jvm/jdk-15.0.1/`

There are some initial data loaded:
- 3 levels: `noob`, `pro` and `invincible`
- 5 games: `Fortnite`, `Call of Duty Warzone`, `Dota`, `Among Us` and `Fall Guys`
- 3 genders: `Male`, `Female` and `Other`
- 3 locations: `Europe`, `Asia` and `America`
- 100 gamers randomly generated
- a gamer level for each gamer randomly generated
- credits for each game per gamer

## How to use:

`cd` into the folder named `initial` and execute the following command:

`./mvnw clean package`

This compiles the code, runs the tests and builds the `.jar` file we need to run. If you got the message `BUILD SUCCESS`, you can run the following command which will start the local server:

`java -jar target/rest-service-0.0.1-SNAPSHOT.jar`

If you can see the console output `Started RestServiceApplication`, you can start hitting the endpoints.

## Endpoints:

### Get all levels:

This fetches all levels.

`GET localhost:8080/levels`

<br />

### Get all games:

This fetches all games.

`GET localhost:8080/games`

<br />


### Get all genders:

This fetches all genders.

`GET localhost:8080/genders`

<br />


### Get all gamers:

This fetches all gamers.

`GET localhost:8080/gamers`

<br />


### Get gamer by gamerId:

This fetches a gamer based on gamerId.

`GET localhost:8080/gamer/:gamerId`

<br />


### Get all gamer levels by gamerId:

This fetches all gamer levels based on a gamerId.

`GET localhost:8080/gamer/:gamerId/gamerlevel`

<br />


### Get all locations:

This fetches all locations.

`GET localhost:8080/geography`

<br />


### Get location by geographyId:

This fetches a location based on geographyId.

`GET localhost:8080/geography/:geographyId`

<br />


### Get all gamer levels:

This fetches all gamer levels.

`GET localhost:8080/gamerlevels`

<br />


### Get gamer level by gamerLevelId:

This fetches a gamer level based on gamerLevelId.

`GET localhost:8080/gamerlevel/:gamerLevelId`

<br />


### Make match based on gamerId and gameId:

Makes match based on gamerId and gameId. 

`GET localhost:8080/gamer/:gamerId/make-match/:gameId`

<br />


### Get gamers with maximum credit for all games based on level:

This fetches gamers with the highest credit for each game based on levelId.

`GET localhost:8080/max-credit/:levelId`

<br />

### Get gamers with credits summed based on levelId and gameId:

This fetches all gamers with their credits summed who are on the same level and play the same game.

`GET localhost:8080/credits-summed/:levelId/:gameId`

<br />

### Get credits based on gamerId, gameId and levelId:

This fetches all the credits for a gamer, specified further by a gameId and levelId. The last element returned in the array is the sum of all credits for the gamer.

`GET localhost:8080/user-credits/:gamerId/:gameId/:levelId`

<br />

### Create a gamer:

Creates a new gamer.

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

Creates a new gamer level.

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

Gives credit to a gamer.

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

Creates a new location.

`POST localhost:8080/geography`

**Payload:**

```
{
    "name": "Antarctica"
}
```
<br />

### Update a gamer:

Updates a gamer.

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