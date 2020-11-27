const INVITE_PROJECT = 'USER/INVITE_PROJECT'
const UPDATE_PROFILE = 'USER/UPDATE_PROFILE'

export {
    INVITE_PROJECT,
    UPDATE_PROFILE
}

const inviteProject = (projectId, targets) => ({
    type: INVITE_PROJECT,
    payload: {projectId, targets}
})

const updateProfile = (accountUuid, profile) => ({
    type: UPDATE_PROFILE,
    payload: {accountUuid, profile}
})

export {
    inviteProject,
    updateProfile
}