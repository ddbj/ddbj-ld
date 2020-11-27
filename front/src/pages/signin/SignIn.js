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
