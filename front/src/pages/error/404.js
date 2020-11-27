import React from 'react'
import { Container } from 'reactstrap'

import s from './ErrorPage.module.scss';
import {useIntl} from "react-intl";

const ErrorPage = () => {
    const intl = useIntl()

    return (
        <div className={s.errorPage}>
            <Container>
                <div className={`${s.errorContainer} mx-auto`}>
                    <h1 className={s.errorCode}>404</h1>
                    <p className={s.errorInfo}>
                        {intl.formatMessage({id: 'error.404'})}
                    </p>
                </div>
            </Container>
        </div>
    )
}

export default ErrorPage
