#!/bin/bash

cd supersede-frontend-core/ && sh gradlew build &&
cd ../supersede-frontend && sh gradlew build &&
cd ../supersede-client && sh gradlew build &&
cd ../admin-user-manager-app && sh gradlew build &&
cd ../redis-sessions-inspector && sh gradlew build