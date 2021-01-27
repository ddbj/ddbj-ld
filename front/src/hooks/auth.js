import {useCallback, useEffect} from "react"
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
    return !!currentUser
}

const useIsCurator = () => {
    const currentUser = useCurrentUser()
    return currentUser && currentUser.curator === true
}

const useSignOut = () => {
    const dispatch = useDispatch()
    const isAuthorized = useIsAuthorized()

    useEffect(() => {
        dispatch(authAction.logOut())
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

    const admin = currentUser.curator
    const owner = role.owner
    const writable = role.writable

    return admin || owner || writable
}

const useAuthAction = (history) => {
    const loginURL = useLoginURL()

    const onSignIn = useCallback(() => {
        window.location.href = loginURL
    }, [])

    const onSignOut = useCallback(() => {
        history.push(`/signout`)
    }, [])

    return {
        onSignIn,
        onSignOut,
    }
}

export {
    useCurrentUser,
    useIsAuthorized,
    useIsCurator,
    useSignOut,
    useUrlParam,
    useLoginURL,
    useCoded,
    useEditable,
    useAuthAction,
}