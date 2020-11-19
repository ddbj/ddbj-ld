#!/bin/bash

if [ ! -e "/opt/opendj/db/userRoot" ]; then
  rm -f /opt/opendj/config/schema/99-user.ldif

  /opt/opendj/setup \
    --cli \
    --baseDN ou=users,dc=general,dc=member,dc=account,dc=ddbj,dc=nig,dc=ac,dc=jp \
    --sampleData 5 \
    --ldapPort 1389 \
    --adminConnectorPort 4444 \
    --rootUserDN cn=Directory\ Manager \
    --rootUserPassword $OPENDJ_PASS \
    --enableStartTLS \
    --ldapsPort 1636 \
    --generateSelfSignedCertificate \
    --no-prompt \
    --noPropertiesFile

cp -p /opt/opendj/99-user.ldif /opt/opendj/config/schema/
fi

/opt/opendj/bin/stop-ds
/opt/opendj/bin/start-ds --nodetach
