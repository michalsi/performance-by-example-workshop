TOKEN=
BASE_URL=
TEST_SCENARIO=EditSprintsTest

function help() {
  echo "$1
    Performance tests runner
       Usage:
            ./runTest.sh [OPTIONS]

          Options:
              -b    Base url,
              -t    Personal Authorisation Token
              -s    Test Scenario
              -h    Display this help message

       Example:
      ./runTest.sh -b  https://dummy.instenv.internal.atlassian.com  -t NTU1Mzg2MjQ5MTcwOuJgOa4zUNf6wVQy2cGVISU9QVCa -s EditSprintsTest
      "
}

while getopts b:t:s:h flag; do
  case "${flag}" in
  b) BASE_URL=${OPTARG} ;;
  t) TOKEN=${OPTARG} ;;
  s) TEST_SCENARIO=${OPTARG} ;;
  h) help && exit 0 ;;
  *) help && exit 1 ;;
  esac
done

if [[ -z ${BASE_URL} || -z ${TOKEN} || -z ${TEST_SCENARIO} ]]; then
  help "Missing required arguments" && exit 1
fi

./mvnw gatling:test \
    -Dgatling.simulationClass="${TEST_SCENARIO}" \
    -Durl="${BASE_URL}" \
    -Dtoken="${TOKEN}"