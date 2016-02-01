#!/bin/bash
user="$1"
shift
export PGPASSWORD="$1"
shift

while [ "$1" != "" ]; do
	echo "restore $1"
	psql -U "$user" -f "schema.sql" "$1"
    shift
done
