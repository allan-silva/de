#!/bin/bash

set -e

cd demo-sql

echo "******************************************"
echo " Downloading demo database $DATABASE_SIZE"
echo "******************************************"

if test "$DATABASE_SIZE" = small
then
    file="demo-small-en.zip"
elif test "$DATABASE_SIZE" = medium
then
    file="demo-medium-en.zip"
elif test "$DATABASE_SIZE" = big
then
    file="demo-big-en.zip"
fi

wget --show-progress "https://edu.postgrespro.com/$file"

unzip $file

rm $file

db_file=$(find . -type f -name "*.sql")

echo "******************************************"
echo " Importing demo database $db_file"
echo "******************************************"

psql -f $db_file -U postgres

echo "******************************************"
echo " Process finished"
echo "******************************************"
