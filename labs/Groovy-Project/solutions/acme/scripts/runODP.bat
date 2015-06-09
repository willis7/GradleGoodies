@echo off
setlocal ENABLEDELAYEDEXPANSION

if defined CLASSPATH (set CLASSPATH=%CLASSPATH%;.) else (set CLASSPATH=acme-odp.jar)
FOR /R .\lib %%G IN (*.jar) DO set CLASSPATH=!CLASSPATH!;%%G
REM Echo The Classpath definition is %CLASSPATH%

java -Xmx1024m -Xms128m -Denv=prod com.acme.odp.ODP %*