import React, { useEffect } from 'react'
import { Container } from 'reactstrap'

import s from './ErrorPage.module.scss'
import { useIntl } from "react-intl"
import { logOut } from "../../actions/auth"
import { useDispatch } from "react-redux"

const ErrorPage = () => {
    const intl = useIntl()
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(logOut())
    }, [])

    return (
        <div className={s.errorPage}>
            <Container>
                <div className={`${s.errorContainer} mx-auto`}>
                    <h1 className={s.errorCode}>401</h1>
                    <p className={s.errorInfo}>
                        {intl.formatMessage({id: 'error.401'})}
                    </p>
                </div>
            </Container>
        </div>
    )
}

export default ErrorPage
