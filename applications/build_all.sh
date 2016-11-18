#!/bin/bash

echo -e "\nBuilding supersede-frontend-core...\n"
cd supersede-frontend-core/ && ./gradlew build

echo -e "\nBuilding supersede-frontend...\n"
cd ../supersede-frontend && ./gradlew build

echo -e "\nBuilding supersede-client...\n"
cd ../supersede-client && ./gradlew build

echo -e "\nBuilding admin-user-manager-app...\n"
cd ../admin-user-manager-app && ./gradlew build

echo -e "\nBuilding redis-sessions-inspector...\n"
cd ../redis-sessions-inspector && ./gradlew build