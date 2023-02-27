#!/bin/bash

if test "$1" = "main-node"
then 
    echo "Starting main node"
    start-master.sh
    supervisord -c supervisor.conf
else
    echo "Start worker node - Main host: $MAIN_HOST"
    start-worker.sh spark://$MAIN_HOST:7077
    supervisord -c supervisor.conf
fi
