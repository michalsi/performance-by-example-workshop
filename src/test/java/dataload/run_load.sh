#!/bin/bash

db_host="10.227.133.5"
db_port="5432"
db_name="atldb"
db_user="atldbuser"
db_password="kQiwyt6Y,6jPDIYf5J0fWmAJ"
num_rows="100000"

./create_sprints-file.sh -h "$db_host" -p "$db_port" -d "$db_name" -u "$db_user" -w "$db_password" -n "$num_rows"
