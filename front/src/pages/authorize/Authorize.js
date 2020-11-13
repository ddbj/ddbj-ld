import React from 'react'
import ReactLoading from 'react-loading'
import style from './Authorize.module.scss'
import {useIsAuthorized, useUrlParam} from '../../hooks/auth'
import {Redirect} from "react-router-dom"
import {useDispatch} from 'react-redux'

import { login } from "../../actions/auth"

const Authorize = ({history}) => {
    // FIXME このへん整理したい

    const isAuthorized = useIsAuthorized()
    const code = useUrlParam("code")
    const dispatch = useDispatch()

    if (isAuthorized) {
        return <Redirect to="/me"/>
    }

    if (code) {
        dispatch(login(code, history))
    } else {
        return <Redirect to="/401"/>
    }

    return (
        <div className={style.loadingWrapper}>
            <ReactLoading
                type={'spinningBubbles'}
                color={'rgb(53, 126, 221)'}
                height={'20%'}
                width={'20%'}
            />
        </div>
    )
}

export default Authorize