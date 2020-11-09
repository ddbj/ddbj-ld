import qs from 'query-string'
import {useCallback, useEffect} from 'react'
import {useDispatch, useSelector} from 'react-redux'

import * as viewCustomizedService from '../services/projectBook/view/customized'

import * as projectsAction from '../actions/admin/project'
import * as statisticAction from '../actions/admin/statistic'

import {DEFAULT_COUNT, DEFAULT_OFFSET, usePagination} from './pagination'

const parseSearchParams = search => {
    const params = qs.parse(search)
    const {offset, count} = params
    return {
        offset: offset ? +offset : DEFAULT_OFFSET,
        count: count ? +count : DEFAULT_COUNT,
    }
}

const useUpdateCondition = (base = '/admin/project/published', history) => {
    return useCallback(({offset, count}) => {
        history.push(`${base}?${qs.stringify({
            offset,
            count,
        })}`)
    }, [base, history])
}

export const useAllProjects = ({location, history, base}) => {
    const dispatch = useDispatch()
    const {total, rows} = useSelector(({admin}) => ({
        total: admin.project.total,
        rows: admin.project.rows ? admin.project.rows.map(projectBook => viewCustomizedService.getProject(projectBook)) : null
    }))

    const {
        offset,
        setOffset,
        count,
        setCount,
        pages
    } = usePagination(total)

    const updateConditions = useUpdateCondition(base, history)

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
        dispatch(projectsAction.getAllProjects({offset, count}))
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

export const useProjectsStatistic = () => {
    const dispatch = useDispatch()
    const statistic = useSelector(({admin}) => admin.statistic.projects)

    useEffect(() => {
        dispatch(statisticAction.getProjects())
    }, [dispatch])

    return statistic
}

export const useUsersStatistic = () => {
    const dispatch = useDispatch()
    const statistic = useSelector(({admin}) => admin.statistic.users)

    useEffect(() => {
        dispatch(statisticAction.getUsers())
    }, [dispatch])

    return statistic
}