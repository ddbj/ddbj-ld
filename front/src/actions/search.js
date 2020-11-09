const SEARCH = 'SEARCH/SEARCH'
const SEARCH_START = 'SEARCH/START'
const SEARCH_SUCCEED = 'SEARCH/SEARCH_SUCCEED'
const GET_SETTING = 'SEARCH/GET_SETTING'
const SETTING_SUCCEED = 'SEARCH/SETTING_SUCCEED'

export {
    SEARCH,
    SEARCH_START,
    SEARCH_SUCCEED,
    GET_SETTING,
    SETTING_SUCCEED
}

const search = ({keyword, samples, instruments, offset, count, operation}) => ({
    type: SEARCH,
    payload: {keyword, samples, instruments, offset, count, operation}
})

const searchStart = () => ({
    type: SEARCH_START
})

const searchSucceed = ({rows, total}) => ({
    type: SEARCH_SUCCEED,
    payload: {rows, total}
})

const getSetting = () => ({
    type: GET_SETTING
})

const settingSucceed = ({setting}) => ({
    type: SETTING_SUCCEED,
    payload: {setting}
})

export {
    search,
    searchStart,
    searchSucceed,
    getSetting,
    settingSucceed
}
