#!/bin/bash

echo -e "\nBuilding supersede-frontend-core...\n"
cd supersede-frontend-core/ && ./gradlew clean build --refresh-dependencies

echo -e "\nBuilding supersede-frontend...\n"
cd ../supersede-frontend && ./gradlew clean build --refresh-dependencies -x test

echo -e "\nBuilding supersede-client...\n"
cd ../supersede-client && ./gradlew clean build --refresh-dependencies

echo -e "\nBuilding admin-user-manager-app...\n"
cd ../admin-user-manager-app && ./gradlew clean build --refresh-dependencies

echo -e "\nBuilding redis-sessions-inspector...\n"
cd ../redis-sessions-inspector && ./gradlew clean build --refresh-dependencies
