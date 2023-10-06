# Performance By Example

This repository contains supportive materials for a performance testing workshop. It includes Gatling tests written in
Java and run with Maven. To make the test execution process easier and more consistent, Docker is used to encapsulate
the testing environment.

## Personal Access Tokens Setup

Tests authenticate using Personal Access Tokens (PATs). You can set them up
following [these steps](https://confluence.atlassian.com/enterprise/using-personal-access-tokens-1026032365.html).

Use the curl command below to generate a PAT:

```
curl -X POST <JIRA_BASE_URL>/rest/pat/latest/tokens \
-H "Content-Type: application/json" \
-d '{"name": "tokenName","expirationDuration": 90}' \
--user "<user>:<password>" | jq -r '.rawToken'
```  

## Running Tests with Docker

The project utilizes Docker to run the Gatling tests. This approach ensures a consistent environment and eliminates the
need to manage Java and Maven dependencies on your local machine. For an easier and more user-friendly experience, two
shell scripts, `testManager.sh` and `runTest.sh`, are provided.

### testManager.sh

This script serves as a front-end interface for managing the Docker image and running tests. To use it, simply run:

```./testManager.sh```

You will be presented with several options:

1. `🐳🔨 Rebuild Docker image`: Builds the Docker image with all necessary dependencies.
2. `🐳🚀 Run Gatling tests`: Runs the Gatling tests inside the Docker container.
3. `🐳🔨🚀 Rebuild Docker image and run Gatling tests`: Rebuilds the Docker image and runs the Gatling tests.
4. `☕🔨 Rebuild with Maven`: Rebuilds the application using Maven.
5. `☕🚀 Run tests with Maven`: Runs the tests using Maven.
6. `☕🔨🚀 Rebuild and run tests with Maven`: Rebuilds the application and runs the tests using Maven.
7. `⚙️ Setup environment variables`: Allows you to set up environment variables.

### Environment Variables

The `testManager.sh` script uses environment variables: **JIRA_BASE_URL**, **PERSONAL_ACCESS_TOKEN**, and *
*TEST_SCENARIO**. If these variables are set, their values will be used as parameters for the Gatling tests . If they're
not set, the script will prompt you to enter these values.

- **JIRA_BASE_URL**: The base URL of your Jira instance.
- **PERSONAL_ACCESS_TOKEN**: Your personal access token.
- **TEST_SCENARIO**: The name of the Gatling test scenario you wish to run.

We provide a `setupEnv.sh` script to help you manage these environment variables. This script will display the current
values and prompt you to enter new ones if desired.

To avoid manually entering these values every time, you can also set these environment variables in your shell's profile
file:

```bash
export JIRA_BASE_URL=<your_jira_base_url>
export PERSONAL_ACCESS_TOKEN=<your_personal_access_token>
export TEST_SCENARIO=<your_test_scenario>
```

## Locating Tests

The Gatling tests are located in a package named `simulations`, as defined by `PACKAGE_NAME=simulations` in
the `runTest.sh` script. This is the default package name where all the test scenarios are stored.

When specifying the `TEST_SCENARIO` environment variable or command-line option, you only need to provide the class name
of the test scenario you wish to run. The `runTest.sh` script will automatically prepend this with `PACKAGE_NAME` to
locate the correct test in the `simulations` package.

### runTest.sh

This script is used to execute the Gatling tests within the Docker container. It's called by `testManager.sh` and
doesn't typically need to be run directly by users.

## Test Reports

When tests are executed via `testManager.sh`, Gatling generates reports within the Docker container. These reports are
then copied from the Docker container to the `target` directory in your local project. You can access these reports by
opening the `index.html` file in a web browser from your local `target` directory.

## Manual Test Execution

If you prefer to run the tests manually, follow the steps below:

### Setup

Install with Maven wrapper:

```bash
./mvnw clean install -DskipTests
```

### Test Execution

To execute the tests, use the following command:

```bash
./mvnw gatling:test -Dgatling.simulationClass=<your_test_name>
```

Replace `<your_test_name>` with the name of the Gatling test scenario you wish to run.

Please ensure to replace all necessary parameters and values in the scripts and commands to match your specific testing
and environment setup.