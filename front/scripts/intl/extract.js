/* eslint-disable no-console */
const {
  writeFileSync,
  mkdirSync,
} = require('fs');

const { dirname } = require('path');

const { stringify } = require('csv-stringify/sync');

const {
  languages,
  additional_message_ids: additionalMessageIds
} = require('./config.json');

const {
  getMapFilePath,
  getDefeinedAllMessages,
  getExistedMessageIds
} = require('./utils');

const MESSAGE_ID_PATTERN = /^[a-z0-9_.]+$/;

async function extract () {
  const definedAllMessages = getDefeinedAllMessages();
  const existedMessageIds = await getExistedMessageIds();
  const messageIds = [...additionalMessageIds, ...existedMessageIds].sort();

  messageIds.forEach(id => {
    if (MESSAGE_ID_PATTERN.test(id)) return;
    console.info(`Invalid message id: ${id}`);
  });

  const data = messageIds.map(id => ({
    id,
    ...languages.reduce((messages, language) => {
      const message = definedAllMessages[id] ? (definedAllMessages[id][language] || '') : '';
      return {
        ...messages,
        [language]: message
      };
    }, {})
  }));

  const mapFilePath = getMapFilePath();
  const csv = stringify(data, { header: true });

  mkdirSync(dirname(mapFilePath), { recursive: true });
  writeFileSync(mapFilePath, csv);
}

extract();
