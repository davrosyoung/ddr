#!/bin/ksh

PROJECT_DIR=/Users/dave/projects/ddr
DATA_DIR=/Users/dave/projects/ddr/data
SHEET_NAME="Allocated"
HEAP_SIZE="512m"
FILE_NAME="osho2.csv"
#ANOTHER_FILE_NAME="nick_data.obj"

if [ ! -e $PROJECT_DIR ]
then	echo "Cannot find project directory ["$PROJECT_DIR"]"
	exit 1
fi

if [ ! -e $DATA_DIR ]
then	echo "Cannot find data directory ["$DATA_DIR"]"
	exit 1
fi

CLASSPATH=$CLASSPATH":"${PROJECT_DIR}"/out/production"
CLASSPATH=$CLASSPATH":"${PROJECT_DIR}"/dist/ddr.jar"
for jar in $PROJECT_DIR/java/lib/*.jar
do
	CLASSPATH=${CLASSPATH}":"${jar}
done
CLASSPATH=${CLASSPATH}":"${PROJECT_DIR}"/conf"

export CLASSPATH PROJECT_DIR

echo "CLASSPATH=["$CLASSPATH"]"

java -Xmx${HEAP_SIZE} -Dlog4j.debug -Dlog4j.configuration=log4j.txt au.com.polly.ddr.ui.SimplePlotGrapherHarness "${DATA_DIR}/${FILE_NAME}"
