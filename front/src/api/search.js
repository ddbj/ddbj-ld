import {requestGet, requestPost} from './common'
import config from './../config'

const DEFAULT_OFFSET = 0
const DEFAULT_COUNT = 30
const DEFAULT_OPERATION = 'OR'

export const search = (
    keyword,
    samples,
    instruments,
    offset = DEFAULT_OFFSET,
    count = DEFAULT_COUNT,
    operation = DEFAULT_OPERATION
) => {
    const params = {keyword, samples, instruments, operation, offset, count}

    return requestPost(null, config.searchApi, params)
}

export const getSetting = () => {
    return requestGet(null, config.getSettingApi)
}