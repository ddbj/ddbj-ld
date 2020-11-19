import qs from 'query-string'
import {useCallback, useEffect, useMemo} from "react"
import {useDispatch, useSelector} from 'react-redux'

import * as customizedViewService from '../services/projectBook/view/customized'

import * as meAction from '../actions/me'

import {useInput} from './forms'
import {useCurrentUser} from './auth'
import {DEFAULT_COUNT, DEFAULT_OFFSET, usePagination} from './pagination'

export const useProfileForm = () => {
    const dispatch = useDispatch()
    const currentUser = useCurrentUser()

    const [name, nameChangeHandler] = useInput(currentUser.name)
    const [email, emailChangeHandler] = useInput(currentUser.email)

    const id = useMemo(() => currentUser.id, [currentUser])
    const isSubmittable = useMemo(() => !!email && !!name, [email, name])

    const submitHandler = useCallback(event => {
        event.preventDefault()
        if (!isSubmittable) return
        dispatch(meAction.updateProfile({name, email}))
    }, [dispatch, email, isSubmittable, name])

    return {
        submitHandler,
        id,
        email, emailChangeHandler,
        name, nameChangeHandler,
        isSubmittable
    }
}

const parseSearchParams = search => {
    const params = qs.parse(search)
    const {offset, count} = params
    return {
        offset: offset ? +offset : DEFAULT_OFFSET,
        count: count ? +count : DEFAULT_COUNT,
    }
}

const useUpdateCondition = history => {
    return useCallback(({offset, count}) => {
        history.push(`/me/project?${qs.stringify({
            offset,
            count,
        })}`)
    }, [history])
}

export const useProjects = ({location, history}) => {
    const dispatch = useDispatch()

    const {total, projects} = useSelector(({auth}) => ({
        total: auth.currentUser ? auth.currentUser.project.length : 0,
        projects: auth.currentUser.project ? auth.currentUser.project.map(prj => customizedViewService.getProject(prj)) : null
    }))

    const {
        offset,
        setOffset,
        count,
        setCount,
        pages
    } = usePagination(total)

    const rows = projects.filter((project, idx) => {
        return offset <= idx && idx < offset + count
    })

    const updateConditions = useUpdateCondition(history)

    const selectOffset = useCallback(offset => {
        updateConditions({offset, count})
    }, [count, updateConditions])

    const selectCount = useCallback(count => {
        updateConditions({offset, count})
    }, [offset, updateConditions])

    useEffect(() => {
        const {offset, count} = parseSearchParams(location.search)
        setOffset(offset || DEFAULT_OFFSET)
        setCount(count || DEFAULT_COUNT)
        dispatch(meAction.fetchProjects({offset, count}))
    }, [dispatch, location, setCount, setOffset])

    return {
        count,
        selectCount,
        pages,
        selectOffset,
        total,
        rows,
    }
}