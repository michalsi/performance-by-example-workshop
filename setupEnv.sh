#!/bin/bash

var_names=("JIRA_BASE_URL" "PERSONAL_ACCESS_TOKEN" "TEST_SCENARIO")
current_values=("${JIRA_BASE_URL}" "${PERSONAL_ACCESS_TOKEN}" "${TEST_SCENARIO}")
new_values=()

get_value() {
    local var_name="${var_names[$1]}"
    local current_value="${current_values[$1]}"
    local prompt_message="Please enter the new value for $var_name (leave blank to keep the current value):"

    if [ -n "$current_value" ]; then
        if [ "$var_name" = "PERSONAL_ACCESS_TOKEN" ]; then
            echo "ℹ️ Current value of $var_name: ${current_value:0:4}****${current_value: -4}"
        else
            echo "ℹ️ Current value of $var_name: $current_value"
        fi
    fi

    echo "$prompt_message"
    read -r val
    if [ -z "$val" ]; then
        echo "✔️ Keeping the current value for $var_name."
        new_values+=("$current_value")
    else
        new_values+=("$val")
    fi
}

for i in "${!var_names[@]}"; do
    get_value "$i"
done

echo -e "\n⚠️ To set the new values for the environment variables, run the following commands:"
for i in "${!var_names[@]}"; do
    echo "export ${var_names[$i]}=${new_values[$i]}"
done