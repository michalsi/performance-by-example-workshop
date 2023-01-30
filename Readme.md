# Performance By Example
Supportive materials for performance testing workshop

## How do I get set up? ##

Install with maven wrapper:
`./mvn clean install -DskipTests`

### Set up Personal Access Token ###

Tests authenticate with Personal access tokens (PATs)

[Here](https://confluence.atlassian.com/enterprise/using-personal-access-tokens-1026032365.html) are the steps how to set it up.


## How to run tests ##

Raw Gatling execution command:

`./mvnw gatling:test -Dgatling.simulationClass=BasicUserJourneyTest`

### Handy script for execution ##

Use `runTest.sh` to make your life easier:

`./runTest.sh -b  <jira_base_url>  -t <personal_access_token>`
