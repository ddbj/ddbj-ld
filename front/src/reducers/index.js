import {combineReducers} from 'redux';

import auth from './auth';
import app from './app';
import me from './me';
import layout from './layout';
import i18n from './i18n';
import entry from './entry';
import storage from "localforage";
import {persistReducer} from 'redux-persist';

const persistConfig = {
    key: 'root/202007201217',
    storage,
    blacklist: ['app', 'me']
};

const appReducers = combineReducers({
    app,
    auth,
    layout,
    i18n,
    entry
});

const rootReducers = (state, action) => {
    if (action.type === "LOGOUT") {
        state = undefined;
    }
    return appReducers(state, action);
};

export default persistReducer(persistConfig, rootReducers);
