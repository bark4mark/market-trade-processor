Market Trade Processor
======================

Running the app
---------------
The app is built using Spring boot, clone the git repo and run the following:
```
mvn spring-boot-run
```

The app will start on port 8080.

Testing the app
---------------
There is a method in co.markhoward.messagetradeprocessor.testclient.TestClient called
generateRequests, once the app is running remove the annotation and run using
jUnit. This creates random messages and posts them to the endpoint.

If the app is running on port 8080 navigate to http://localhost:8080 in your browser
to view the dashboard.

Database
--------
The database (h2) is configured to run in memory by default, the configuration lies
in the application.properties file.

All interaction with the database uses standard JPA.

Endpoints
---------
POST /api/trademessage
An endpoint that consumes the following json:
```json
{
    "userId": "134256",
    "currencyFrom": "EUR",
    "currencyTo": "GBP",
    "amountSell": 1000,
    "amountBuy": 747.1,
    "rate": 0.7471,
    "timePlaced": "24-JAN-15 10:27:44",
    "originatingCountry": "FR"
}
```

GET /api/trademessage/live
An enpoint that returns the latest trade message

GET /api/trademessage/currencystats
An endpoint that returns the current currency statistics
(most purchased, most sold currencies)

Frontend
--------
The frontend contains a world map, as trade messages come in the world map is
updated, the country is highlighted and some of the details from the trade message
are displayed.

Validation
----------
Some basic validation is applied:
* Currencies must have 3 letters
* Country codes must have 2 letters

Implementation
--------------
I used a publisher/subsciber pattern, as messages are received the subscriber classes
pick them up and process them.

There are two subscribers in this example, one that persists the TradeMessage in
the TradeMessage table and another to update the currency statistics.
