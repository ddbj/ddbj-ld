#!/bin/sh

mkdir logs
chmod +t logs
mkdir -p data/public_db data/jvar_db data/elasticsearch data/opendj  data/openam data/file
chmod -R +t data
