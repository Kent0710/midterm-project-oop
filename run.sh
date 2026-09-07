#!/bin/bash
set -e

# Change directory to the script's directory
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

# Create output directory if it doesn't exist
mkdir -p out

# Compile all Java source files
javac -d out src/*.java

# Run the Main entry point
java -cp out Main "$@"
