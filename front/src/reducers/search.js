import {SEARCH_START, SEARCH_SUCCEED, SETTING_SUCCEED} from '../actions/search'

const defaultState = {
    rows: null,
    total: null,
}

const searchReducer = (state = defaultState, action) => {
    switch (action.type) {
        case SEARCH_START:
            return {...state}
        case SEARCH_SUCCEED: {
            const {rows, total} = action.payload

            return {...state, rows, total}
        }
        case SETTING_SUCCEED: {
            const {setting} = action.payload

            return {...state, setting}
        }
        default:
            return state
    }
}

export default searchReducer
