#!/bin/bash

# Ensure you have Java 8 installed before running this script
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)

# Compile the project
javac -cp ./lib/acm.jar -d ./bin ./src/*.java

# Run the project
java -cp ./lib/acm.jar:./bin BoggleGraphics
