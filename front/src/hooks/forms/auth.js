import {useCallback, useMemo} from 'react'
import {useDispatch, useSelector} from 'react-redux'

import * as authAction from '../../actions/auth'

import {useInput} from '../forms'
import {useCurrentUser} from '../auth'

export const useSignInForm = () => {
    const dispatch = useDispatch()
    const errorMessage = useSelector(({auth}) => auth.errorMessage)
    const currentUser = useCurrentUser()

    const [id, updateId] = useInput("")
    const [password, updatePassword] = useInput("")

    // TODO: 厳密にする
    const isSubmittable = useMemo(() => !!id && !!password, [id, password])

    const signInWithJVarHandler = useCallback(event => {
        event.preventDefault()
        if (!isSubmittable) return
        dispatch(authAction.signInWithJVar(id, password))
    }, [dispatch, id, isSubmittable, password])

    const signInWithDDBJAccountHandler = useCallback(event => {
        dispatch(authAction.signInWithDDBJ())
    }, [dispatch])

    return {
        currentUser,
        id, updateId,
        password, updatePassword,
        isSubmittable,
        errorMessage,
        signInWithJVarHandler,
        signInWithDDBJAccountHandler,
    }
}
