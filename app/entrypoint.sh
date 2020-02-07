#!/bin/bash

sleep 30
java -jar -Dspring.profiles.active=docker build/libs/${JAR_TARGET} -xms5g -xmx50g

