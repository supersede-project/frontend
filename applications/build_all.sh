#!/bin/bash

cd supersede-frontend-core/ && ./gradlew build &&
cd ../supersede-frontend && ./gradlew build &&
cd ../supersede-client && ./gradlew build &&
cd ../admin-user-manager-app && ./gradlew build &&
cd ../redis-sessions-inspector && ./gradlew build