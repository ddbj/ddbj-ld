import {
    SET_ENTRIES
} from "../actions/entry"

const defaultState = {
    entry: null
}

export default function entry(state = defaultState, action) {
    switch (action.type) {
        case SET_ENTRIES: {
            const {entries} = action.payload
            return {
                ...state,
                entries
            }
        }
        default:
            return state
    }
}
