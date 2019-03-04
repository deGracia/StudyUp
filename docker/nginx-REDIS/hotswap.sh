#!/usr/bin/env bash
set -e

# error when no NEW_HOST parameter is passed
NEW_HOST="$1"
if [[ -z "${NEW_HOST}" ]]; then
    echo "Usage: $0 NEW_HOST" >&2
    exit 1
fi

# check whether the passed parameter is the same as what is already being used
# print an informative warning message without invoking a reload on nginx if that's not. 

OLD_HOST="$(grep -oE 'server +[a-zA-Z0-9_.]+:' /etc/nginx/nginx.conf | grep -oE '[a-zA-Z0-9_.]+:' | grep -oE '[a-zA-Z0-9_.]+')"

if [[ "${OLD_HOST}" = "${NEW_HOST}" ]]; then
    echo "The current host is already ${OLD_HOST}" >&2
    exit 0
fi

# sed find and replace text
sed -i "s/server \+[a-zA-Z0-9_.]\+:/server ${NEW_HOST}:/g" /etc/nginx/nginx.conf

# reload the configuration to redirect nginx
/usr/sbin/nginx -s reload

