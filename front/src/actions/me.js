export const FETCH_PROJECTS = 'ME/FETCH_PROJECTS'

export const fetchProjects = ({offset, count}) => ({
    type: FETCH_PROJECTS,
    payload: {offset, count}
})

export const FETCH_PROJECTS_START = 'ME/FETCH_PROJECTS_START'

export const fetchProjectsStart = () => ({
    type: FETCH_PROJECTS_START
})

export const SET_PROJECTS = 'ME/SET_PROJECTS'

export const setProjects = ({rows, total}) => ({
    type: SET_PROJECTS,
    payload: {rows, total}
})

export const UPDATE_PROFILE = 'ME/UPDATE_PROFILE'

export const updateProfile = profile => ({
    type: UPDATE_PROFILE,
    payload: profile
})
