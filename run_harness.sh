#!/bin/ksh

PROJECT_DIR=/Users/dave/projects/ddr
DATA_DIR=/Users/dave/documents/work/nick
#FILE_NAME="Production Allocation_20110731_NICK.xlsx"
SHEET_NAME="Allocated"
HEAP_SIZE="4096m"
FILE_NAME="JustAllocation.xlsx"

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

java -Xmx${HEAP_SIZE} -Dlog4j.debug -Dlog4j.configuration=log4j.txt au.com.polly.ddr.AllocationSheetExplorerHarness "${DATA_DIR}/${FILE_NAME}" ${SHEET_NAME}
