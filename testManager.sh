#!/bin/bash

echo -e "Welcome to the Gatling Test Manager! This script is designed to help you build the Docker image and run Gatling tests.\n"
echo -e "💡 Pro Tip: You can set the JIRA_BASE_URL and PERSONAL_ACCESS_TOKEN environment variables to skip the input prompts."
echo -e "Please select an option:"
echo "1: 🏗️ Rebuild Docker image"
echo "2: 🚀 Run Gatling tests"
echo "3: 🏗️ + 🚀 Rebuild Docker image and run Gatling tests"

read -r option

# get value from the user or use environment variable
get_value() {
    local var_name="$1"
    local prompt_message="$2"

    if [ -n "${!var_name}" ]; then
        if [ "$var_name" = "PERSONAL_ACCESS_TOKEN" ]; then
            echo "ℹ️ Using environment variable $var_name: ${!var_name:0:4}****${!var_name: -4}"
        else
            echo "ℹ️ Using environment variable $var_name: ${!var_name}"
        fi
    else
        echo "$prompt_message"
        read -r val
        export "$var_name=$val"
    fi
}

build_docker_image() {
    echo "🏗️ Rebuilding Docker image..."
    docker build -t gatling-test .
    build_status=$?
    if [ $build_status -ne 0 ]; then
        echo "❌ Docker image build failed, not running tests."
        exit 1
    fi
}

run_tests() {
    get_value "JIRA_BASE_URL" "Please enter the Jira base URL:"
    get_value "PERSONAL_ACCESS_TOKEN" "Please enter the personal access token:"
    get_value "TEST_SCENARIO" "Please enter the test scenario name:"

    echo "🚀 Running Gatling tests..."
    docker run -v "$(pwd)"/target:/app/target -it --rm gatling-test ./runTest.sh -b "$JIRA_BASE_URL" -t "$PERSONAL_ACCESS_TOKEN" -s "$TEST_SCENARIO"
}

case $option in
    1)
    build_docker_image
    ;;
    2)
    run_tests
    ;;
    3)
    build_docker_image
    run_tests
    ;;
    *)
    echo "❌ Invalid option selected."
    ;;
esac
