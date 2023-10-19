#!/bin/bash
docker build -t gatling-test .
docker run -it --rm=false --name gatling-test-container gatling-test ./mvnw gatling:test -Dgatling.simulationClass=simulations.CrudTest
docker cp gatling-test-container:/app/target/gatling ./target/
docker rm gatling-test-container
