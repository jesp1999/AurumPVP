@echo off
:: Script Written by Ian McDowell
:: Creates Plugin Test Server

:: Create directory for server files
if not exist server\ (mkdir server)
cd server

:: Check if Build Tools has been run for Spigot
if not exist Spigot\ (
	curl https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar -O BuildTools.jar
	git config --global --unset core.autocrlf
	java -jar BuildTools.jar
)

:: Creates eula agreed text file so server can run
if not exist eula.txt (echo eula=true>eula.txt)

:: Copies latest version of plugin into server files
if not exist plugins\ (
	set "PLUGIN_DIR=../target"
	for /f "tokens=1" %%i in ('ls %PLUGIN_DIR% ^| grep -P "AurumPVP-[0-9]*(\.[0-9]*)*-SNAPSHOT.jar"') do set PLUGIN_JAR=%%i
	md plugins
	cp %PLUGIN_DIR%\%PLUGIN_JAR% ./plugins
)

:: Gets the name of the spigot jar file and boots 
for /f "tokens=1" %%i in ('ls ^| grep -P "spigot-(([0-9]*)\.)*jar"') do set SPIGOT_JAR=%%i
java -jar %SPIGOT_JAR% nogui
