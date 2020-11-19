import React, {useEffect} from 'react'
import {BrowserRouter as Router, Redirect, Route, Switch} from 'react-router-dom'
import {ToastContainer} from 'react-toastify'
import {useIsReady} from '../hooks/app'
import {useIsAuthorized} from '../hooks/auth'
import '../styles/theme.scss'
import SignOut from '../pages/signout'
import LayoutComponent from '../components/Layout'

const CloseButton = ({closeToast}) =>
    (<i onClick={closeToast} className="la la-close notifications-close"/>)

const AuthorizedRoute = props => {
    const isAuthorized = useIsAuthorized()
    return isAuthorized ? <Route {...props} /> : <Redirect to="/"/>
}


const App = () => {
    const isReady = useIsReady()

    // DDBJヘッダの取り付け
    useEffect(() => {
        const script = document.createElement("script")
        script.src = "https://www-preview.ddbj.nig.ac.jp/assets/js/ddbj_common_framework.js"
        script.id="DDBJ_common_framework"
        script.style=""
        document.body.prepend(script)
    }, [])

    if (!isReady) return null
    return (
        <>
            <ToastContainer
                autoClose={5000}
                hideProgressBar
                closeButton={<CloseButton/>}
            />
            <Router>
                <Switch>
                    <AuthorizedRoute path="/signout" component={SignOut}/>
                    <Route path="*" component={LayoutComponent}/>
                </Switch>
            </Router>
        </>
    )
}

export default App
