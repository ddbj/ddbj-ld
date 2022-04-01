import { Provider } from 'react-redux';
import { SessionProvider } from 'next-auth/react';
import { persistStore  } from 'redux-persist';
import { PersistGate } from 'redux-persist/integration/react';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import '../styles/style.scss';

import { RESPONSE_STATUS } from '../constants';

import { store } from '../store';

import ErrorPage from '../pages/_error';
import { NextIntlProvider } from '../components/features/NextIntl';

import Layout from '../components/features/Layout';
import RefreshToken from '../components/features/auth/RefreshToken';

function HandlePageComponent ({ status = RESPONSE_STATUS.OK, children }) {
  if (status === RESPONSE_STATUS.OK) {
    return children;
  }
  return <ErrorPage status={status} />;
}

const persistor = persistStore(store);

export default function App ({
  Component,
  pageProps: {
    session,
    ...pageProps
  }
}) {
  return (
    <SessionProvider session={session}>
      <NextIntlProvider>
        <Provider store={store}>
          <PersistGate loading={null} persistor={persistor}>
            <RefreshToken />
            <ToastContainer />
            <HandlePageComponent {...pageProps}>
              <Layout>
                <Component {...pageProps} />
              </Layout>
            </HandlePageComponent>
          </PersistGate>
        </Provider>
      </NextIntlProvider>
    </SessionProvider>
  );
}
