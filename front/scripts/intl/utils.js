/* eslint-disable no-console */
const { readFileSync } = require('fs');
const { resolve } = require('path');

const glob = require('glob');
const parse = require('csv-parse/lib/sync');
const { extract } = require('@formatjs/cli');

const {
  source,
  languages,
  map_file: mapFile,
  intl_directory: intlDirectory
} = require('./config.json');

function getMapFilePath () {
  return resolve(__dirname, mapFile);
}
exports.getMapFilePath = getMapFilePath;

function getMessageFilePath(language) {
  return resolve(__dirname, intlDirectory, `${language}.json`);
}
exports.getMessageFilePath = getMessageFilePath;

function getDefeinedMessages(language) {
  const messageFilePath = getMessageFilePath(language);
  const json = readFileSync(messageFilePath);
  const definedMessages = JSON.parse(json);
  return definedMessages;
}

exports.getDefeinedAllMessages = function getDefeinedAllMessages() {
  const allMessages = {};

  languages.forEach(language => {
    const messages = getDefeinedMessages(language);

    Object.entries(messages).forEach(([key, message]) => {
      allMessages[key] = allMessages[key] || {};
      allMessages[key][language] = message;
    });
  });

  return allMessages;
};

exports.getExistedMessageIds = async function getExistedMessageIds() {
  const pattern = resolve(__dirname, source);
  const entries = glob.sync(pattern);
  const json = await extract(entries, { throws: Error('@formatjs/cli error') });
  const ids = Object.keys(JSON.parse(json));
  return ids;
};

exports.getMessagesFromMapFile = function getMessagesFromMapFile(language) {
  const csv = readFileSync(getMapFilePath());

  return parse(csv, { columns: true })
    .reduce((messages, record) => {
      return {
        ...messages,
        [record.id]: record[language]
      };
    }, {});
};
