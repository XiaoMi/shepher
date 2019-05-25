FROM mysql:5.7.26
COPY init.sql /docker-entrypoint-initdb.d/
COPY --from=healthcheck/mysql /usr/local/bin/docker-healthcheck /usr/local/bin/

HEALTHCHECK CMD ["docker-healthcheck"]