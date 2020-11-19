import {useMemo, useState} from 'react'

export const COUNT_OPTIONS = [
    10,
    20,
    30,
    50,
    100,
]

export const DEFAULT_OFFSET = 0
export const DEFAULT_COUNT = 20

export const usePages = (total, offset, count) => {
    return useMemo(() =>
            Array.from({length: Math.ceil(total / count)})
                .fill(null)
                .map((_, index) => {
                    return {
                        number: index + 1,
                        offset: index * count,
                        isCurrent: offset === index * count
                    }
                })
        , [total, offset, count])
}

export const usePagination = (total) => {

    const [offset, setOffset] = useState(DEFAULT_OFFSET)
    const [count, setCount] = useState(DEFAULT_COUNT)
    const pages = usePages(total, offset, count)

    return {
        offset,
        setOffset,
        count,
        setCount,
        pages
    }
}