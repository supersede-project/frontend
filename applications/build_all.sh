#!/bin/bash

cd supersede-frontend-core
echo -e "\nBuilding supersede-frontend-core...\n"
sh gradlew build

cd ../supersede-frontend
echo -e "\nBuilding supersede-frontend...\n"
sh gradlew build

cd ../supersede-client
echo -e "\nBuilding supersede-client...\n"
sh gradlew build

cd ../admin-user-manager-app
echo -e "\nBuilding admin-user-manager-app...\n"
sh gradlew build

cd ../configuration-tools
echo -e "\nBuilding configuration-tools...\n"
sh gradlew build
