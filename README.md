# ACODIHUE - PROJECT -CORE

This project was generated with

* Java 11
* jakarta EE 8
* Payara Micro 3.0
* JWT 2.0
* OPEN CSV 4.0
* APACHE POI x.0
* Arquillian is disabled
* Junit is disabled

## IDE Development

Recommend to use Intellij 2020.

## DATABASE

use the file server-variables to inject environments into container, its configuration local and the file its added into
.gitignore

````
- DATABASE_SERVER=172.18.0.1
- DATABASE_PORT=5432
- DATABASE_NAME=acodihue_database
- DATABASE_USER=solar
- DATABASE_PWD=solarpass
````

## INSTALLING AND DEPLOYING

before to deploy, you must install `open-jdk (V 11)`  not oracle jdk use the next commands

````
sudo apt install openjdk-11-jdk
````

and after check the version

````
java --version
````

next , you must install `maven (3.6)` to create `acodihue-core.war`and package the project.

````
sudo apt install maven
````

and after check the version

````
maven --version
````

## Build

Use the `buildAndRun.sh` to deploy a container automatically
