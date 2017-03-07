#!/bin/bash

echo "This Scripte Executable File:$0"

echo "Argument Count: $#"

echo "Argument List \$* : $*"

echo "Argument List \$@ : $@"

echo "Argument 1 : $1"

echo "Argument 2 : $2"

javac $2.java

java $2

