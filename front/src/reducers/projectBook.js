import {
    ACCESS_PROJECT_VIEW_DATA,
    GET_PROJECT,
    SET_CURRENT_PROJECT_VIEW,
    SET_DRAFT_PROJECT,
    SET_PUBLISHED_PROJECT
} from '../actions/project'

import * as getService from '../services/projectBook/get'

const defaultState = {
    published: {},
    draft: {},
    currentProjectView: null,
    isLoading: false
}

export default function projectBook(state = defaultState, action) {
    switch (action.type) {
        case SET_PUBLISHED_PROJECT: {
            const {projectBook} = action.payload
            const id = getService.getId(projectBook)
            return {
                ...state,
                published: {
                    ...state.published,
                    [id]: projectBook
                }
            }
        }
        case SET_DRAFT_PROJECT: {
            const { draftMetadata } = action.payload
            return {
                ...state,
                draftMetadata
            }
        }
        case SET_CURRENT_PROJECT_VIEW: {
            const currentProjectView = action.payload.currentProjectView
            return {
                ...state,
                currentProjectView,
                isLoading: false
            }
        }
        case GET_PROJECT: {
            return {
                ...state,
                isLoading: true
            }
        }
        case ACCESS_PROJECT_VIEW_DATA: {
            return {
                ...state,
                isLoading: true
            }
        }
        default:
            return state
    }
}
