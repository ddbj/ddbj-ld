import React from 'react'
import {Redirect} from 'react-router-dom'

import {useSignOut} from "../../hooks/auth"

const SignOut = () => {
    const {isAuthorized} = useSignOut()

    if (!isAuthorized) return <Redirect to="/"/>

    return (
        <div>ログアウト中</div>
    )
}

export default SignOut