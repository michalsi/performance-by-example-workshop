TOKEN=
BASE_URL=

TEST_SCENARIO=BasicUserJourneyTest

function help() {
  echo "$1
    Performance tests runner
       Usage:
            ./runTest.sh [OPTIONS]

          Options:
              -b    base url,
              -t    Personal Authorisation Token

       Example:
      ./runTest.sh -b  https://dummy.instenv.internal.atlassian.com  -t NTU1Mzg2MjQ5MTcwOuJgOa4zUNf6wVQy2cGVISU9QVCa
      "
}

if [ $# -eq 0 ]; then
  help "$@" && exit
fi

while getopts b:t: flag; do
  case "${flag}" in
  b) BASE_URL=${OPTARG} ;;
  t) TOKEN=${OPTARG} ;;
  *) help && exit 1 ;;
  esac
done


./mvnw gatling:test \
    -Dgatling.simulationClass="${TEST_SCENARIO}" \
    -Durl="${BASE_URL}" \
    -Dtoken="${TOKEN}"
