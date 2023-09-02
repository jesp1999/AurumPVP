#!/bin/bash
# Script Written by Ian McDowell
# Creates Plugin Test Server

# Create directory for server files
if [ ! -d "./server" ]
then
	mkdir server
fi
cd server

# Check if Build Tools has been run for Spigot
if [ ! -d "./Spigot" ]
then
	sudo wget https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar -O BuildTools.jar
	git config --global --unset core.autocrlf
	java -jar BuildTools.jar
fi

# Creates eula agreed text file so server can run
if [ ! -f "eula.txt" ]
then
	echo "eula=true" > eula.txt
fi

# Copies latest version of plugin into server files
if [ ! -d "./plugins" ]
then
	PLUGIN_DIR="../target"
	PLUGIN_JAR=$(ls $PLUGIN_DIR | grep -P "AurumPVP-[0-9]*(\.[0-9]*)*-SNAPSHOT.jar")
	mkdir plugins
	cp $PLUGIN_DIR/$PLUGIN_JAR ./plugins -r
fi

# Gets the name of the spigot jar file and boots
SPIGOT_JAR=$(ls | grep -P "spigot-(([0-9]*)\.)*jar")
java -Xmx4G -Xms2G -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar $SPIGOT_JAR nogui
