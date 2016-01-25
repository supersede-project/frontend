#!/bin/bash
user="$1"
shift
PGPASSWORD="$1"
shift

while [ "$1" != "" ]; do
	psql -U $user $1 < schema.sql
    shift
done