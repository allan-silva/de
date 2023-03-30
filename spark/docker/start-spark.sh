#!/bin/bash

if test "$1" = "main-node"
then 
    echo "Starting main node"
    $SPARK_HOME/sbin/start-master.sh
    supervisord -c supervisor.conf
elif test "$1" = "worker-node"
then
    echo "Start worker node - Main host: $MAIN_HOST"
    $SPARK_HOME/sbin/start-worker.sh spark://$MAIN_HOST:7077
    supervisord -c supervisor.conf
elif test "$1" = "spark-submit"
then
    echo "Runnig command: spark-submit --master spark://$MAIN_HOST:7077 ${@:2}"
    $SPARK_HOME/bin/spark-submit ${@:2}
elif test "$1" = "history-server"
then
    $SPARK_HOME/sbin/start-history-server.sh
    supervisord -c supervisor.conf
elif test "$1" = "shell"
then
    /bin/bash
else
    echo "Wrong parameters $1"
fi
