FROM openjdk:17-bullseye
# TODO by suecharo FROM openjdk:23-bookworm などに変更したい (17-bookworm は存在しない)

RUN apt update && \
    apt install -y --no-install-recommends \
    curl && \
    apt clean && \
    rm -rf /var/lib/apt/lists/*

COPY ./entrypoint.sh /usr/local/bin/entrypoint.sh

WORKDIR /api

ENTRYPOINT []
CMD ["bash", "/usr/local/bin/entrypoint.sh"]
