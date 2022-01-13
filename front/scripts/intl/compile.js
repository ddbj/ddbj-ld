/* eslint-disable no-console */
const { writeFileSync } = require('fs');
const { resolve } = require('path');

const {
  getMessageFilePath,
  getMessagesFromMapFile
} = require('./utils');

const {
  languages
} = require('./config.json');

async function compile () {
  languages.forEach(language => {
    const messages = getMessagesFromMapFile(language);
    const json = JSON.stringify(messages, null, 4);

    writeFileSync(
      resolve(__dirname, getMessageFilePath(language)),
      json,
      { encoding: 'utf-8' }
    );

    Object.entries(messages).forEach(([key, message]) => {
      if (message !== '') return;
      console.info(`${language}/${key} is not defined.`);
    });
  });
}

compile();
