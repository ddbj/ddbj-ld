import BaseDocument, { Html, Head, Main, NextScript } from 'next/document';

export default class Document extends BaseDocument {
  render () {
    return (
      <Html>
        <Head>
          <link rel="icon" href="/favicon.ico" />
        </Head>
        <body className="ddbj-app">
          <div className="ddbj-app__main">
            <Main />
          </div>
          <script async id="DDBJ_common_framework" src="https://www-preview.ddbj.nig.ac.jp/assets/js/ddbj_common_framework.js" />
          <NextScript />
        </body>
      </Html>
    );
  }
}
