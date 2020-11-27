const GET_INVITATION_LIST = 'ACCOUNT/GET_INVITATION_LIST'

export {
    GET_INVITATION_LIST
}

const getInvitationList = (projectId) => ({
    type: GET_INVITATION_LIST,
    payload: { projectId }
})

export {
    getInvitationList
}