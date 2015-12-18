#!/bin/sh

$CATALINA_HOME/bin/shutdown.sh ;

rm -rf $CATALINA_HOME/webapps/* ;

cp wp5/build/libs/wp5-0.0.1-SNAPSHOT.war $CATALINA_HOME/webapps/ROOT.war ;

cp admin-user-manager-app/build/libs/admin-user-manager-app-0.0.1-SNAPSHOT.war $CATALINA_HOME/webapps/admin-user-manager-app.war ;

$CATALINA_HOME/bin/startup.sh ;

echo $CATALINA_HOME
