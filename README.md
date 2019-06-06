
SHIPMENT SERVICE
================
[![Build Status](https://api.travis-ci.org/TusharPanda1986/shipment-service.svg)](https://travis-ci.org/TusharPanda1986/shipment-service)

A range assessment service which determines the minimum number of zip code ranges to which a product cannot be shipped.

REQUIREMENTS
------------
- Any IDE of your choice.
- [Oracle JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 8
- [Gradle](https://maven.apache.org/install.html) 3.6
- GIT client


SETUP
-----

### Checkout the project and build

The code is uploaded to [github](https://github.com/TusharPanda1986/shipment-service). Clone it and build the master branch by executing following command:

    mvn clean install
    
This will build the project, execute the test cases and create the executable jar file.

EXECUTION
---------

The JAR file will be created in ${PROJECT_DIR}/target.
Access the directory in command prompt or console and execute the following command:

    java -jar shipment-service-0.0.1-SNAPSHOT.jar [94133,94133] [94200,94299] [94226,94399]

         

TESTS
-----

### Running Unit Tests (Java)

    mvn test

This will execute the Junit tests within com.ws.shipment.service.ShipmentServiceTest