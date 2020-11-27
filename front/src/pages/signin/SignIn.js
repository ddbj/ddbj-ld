<<<<<<< HEAD
import React, {useEffect} from 'react'
import { useLoginURL } from "../../hooks/auth"

const SignIn = () => {
    const loginURL = useLoginURL()

    useEffect(() => {
        window.location.href = loginURL
    }, [])

    return <>Redirect...</>;
}

export default SignIn
=======
import React from 'react'
// import { useSignIn } from '../../hooks/auth'

// FIXME 廃止予定
const SignIn = () => {
    return <div></div>;
}

export default SignIn
>>>>>>> 差分修正
