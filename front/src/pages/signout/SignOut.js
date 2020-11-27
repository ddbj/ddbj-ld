import React from 'react'
import {Redirect} from 'react-router-dom'

import {useSignOut} from "../../hooks/auth"

const SignOut = () => {
<<<<<<< HEAD
    const { isAuthorized } = useSignOut()
=======
    const {isAuthorized} = useSignOut()
>>>>>>> 差分修正

    if (!isAuthorized) return <Redirect to="/"/>

    return (
<<<<<<< HEAD
        <>
            Sign Out...
        </>
    )
}

export default SignOut
=======
        <div>ログアウト中</div>
    )
}

export default SignOut
>>>>>>> 差分修正
