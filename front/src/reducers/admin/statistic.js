import {GET_PROJECTS_SUCCEED, GET_USERS_SUCCEED} from '../../actions/admin/statistic'

const defaultState = {
    users: null,
    projects: null
}

const statisticReducer = (state = defaultState, action) => {
    switch (action.type) {
        case GET_PROJECTS_SUCCEED: {
            return {
                ...state,
                projects: action.payload.statistic
            }
        }
        case GET_USERS_SUCCEED: {
            return {
                ...state,
                users: action.payload.statistic
            }
        }
        default:
            return state
    }
}

export default statisticReducer