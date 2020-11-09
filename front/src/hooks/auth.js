import {useEffect} from "react"
import {useDispatch, useSelector} from "react-redux"

import * as authAction from '../actions/auth'
import config from '../config'
import {useIsPublished} from "./project/status";

const useCurrentUser = () => {
    const currentUser = useSelector(({auth}) => auth.currentUser)
    return currentUser
}

const useIsAuthorized = () => {
    const currentUser = useCurrentUser()

    if(config.isDummy) {
        // ダミーログインが済んでいるならログイン状態とする
        return true
    }

    return !!currentUser
}

const useIsAdmin = () => {
    const currentUser = useCurrentUser()

    if(config.isDummy && config.isDummyAdmin) {
        // ダミーログインしていて管理者アカウントなら管理者ログイン状態とする
        return true
    }

    return currentUser && currentUser.admin === true
}

const useSignOut = () => {
    const dispatch = useDispatch()
    const isAuthorized = useIsAuthorized()

    useEffect(() => {
        dispatch(authAction.signOut())
    }, [dispatch])

    return {isAuthorized}
}

const useLoginURL = () => {
    const {
        clientId,
        scope,
        redirectUrl,
        authorizeUrl,
    } = config.auth

    return authorizeUrl
        + "?response_type=code&"
        + "client_id=" + clientId
        + "&state=xyz&"
        + "scope=" + scope + "&"
        + "redirect_uri=" + redirectUrl
}

const useUrlParam = (name) => {
    const params = window.location.search;
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)')
    const results = regex.exec(params)

    if (null === results) {
        return null
    }

    return results[1].replace(/\+/g, ' ')
}

const useCoded = () => {
    const code = useUrlParam("code")

    return !!code;
}

const useEditable = (projectId) => {
    const currentUser = useCurrentUser()
    const isPublished = useIsPublished(projectId)

    if (!currentUser) {
        return false
    }

    const role = currentUser.role.find(r => r.projectId === projectId)

    if (!role) {
        return false
    }

    if(isPublished) {
        return false
    }

    const admin = currentUser.admin
    const owner = role.owner
    const writable = role.writable

    return admin || owner || writable
}

export {
    useCurrentUser,
    useIsAuthorized,
    useIsAdmin,
    useSignOut,
    useUrlParam,
    useLoginURL,
    useCoded,
    useEditable
}