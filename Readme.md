# Performance By Example
Supportive materials for performance testing workshop

### Set up Personal Access Token ###

Tests authenticate with Personal access tokens (PATs)

[Here](https://confluence.atlassian.com/enterprise/using-personal-access-tokens-1026032365.html) are the steps how to set it up.
```
curl -X POST <JIRA_BASE_URL>/rest/pat/latest/tokens \
-H "Content-Type: application/json" \
-d '{"name": "tokenName","expirationDuration": 90}' \
--user "<user>:<password>" | jq -r '.rawToken'
```

## Running Gatling Tests with Docker  ##
This project uses Docker to run Gatling tests, ensuring a consistent environment and eliminating the need to manage Java and Maven dependencies on your local machine. We have provided two shell scripts, testManager.sh and runTest.sh, to make this process even simpler.

### testManager.sh ###
This script serves as a user-friendly interface for managing the Docker image and running tests.

```./testManager.sh```
When you run this script, you'll be given three options:

- `🏗️ Rebuild Docker image`: Build the Docker image, pulling in all necessary dependencies.
- `🚀 Run Gatling tests`: Run your Gatling tests inside the Docker container.
- `🏗️ + 🚀 Rebuild Docker image and run Gatling tests` : Rebuild the Docker image and then run the Gatling tests.

### Environment Variables ###

The script uses two environment variables, **JIRA_BASE_URL** and **PERSONAL_ACCESS_TOKEN**.

- **JIRA_BASE_URL**: Base URL of your Jira instance.
- **PERSONAL_ACCESS_TOKEN**: Your personal access token.
- **TEST_SCENARIO**: Name of the Gatling test scenario you wish to run.

If these environment variables are set when the script is run, it will use their values as parameters for the Gatling tests. If they're not set, the script will prompt you to enter these values. To avoid entering these values every time, you can set these environment variables in your shell's profile file.

```
export JIRA_BASE_URL=<your_jira_base_url>
export PERSONAL_ACCESS_TOKEN=<your_personal_access_token>
export TEST_SCENARIO=<your_test_scenario>
```
Replace <your_value> with your actual values.

### runTest.sh ###
This script is used to execute the Gatling tests within the Docker container. It's called by testManager.sh and doesn't typically need to be run directly by users.

Please remember to replace all necessary parameters and values in the scripts and commands to match your particular testing and environment setup.

### Test Reports Access ###

When tests are executed via testManager.sh, Gatling reports are automatically saved in the target directory of your local project due to a shared Docker volume. You can view these reports by opening the index.html file in a web browser. Be aware that changes in the local target directory are mirrored in the Docker container, and vice versa.

## How to run tests manually ##

### How do I get set up? ###

Install with maven wrapper:
`./mvn clean install -DskipTests`

### Test Execution ###

Raw Gatling execution command:

`./mvnw gatling:test -Dgatling.simulationClass=<your_test_name>`