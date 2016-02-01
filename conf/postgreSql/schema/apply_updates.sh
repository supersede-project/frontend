#!/bin/bash
user="$1"
shift
export PGPASSWORD="$1"
shift

while [ "$1" != "" ]; do
	find . -type f -iname "update*.sql" -print0 | while IFS= read -r -d $'\0' line; do
		echo "apply $line to $1" 
		psql -U $user -f "$line" $1
	done
	shift
done
