#!/bin/bash

cd supersede-frontend-core
echo -e "\nCleaning supersede-frontend-core...\n"
./gradlew clean
echo -e "\nBuilding supersede-frontend-core...\n"
./gradlew build --refresh-dependencies

cd ../supersede-frontend
echo -e "\nCleaning supersede-frontend...\n"
./gradlew clean
echo -e "\nBuilding supersede-frontend...\n"
./gradlew build --refresh-dependencies

cd ../supersede-client
echo -e "\nCleaning supersede-client...\n"
./gradlew clean
echo -e "\nBuilding supersede-client...\n"
./gradlew build --refresh-dependencies

cd ../admin-user-manager-app
echo -e "\nCleaning admin-user-manager-app...\n"
./gradlew clean
echo -e "\nBuilding admin-user-manager-app...\n"
./gradlew build --refresh-dependencies


cd ../configuration-tools
echo -e "\nCleaning configuration-tools...\n"
./gradlew clean
echo -e "\nBuilding configuration-tools...\n"
./gradlew build --refresh-dependencies