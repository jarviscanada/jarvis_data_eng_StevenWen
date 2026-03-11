#!/bin/bash

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5


# Check # of args
if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

hostname=$(hostname -f)

lscpu_out=`lscpu`
vmstat_mb=$(vmstat --unit M)

cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | egrep "^Architecture:" | awk '{print $2}')
cpu_model=$(echo "$lscpu_out" | egrep "^Model name:" | cut -d: -f2 | xargs) #cut -d: -f2 ? splits by : and keeps the right side

cpu_mhz=$(echo "$lscpu_out" | egrep "^Model name:" \
  | egrep -o '[0-9]+\.[0-9]+GHz' \
  | sed 's/GHz//' \
  | awk '{printf "%.0f", $1 * 1000}'
)

l2_cache=$(echo "$lscpu_out" | egrep "^L2 cache:" | awk '{print $3}'|xargs)
total_mem=$(echo "$vmstat_mb" | tail -1 | awk '{print $4}')
timestamp=$(date +"%Y-%m-%d %H:%M:%S")

insert_stmt="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, total_mem, \"timestamp\")
VALUES('$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, $l2_cache, $total_mem, '$timestamp');"

#set up env var for pql cmd
export PGPASSWORD=$psql_password
#Insert date into a database
psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_stmt"

exit $?

