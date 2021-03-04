import React, {useEffect} from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import {ToastContainer} from 'react-toastify'
import {useIsReady} from '../hooks/app'
import '../styles/theme.scss'
import LayoutComponent from '../components/Layout'

const CloseButton = ({closeToast}) =>
    (<i onClick={closeToast} className="la la-close notifications-close"/>)


const App = () => {
    const isReady = useIsReady()

    // DDBJヘッダの取り付け
    useEffect(() => {
        const script = document.createElement("script")
        script.src = "https://www.ddbj.nig.ac.jp/assets/js/ddbj_common_framework.js"
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
                    <Route path="*" component={LayoutComponent}/>
                </Switch>
            </Router>
        </>
    )
}

export default App
