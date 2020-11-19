import {useCallback, useEffect, useMemo, useState} from 'react'
import {useDispatch, useSelector} from 'react-redux'
import qs from 'query-string'

import * as searchAction from '../actions/search'

import {useInput} from './forms'
import {DEFAULT_COUNT, DEFAULT_OFFSET, usePagination} from './pagination'

export const TAB_SIMPLE = 'TAB_SIMPLE'
export const TAB_ADVANCED = 'TAB_ADVANCED'

export const SEARCH_OPERATION_OR = 'OR'
export const SEARCH_OPERATION_AND = 'AND'

export const SEARCH_OPERATION_OPTIONS = [
    {label: 'OR', value: SEARCH_OPERATION_OR},
    {label: 'AND', value: SEARCH_OPERATION_AND},
]

export const useHeaderSearchForm = ({location, history}) => {
    const query = qs.parse(location.search)
    const [keyword, updateKeyword] = useInput(query.keyword || '')

    const submitHandler = useCallback(event => {
        event.preventDefault()
        history.push(`/search?${qs.stringify({keyword})}`)
    }, [history, keyword])

    return {
        keyword,
        updateKeyword,
        submitHandler
    }
}

const parseSearchParams = search => {
    const params = qs.parse(search)
    const {keyword, samples, instruments, offset, count, operation} = params

    return {
        keyword: keyword ? keyword.replace("　", " ").split(" ") : [],
        samples: (
            samples ? (
                Array.isArray(samples) ? samples : [samples]
            ) : []
        ),
        instruments: (
            instruments ? (
                Array.isArray(instruments) ? instruments : [instruments]
            ) : []
        ),
        offset: offset ? +offset : DEFAULT_OFFSET,
        count: count ? +count : DEFAULT_COUNT,
        operation: operation ? operation : null
    }
}

export const useUpdateCondition = (history, isSimple) => {
    return useCallback(({keyword, samples, instruments, offset, count, operation}) => {
        if (isSimple) {
            history.push(`/search?${qs.stringify({keyword, offset, count, operation: operation.value})}`)
            return
        }

        history.push(`/search?${qs.stringify({
            keyword,
            samples: samples ? samples.map(option => option.value) : null,
            instruments: instruments ? instruments.map(option => option.value) : null,
            offset,
            count,
            operation: operation.value
        })}`)
    }, [history, isSimple])
}

export const useSearchForm = ({location, history}) => {
    const dispatch = useDispatch()
    const {total, rows} = useSelector(({search}) => ({
        total: search.total,
        rows: search.rows ? search.rows : []
    }))

    const [tab, setTab] = useState(null)

    const {
        offset,
        setOffset,
        count,
        setCount,
        pages
    } = usePagination(total)

    const [operation, setOperation] = useState(SEARCH_OPERATION_OPTIONS[0])

    const [keyword, setKeyword] = useState('')
    const [samples, setSamples] = useState([])
    const [instruments, setInstruments] = useState([])
    const [samplesOptions, setSampleOptions] = useState([])
    const [instrumentsOptions, setInstrumentsOptions] = useState([])

    const {s, i} = useSelector(({search}) => ({
        s:search && search.setting ? search.setting.samples : [],
        i: search && search.setting ? search.setting.instruments : []
    }))

    useEffect(() => {
        if(null === samples || samples.length === 0) {
            setSampleOptions(s)
        } else {
            setSampleOptions(s.filter(v => (
                !samples.some(target => target.label === v.label)
            )))
        }

        if(null === instruments || instruments.length === 0) {
            setInstrumentsOptions(i)
        } else {
            setInstrumentsOptions(i.filter(v => (
                !instruments.some(target => target.label === v.label)
            )))
        }

    }, [samples, instruments])

    const switchSimple = useCallback(() => setTab(TAB_SIMPLE), [])
    const switchAdvanced = useCallback(() => setTab(TAB_ADVANCED), [])

    const isSimple = useMemo(() => tab === TAB_SIMPLE, [tab])
    const isAdvanced = useMemo(() => tab === TAB_ADVANCED, [tab])

    const updateConditions = useUpdateCondition(history, isSimple)

    const selectOffset = useCallback(offset => {
        updateConditions({keyword, samples, instruments, offset, count, operation})
    }, [keyword, samples, instruments, count, operation, updateConditions])

    const selectCount = useCallback(count => {
        updateConditions({keyword, samples, instruments, offset, count, operation})
    }, [keyword, samples, instruments, offset, operation, updateConditions])

    const isSubmittable = useMemo(() => {
        if (isSimple) {
            return !!keyword
        }

        return (samples && samples.length > 0) || (instruments && instruments.length > 0)
    }, [isSimple, keyword, samples, instruments])

    const submitHandler = useCallback(event => {
        event.preventDefault()
        if (!isSubmittable) return

        updateConditions({
            keyword,
            samples,
            instruments,
            offset: DEFAULT_OFFSET,
            count: DEFAULT_COUNT,
            operation
        })
    }, [isSubmittable, updateConditions, keyword, samples, instruments, operation])

    useEffect(() => {
        const {
            keyword,
            samples,
            instruments,
            offset,
            count,
            operation
        } = parseSearchParams(location.search)

        setSamples(
            samples.map((v) => {
                return {
                    label: v,
                    value: v,
                }
            })
        )
        setInstruments(
            instruments.map((v) => {
                return {
                    label: v,
                    value: v,
                }
            })
        )

        setOffset(offset)
        setCount(count)
        setOperation(SEARCH_OPERATION_OPTIONS.find(option => option.value === operation) || SEARCH_OPERATION_OPTIONS[0])
        setTab(
            (
                (samples && samples.length > 0) ||
                (instruments && instruments.length > 0) ? TAB_ADVANCED : TAB_SIMPLE
            )
        )

        dispatch(
            searchAction.search({
                keyword,
                samples,
                instruments,
                offset,
                count,
                operation
            })
        )
    }, [dispatch, location, setInstruments, setSamples, setCount, setKeyword, setOffset])

    useEffect(() => {
        dispatch(searchAction.getSetting())
    }, [])

    return {
        tab,
        switchSimple,
        switchAdvanced,
        isSimple,
        isAdvanced,
        keyword,
        setKeyword,
        samples,
        setSamples,
        samplesOptions,
        instruments,
        setInstruments,
        instrumentsOptions,
        setOperation,
        count,
        selectCount,
        pages,
        selectOffset,
        isSubmittable,
        submitHandler,
        operation,
        total,
        rows,
    }
}