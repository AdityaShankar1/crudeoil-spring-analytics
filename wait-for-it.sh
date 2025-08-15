#!/bin/sh
# wait-for-it.sh: wait for a host and port to be ready
# Usage: ./wait-for-it.sh db 3306
host="$1"
port="$2"

until nc -z "$host" "$port"; do
  echo "Waiting for $host:$port..."
  sleep 2
done

echo "$host:$port is up"
exec "${@:3}"