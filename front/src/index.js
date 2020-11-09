import React from 'react'
import ReactDOM from 'react-dom'
import {Provider} from 'react-redux'

import * as serviceWorker from './serviceWorker'
import store from './store'
import App from './components/App'
import {Provider as IntlProvider} from './i18n'

// import axios from 'axios'
// import config from './config'
// axios.defaults.baseURL = config.baseURLApi
// axios.defaults.headers.common['Content-Type'] = "application/json"
// const token = localStorage.getItem('token')
// if (token) {
//   axios.defaults.headers.common['Authorization'] = "Bearer " + token
// }

ReactDOM.render(
    <Provider store={store}>
        <IntlProvider>
            <App/>
        </IntlProvider>
    </Provider>,
    document.getElementById('root')
)

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister()
