import {
    SET_ENTRIES,
    SET_CURRENT_ENTRY
} from "../actions/entry"

const defaultState = {
    entries: null,
    currentEntry: null
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
        case SET_CURRENT_ENTRY: {
            const { currentEntry } = action.payload
            return {
                ...state,
                currentEntry
            }
        }
        default:
            return state
    }
}
