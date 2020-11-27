import {
<<<<<<< HEAD
    SET_ENTRIES,
    SET_CURRENT_ENTRY
} from "../actions/entry"

const defaultState = {
    entries: null,
    currentEntry: null
=======
    SET_ENTRIES
} from "../actions/entry"

const defaultState = {
    entry: null
>>>>>>> 差分修正
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
<<<<<<< HEAD
        case SET_CURRENT_ENTRY: {
            const { currentEntry } = action.payload
            return {
                ...state,
                currentEntry
            }
        }
=======
>>>>>>> 差分修正
        default:
            return state
    }
}
