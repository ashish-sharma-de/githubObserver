#!/bin/bash

# Stop execution if any command fails
set -e

# Building the project
echo "Building the project..."
./gradlew build

# Building and starting the Docker containers
echo "Building and starting Docker containers..."
docker-compose up --build

echo "Build and deployment completed successfully."