export const GET_USERS = 'ADMIN/STATISTIC/GET_USERS'

export const getUsers = () => ({
    type: GET_USERS
})

export const GET_USERS_SUCCEED = 'ADMIN/STATISTIC/GET_USERS_SUCCEED'

export const getUsersSucceed = statistic => ({
    type: GET_USERS_SUCCEED,
    payload: {statistic}
})

export const GET_PROJECTS = 'ADMIN/STATISTIC/GET_PROJECTS'

export const getProjects = () => ({
    type: GET_PROJECTS
})

export const GET_PROJECTS_SUCCEED = 'ADMIN/STATISTIC/GET_PROJECTS_SUCCEED'

export const getProjectsSucceed = statistic => ({
    type: GET_PROJECTS_SUCCEED,
    payload: {statistic}
})