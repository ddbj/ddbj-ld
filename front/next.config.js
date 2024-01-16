const path = require('path');

/** @type {import('next').NextConfig} */
module.exports = {
  swcMinify: false,
  reactStrictMode: false,
  output: 'standalone',
  i18n: {
    locales: ['ja', 'en'],
    defaultLocale: 'ja',
  },
  sassOptions: {
    includePaths: [
      path.join(__dirname, 'src/styles')
    ]
  },
  basePath: process.env.NEXT_BASE_PATH || "",
};
