const path = require('path');

module.exports = {
  swcMinify      : false,
  reactStrictMode: false,
  i18n           : {
    locales      : ['ja', 'en'],
    defaultLocale: 'ja',
  },
  sassOptions: {
    includePaths: [
      path.join(__dirname, 'src/styles')
    ]
  }
};
