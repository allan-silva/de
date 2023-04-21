## Submit command

`docker run --rm --network spark_sparknet -v /tmp/spark:/spark_shared learn-spark spark-submit --class mms.MnMCount /spark_shared/apps_jars/m-ms-count_2.12-1.0.jar`
