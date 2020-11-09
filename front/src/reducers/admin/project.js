import {GET_ALL_PROJECTS_START, GET_ALL_PROJECTS_SUCCEED} from '../../actions/admin/project'

const defaultState = {
    rows: null,
    total: null,
}

const projectsReducer = (state = defaultState, action) => {
    switch (action.type) {
        case GET_ALL_PROJECTS_START: {
            return defaultState
        }
        case GET_ALL_PROJECTS_SUCCEED: {
            const {rows, total} = action.payload
            return {
                ...state,
                rows,
                total
            }
        }
        default:
            return state
    }
}

export default projectsReducer