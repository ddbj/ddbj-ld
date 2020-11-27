import React, {useEffect} from 'react'
<<<<<<< HEAD
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import {ToastContainer} from 'react-toastify'
import {useIsReady} from '../hooks/app'
import '../styles/theme.scss'
=======
import {BrowserRouter as Router, Redirect, Route, Switch} from 'react-router-dom'
import {ToastContainer} from 'react-toastify'
import {useIsReady} from '../hooks/app'
import {useIsAuthorized} from '../hooks/auth'
import '../styles/theme.scss'
import SignOut from '../pages/signout'
>>>>>>> 差分修正
import LayoutComponent from '../components/Layout'

const CloseButton = ({closeToast}) =>
    (<i onClick={closeToast} className="la la-close notifications-close"/>)

<<<<<<< HEAD
=======
const AuthorizedRoute = props => {
    const isAuthorized = useIsAuthorized()
    return isAuthorized ? <Route {...props} /> : <Redirect to="/"/>
}

>>>>>>> 差分修正

const App = () => {
    const isReady = useIsReady()

    // DDBJヘッダの取り付け
    useEffect(() => {
        const script = document.createElement("script")
<<<<<<< HEAD
        script.src = "https://www.ddbj.nig.ac.jp/assets/js/ddbj_common_framework.js"
=======
        script.src = "https://www-preview.ddbj.nig.ac.jp/assets/js/ddbj_common_framework.js"
>>>>>>> 差分修正
        script.id="DDBJ_common_framework"
        script.style=""
        document.body.prepend(script)
    }, [])

    if (!isReady) return null
<<<<<<< HEAD

=======
>>>>>>> 差分修正
    return (
        <>
            <ToastContainer
                autoClose={5000}
                hideProgressBar
                closeButton={<CloseButton/>}
            />
            <Router>
                <Switch>
<<<<<<< HEAD
=======
                    <AuthorizedRoute path="/signout" component={SignOut}/>
>>>>>>> 差分修正
                    <Route path="*" component={LayoutComponent}/>
                </Switch>
            </Router>
        </>
    )
}

export default App
