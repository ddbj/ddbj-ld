import { Provider } from 'react-redux';
import { persistStore  } from 'redux-persist';
import { PersistGate } from 'redux-persist/integration/react';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import '../styles/globals.scss';

import store from '@/store';
import { NextIntlProvider } from '@/hooks/intl';
import Layout from '@/components/features/Layout';

const persistor = persistStore(store);
export default function App ({ Component, pageProps }) {
  return (
    <NextIntlProvider>
      <Provider store={store}>
        <PersistGate loading={null} persistor={persistor}>
          <Layout>
            <ToastContainer />
            <Component {...pageProps} />
          </Layout>
        </PersistGate>
      </Provider>
    </NextIntlProvider>
  );
}
