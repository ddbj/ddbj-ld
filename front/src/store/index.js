import {applyMiddleware, compose, createStore} from 'redux';
import reducers from '../reducers';
import {persistStore} from 'redux-persist';
import createSagaMiddleware from 'redux-saga';
import sagas from '../sagas';

const composeEnhancers = process.env.NODE_ENV === 'development' && window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
    ? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
    : compose;

const sagaMiddleware = createSagaMiddleware();

const configureStore = () => {
    const store = createStore(
        reducers,
        composeEnhancers(applyMiddleware(sagaMiddleware))
    );

    sagaMiddleware.run(sagas);

    store.persistor = persistStore(store);

    return store;
};

export default configureStore();
