#!/bin/sh

$TOMCAT/bin/shutdown.sh ;

rm -rf $TOMCAT/webapps/* ;

cp wp5/build/libs/wp5-0.0.1-SNAPSHOT.war $TOMCAT/webapps/ROOT.war ;

cp admin-user-manager-app/build/libs/admin-user-manager-app-0.0.1-SNAPSHOT.war $TOMCAT/webapps/admin-user-manager-app.war ;

$TOMCAT/bin/startup.sh ;

echo $TOMCAT