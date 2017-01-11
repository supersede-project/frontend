#!/bin/bash

cd supersede-frontend-core
echo -e "\nBuilding supersede-frontend-core...\n"
./gradlew build --refresh-dependencies

cd ../supersede-frontend
echo -e "\nBuilding supersede-frontend...\n"
./gradlew build --refresh-dependencies

cd ../supersede-client
echo -e "\nBuilding supersede-client...\n"
./gradlew build --refresh-dependencies

cd ../admin-user-manager-app
echo -e "\nBuilding admin-user-manager-app...\n"
./gradlew build --refresh-dependencies

cd ../configuration-tools
echo -e "\nBuilding configuration-tools...\n"
./gradlew build --refresh-dependencies