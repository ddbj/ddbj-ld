export const GET_ALL_PROJECTS = 'ADMIN/PROJECTS/GET_ALL_PROJECTS'

export const getAllProjects = ({offset, count}) => ({
    type: GET_ALL_PROJECTS,
    payload: {
        offset,
        count
    }
})

export const GET_ALL_PROJECTS_START = 'ADMIN/PROJECTS/GET_ALL_PROJECTS_START'

export const getAllProjectsStart = () => ({
    type: GET_ALL_PROJECTS_START
})

export const GET_ALL_PROJECTS_SUCCEED = 'ADMIN/PROJECTS/GET_ALL_PROJECTS_SUCCEED'

export const getAllProjectsSucceed = ({rows, total}) => ({
    type: GET_ALL_PROJECTS_SUCCEED,
    payload: {
        rows,
        total
    }
})
