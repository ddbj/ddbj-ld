import {combineReducers} from 'redux';

import auth from './auth';
import app from './app';
import admin from './admin';
import me from './me';
// import alerts from './alerts';
import layout from './layout';
import projectBook from './projectBook';
import search from './search';
import i18n from './i18n';
import localforage from "localforage"
import {persistReducer} from 'redux-persist';
// import products from './products';
// import register from './register';
// import analytics from './analytics';
// import chat from './chat';

const persistConfig = {
    key: 'root/202007201217',
    storage: localforage,
    blacklist: ['app', 'me']
};

const appReducers = combineReducers({
    app,
    admin,
    me,
    // alerts,
    auth,
    layout,
    // products,
    // register,
    // analytics,
    // chat
    projectBook,
    search,
    i18n,
});

const rootReducers = (state, action) => {
    if (action.type === "LOGOUT") {
        state = undefined;
    }
    return appReducers(state, action);
};

export default persistReducer(persistConfig, rootReducers);
