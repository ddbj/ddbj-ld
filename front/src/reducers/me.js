import {FETCH_PROJECTS_START, SET_PROJECTS} from '../actions/me'

const defaultState = {
    projects: {
        rows: null,
        total: null,
    }
}

const meReducer = (state = defaultState, action) => {
    switch (action.type) {
        case FETCH_PROJECTS_START: {
            return {
                ...state,
                projects: defaultState.projects
            }
        }
        case SET_PROJECTS: {
            const {rows, total} = action.payload
            return {
                ...state,
                projects: {
                    rows,
                    total
                }
            }
        }
        default:
            return state
    }
}

export default meReducer