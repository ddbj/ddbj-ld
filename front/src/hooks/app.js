import {useMemo} from 'react'
import {useSelector} from 'react-redux'

export const useIsReady = () => {
    const {isPersistReady} = useSelector(({app}) => app)
    const isReady = useMemo(() => isPersistReady, [isPersistReady])
    return isReady
}