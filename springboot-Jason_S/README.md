Table of contents
* [Introduction](#Introduction)
* include all first level titles

# Introduction
In this project, I created an MVP of a trading app by utlizing Java and springboot. The final goal of the project is to create a demo that is able to grab relevant quotes, process orders, and also manage accounts. The technology used is a springboot framework for the app itself, the default Apache Tomcat to handle all the web services, a PSQL server, and finally dockerized images for both the app and the server.

# Quick Start
For a quick setup, pull the dockerized images from 

`docker pull jason121301/trading-app`

`docker pull jason121301/trading-psql`

Ensure that a docker network is created so your to docker containers are able to communicate with each other.

`sudo docker network create trading-net`

Then run the dockers appropriately with proper argument for the API Keys alongside the database settings

`sudo docker run --name trading-psql-dev \
-e POSTGRES_PASSWORD=password \
-e POSTGRES_DB=jrvstrading \
-e POSTGRES_USER=postgres \
--network trading-net \
-d -p 5432:5432 jason121301/trading-psql`

`sudo docker run --name trading-app-dev \
-e "PSQL_URL=jdbc:postgresql://trading-psql-dev:5432/jrvstrading" \
-e "PSQL_USER=$PSQL_USER" \
-e "PSQL_PASSWORD=$PSQL_PASSWORD" \
  -e FINNHUB_TOKEN="$FINNHUB_TOKEN" \
  -e FINAGE_TOKEN="$FINAGE_TOKEN" \
--network trading-net \
-p 5000:5000 -t jason121301/trading-app
`
- Finally, it is able to test out the application by going to 5000/swagger-ui.html#/

# Implemenation
## Architecture

- The controller layer handles all the http request and returns corresponding reponses to each request. The service layers then handles the different entities in the environment given the request from the controller. Finally, the data layer inserts and gets the data required to proces thee different requst. As for the web request, TomCat is used to handle all the web services such as hearing for request and making request. Finally, the financial quotes itself is created by making API request to Finnage and Finnhub.


## REST API Usage
### Swagger
- Swagger is an api documenter that generates a document that has all the different endpoints that the controllers listen and allows you to test and run these endpoints on the browser.
### Quote Controller
- The market data itself comes from a combination of two API, Finnhub and Finnage. In addition to that, the quote HTTP resource also handles updating the daily list, adding a new ticker to the daily list and getting information on the current daily list.
- All qoute controller endpoint
    - GET `/quote/dailyList`: list all securities that are available to trading in this trading system
    - GET `/quote/ticker/{ticker}`: Gets a quote from the database given a specific ticker
    - PUT `/quote/FinnAPI`: Update all quotes in the daily list
    - POST `/quote/ticker/{ticker}`: Gets a quote from the API given a specific ticker and insert into database
### Trader Controller
  - Manages trader and account information. it can deposit and withdraw fund from a given account. Alongside creating and deleting accounts
- All account controller endpoint
  - POST `/trader/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}`: Make a new trader and account given the path variables
  - POST `/trader`: Create a new trader and account given a Json of type trader in the body
  - DELETE `/trader/traderId/{traderId}` : Delete a trader and account given the traderId
  - PUT `/trader/deposit/traderId/{traderId}/amount/{amount}` : Deposit a given amount into the account
  - PUT `/trader/withdraw/traderId/{traderId}/amount/{amount}` : Withdraw a given amount into the account

### Order Controller
- Controller that handles creation of orders
- All order controller endpoint
  - POST `/order/marketorder`: create an order given the corresponding Json

### Dashboard controller
- Controler to obtain trader and account information alongide position that could be used to build a dashboard
- All dashboard controler endpoint
  - GET `/dashboard/profile/traderId/{traderId}` : Get the TraderAccountView of a Trader given its id
  - GET `/dashboard/portfolio/traderId/{traderId}` : Get the Portfolio View of a Trader. This includes the position which is the stocks this trader has

# Test
I tested my application by creating JUnit test for each java object to have an integration test for each function. This testing includes its own test database and config so it does not test on live data. The test itself has 70 coverage
# Deployment
- https://www.notion.so/jarviscanada/Dockerize-Trading-App-fc8c8f4167ad46089099fd0d31e3855d#6f8912f9438e4e61b91fe57f8ef896e0
- The three parts of the dockerized app
  - Docker Network
    - Creates a bridge between the container app and the container database that allows the app to access the databas
  - trading-app
    - The uber jar that contains all the code to run the trading app. Must include configurations such as the API keys and the database information. Uses `maven:3.9.12-eclipse-temurin-11` as the base build. Then uses `eclipse-temurin:11-jdk-alpine` to run the packaged uber jar.
  - trading-psql
    - A docker container that uses an sql initialization script to setup the initial tables and schemas for the application to use to store all it entities. It uses `postgres:9.6-alpine` as its base image. 


# Improvements
- Include an actual front end UI that people can use to view daily list, place order and view their portfolio.
- Include a schedules call towards daily list instead of a manual call
-  Addind proper identity verification instead of passing in traderId for TraderAccountController
