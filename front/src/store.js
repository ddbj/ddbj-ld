import { configureStore as rtkConfigureStore, combineReducers } from '@reduxjs/toolkit';
import { setupListeners } from '@reduxjs/toolkit/query';

import {
  persistReducer,
  FLUSH,
  REHYDRATE,
  PAUSE,
  PERSIST,
  PURGE,
  REGISTER
} from 'redux-persist';
import storage from 'redux-persist/lib/storage';

import appApi from './services/appApi';
import navigation from './slices/navigationSlice';

export function configureStore (options = {}) {
  const persistConfig = {
    storage,
    key: 'root',
  };

  const store = rtkConfigureStore({
    reducer: persistReducer(persistConfig, combineReducers({
      navigation,
      [appApi.reducerPath]: appApi.reducer
    })),
    middleware: (getDefaultMiddleware) => getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
      }
    }).concat(
      appApi.middleware
    ),
    ...options,
  });

  setupListeners(store.dispatch);

  return store;
}

export const store = configureStore();
