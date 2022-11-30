FROM postgres:15

ENV DATABASE_SIZE small

RUN apt-get update && apt-get install -y wget unzip

COPY ./init-demo.sh docker-entrypoint-initdb.d/

RUN chown postgres:postgres docker-entrypoint-initdb.d/init-demo.sh && chmod +rwx docker-entrypoint-initdb.d/init-demo.sh

RUN mkdir demo-sql && chown postgres:postgres demo-sql

CMD ["postgres"]
