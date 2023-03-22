TOKEN=
BASE_URL=https://<id>.instenv.internal.atlassian.com
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

       Example:
      ./runTest.sh -b  https://dummy.instenv.internal.atlassian.com  -t NTU1Mzg2MjQ5MTcwOuJgOa4zUNf6wVQy2cGVISU9QVCa -s EditSprintsTest
      "
}

#if [ $# -eq 0 ]; then
#  help "$@" && exit
#fi

while getopts b:t:s: flag; do
  case "${flag}" in
  b) BASE_URL=${OPTARG} ;;
  t) TOKEN=${OPTARG} ;;
  s) TEST_SCENARIO=${OPTARG} ;;
  *) help && exit 1 ;;
  esac
done


./mvnw gatling:test \
    -Dgatling.simulationClass="${TEST_SCENARIO}" \
    -Durl="${BASE_URL}" \
    -Dtoken="${TOKEN}"
