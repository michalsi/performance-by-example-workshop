#!/bin/bash

TOKEN=
BASE_URL=
TEST_SCENARIO=
PACKAGE_NAME=simulations # Default package name

function help() {
  echo "$1
    Performance tests runner
       Usage:
            ./runTest.sh [OPTIONS]

          Options:
              -b    Base url,
              -t    Personal Authorisation Token
              -s    Test Scenario (class name)
              -p    Package name (default: simulations)
              -h    Display this help message

       Example:
      ./runTest.sh -b  https://dummy.instenv.internal.atlassian.com  -t NTU1Mzg2MjQ5dfgsdfgMTcwa -s EditSprintsTest
      "
}

while getopts b:t:s:p:h flag; do
  case "${flag}" in
  b) BASE_URL=${OPTARG} ;;
  t) TOKEN=${OPTARG} ;;
  s) TEST_SCENARIO=${OPTARG} ;;
  p) PACKAGE_NAME=${OPTARG} ;;
  h) help && exit 0 ;;
  *) help && exit 1 ;;
  esac
done

if [[ -z ${BASE_URL} || -z ${TOKEN} || -z ${TEST_SCENARIO} ]]; then
  help "Missing required arguments" && exit 1
fi

echo "Running test scenario: ${PACKAGE_NAME}.${TEST_SCENARIO}"

echo "Executing command: ./mvnw gatling:test -Dgatling.simulationClass=\"${PACKAGE_NAME}.${TEST_SCENARIO}\" -Durl=\"${BASE_URL}\" -Dtoken=\"${TOKEN}\""
./mvnw gatling:test -Dgatling.simulationClass="${PACKAGE_NAME}.${TEST_SCENARIO}" -Durl="${BASE_URL}" -Dtoken="${TOKEN}"
