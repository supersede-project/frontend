tomcat="apache-tomcat-8.0.30"
file="dist/$tomcat.zip"
version="0.0.1-SNAPSHOT"
adminapp="admin-user-manager-app"
gameapp="game-requirements"
DATE=$(date +%Y%m%d_%H%M%S)
if ! [ -f "$file" ]
then
	cd dist && 
	wget http://apache.panu.it/tomcat/tomcat-8/v8.0.30/bin/$tomcat.zip && 
	unzip $tomcat.zip &&
	cd ..
fi

rm -rf dist/$tomcat/webapps/* &&
cp conf/multitenancy.properties dist/$tomcat/conf/ && 
cd applications &&
cd wp5-utils && gradle build && cd .. &&
cd wp5-clients-utils && gradle build && cd .. &&
cd wp5 && gradle build && cp build/libs/wp5-$version.war.original ../../dist/$tomcat/webapps/ROOT.war && cd .. &&
cd $adminapp && gradle build && cp build/libs/$adminapp-$version.war.original ../../dist/$tomcat/webapps/$adminapp.war && cd .. &&
cd $gameapp && gradle build && cp build/libs/$gameapp-$version.war.original ../../dist/$tomcat/webapps/$gameapp.war && cd .. &&
cd ../dist &&
zip -r supersede-bin-$DATE $tomcat
