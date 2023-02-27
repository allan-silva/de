FROM eclipse-temurin:17

MAINTAINER allan-silva <allan@allansilva.com.br>

RUN apt-get update && apt-get install -y curl supervisor

ENV SCALA_HOME=/opt/scala
ENV SPARK_HOME=/opt/spark


# Create Scala and Spark home.

RUN mkdir $SCALA_HOME $SPARK_HOME


# Download Scala and Spark.

RUN curl -o scala-2.12.17.tgz -fL https://downloads.lightbend.com/scala/2.12.17/scala-2.12.17.tgz
RUN curl -o spark-3.3.2-bin-hadoop3.tgz -fL https://dlcdn.apache.org/spark/spark-3.3.2/spark-3.3.2-bin-hadoop3.tgz


# Install Scala

RUN tar --strip-components=1 -xvf scala-2.12.17.tgz -C $SCALA_HOME

RUN rm scala-2.12.17.tgz


# Install Spark

RUN tar --strip-components=1 -xvf spark-3.3.2-bin-hadoop3.tgz -C $SPARK_HOME

RUN rm spark-3.3.2-bin-hadoop3.tgz


# Setup PATH
ENV PATH=$PATH:$SCALA_HOME/bin:$SPARK_HOME/bin:$SPARK_HOME/sbin

# Setup entrypoint
ENV WORKDIR=/opt/entrypoint
RUN mkdir $WORKDIR
COPY ./start-spark.sh $WORKDIR
COPY ./supervisor.conf $WORKDIR
RUN chmod +x $WORKDIR/start-spark.sh
WORKDIR $WORKDIR

EXPOSE 7077 8080

ENTRYPOINT ["./start-spark.sh"]
