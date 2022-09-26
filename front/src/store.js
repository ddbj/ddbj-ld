import { configureStore, combineReducers } from '@reduxjs/toolkit';
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

const persistConfig = {
  storage,
  key      : 'root',
  whiteList: []
};

const store = configureStore({
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
});

setupListeners(store.dispatch);

export default store;
