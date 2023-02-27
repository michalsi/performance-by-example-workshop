#!/bin/bash

# Define usage function
function usage {
  echo "Usage: $0 [-h host] [-p port] [-d database] [-u username] [-w password] [-n num_rows]"
  echo "  -h: database host (default: localhost)"
  echo "  -p: database port (default: 5432)"
  echo "  -d: database name"
  echo "  -u: database username"
  echo "  -w: database password"
  echo "  -n: number of rows to insert (default: 1000)"
  exit 1
}

# Set default values
host=localhost
port=5432
num_rows=1000

# Parse command-line options
while getopts ":h:p:d:u:w:n:" opt; do
  case $opt in
    h) db_host="$OPTARG";;
    p) db_port="$OPTARG";;
    d) db_name="$OPTARG";;
    u) db_user="$OPTARG";;
    w) db_password="$OPTARG";;
    n) num_rows="$OPTARG";;
    \?) echo "Invalid option -$OPTARG" >&2; usage;;
  esac
done


# Verify that required parameters are present
if [[ -z "${db_host}" || -z "${db_port}" || -z "${db_name}" || -z "${db_user}" || -z "${db_password}" ]]; then
  usage
fi

# Check if psql is installed
if ! command -v psql &> /dev/null; then
  echo "psql is not installed. Please install it by running: brew install postgresql"
  exit 1
fi

# Get the current number of rows in the table
current_num_rows=$(PGPASSWORD="${db_password}" psql -h "${db_host}" -p "${db_port}" -U "${db_user}" -d "${db_name}" -t -c "SELECT COUNT(*) FROM public.\"AO_60DB71_SPRINT\";" | tr -d '[:space:]')
max_sprint_id=$(PGPASSWORD="${db_password}" psql -h "${db_host}" -p "${db_port}" -U "${db_user}" -d "${db_name}" -t -c "SELECT MAX(\"ID\") FROM public.\"AO_60DB71_SPRINT\";" | tr -d '[:space:]')
rapid_view_id=$(PGPASSWORD="${db_password}" psql -h "${db_host}" -p "${db_port}" -U "${db_user}" -d "${db_name}" -t -c "SELECT MAX(\"ID\") FROM public.\"AO_60DB71_RAPIDVIEW\";" | tr -d '[:space:]')

# Generate the insert statement
insert_statement="INSERT INTO public.\"AO_60DB71_SPRINT\"(\"CLOSED\", \"COMPLETE_DATE\", \"END_DATE\", \"GOAL\", \"ID\", \"NAME\", \"RAPID_VIEW_ID\", \"SEQUENCE\", \"STARTED\", \"START_DATE\", \"AUTO_START_STOP\", \"SYNCED\", \"ACTIVATED_DATE\") VALUES "

for ((i=1; i<=num_rows; i++)); do
  insert_statement+="(false, null, $((i+1677243204)), 'Goal $((i+max_sprint_id))', $((max_sprint_id + i)), 'Sprint $((i+max_sprint_id))', $rapid_view_id, null, true, null, false, false, null)"
  if [[ ${i} -ne ${num_rows} ]]; then
    insert_statement+=","
  fi
done
echo "Number of sprints before insertion: ${current_num_rows}"

# Run the insert statement
echo "Inserting  ${num_rows} new rows into database..."

PGPASSWORD="${db_password}" psql -h "${db_host}" -p "${db_port}" -U "${db_user}" -d "${db_name}" -c "${insert_statement}"

# Get the new number of rows in the table
new_num_rows=$(PGPASSWORD="${db_password}" psql -h "${db_host}" -p "${db_port}" -U "${db_user}" -d "${db_name}" -t -c "SELECT COUNT(*) FROM public.\"AO_60DB71_SPRINT\";" | tr -d '[:space:]')

# Display the results

echo "Number of sprints after insertion: ${new_num_rows}"