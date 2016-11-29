#!/bin/bash

cd supersede-frontend-core/ && ./gradlew clean build &&
cd ../supersede-frontend && ./gradlew clean build &&
cd ../supersede-client && ./gradlew clean build &&
cd ../admin-user-manager-app && ./gradlew clean build &&
cd ../redis-sessions-inspector && ./gradlew clean build
