;DOS script to start up the reducer/grapher
@echo off
;set DDR_DIR=C:\daves_stuff\ddr\
set DDR_DIR=
set CLASSPATH=%DDR_DIR%classes
set CLASSPATH=%CLASSPATH%;%DDR_DIR%conf
set CLASSPATH=%CLASSPATH%;%DDR_DIR%lib\*
@echo on
java -Xmx2048m au.com.polly.ddr.Liberator
