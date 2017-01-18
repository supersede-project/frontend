#!/bin/bash

cd supersede-frontend-core
echo -e "\nBuilding supersede-frontend-core...\n"
./gradlew build

cd ../supersede-frontend
echo -e "\nBuilding supersede-frontend...\n"
./gradlew build

cd ../supersede-client
echo -e "\nBuilding supersede-client...\n"
./gradlew build

cd ../admin-user-manager-app
echo -e "\nBuilding admin-user-manager-app...\n"
./gradlew build

cd ../configuration-tools
echo -e "\nBuilding configuration-tools...\n"
./gradlew build