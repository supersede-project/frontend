#!/bin/sh

$TOMCAT/bin/shutdown.sh

sleep 3

rm -rf $TOMCAT/webapps/*

cp applications/supersede-frontend/build/libs/supersede-frontend-0.0.3-SNAPSHOT.war $TOMCAT/webapps/ROOT.war
cp applications/admin-user-manager-app/build/libs/admin-user-manager-app-0.0.3-SNAPSHOT.war $TOMCAT/webapps/admin-user-manager-app.war
cp applications/supersede-client/build/libs/supersede-client-0.0.3-SNAPSHOT.war $TOMCAT/webapps/supersede-client.war
cp applications/supersede-frontend-core/build/libs/supersede-frontend-core-0.0.3-SNAPSHOT.war $TOMCAT/webapps/supersede-frontend-core.war

BUILD_ID=dontKillMe nohup $TOMCAT/bin/startup.sh &

sleep 3

echo $TOMCAT