FROM postgres:9.6-alpine
ENV POSTGRES_USER=postgres
ENV POSTGRES_DB=jrvstrading

COPY init.sql /docker-entrypoint-initdb.d
