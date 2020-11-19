const defaultState = {
    isPersistReady: false
}

const appReducer = (state = defaultState, action) => {
    switch (action.type) {
        case 'persist/PERSIST':
            return {...state, isPersistReady: false}
        case 'persist/REHYDRATE':
            return {...state, isPersistReady: true}
        default:
            return state
    }
}

export default appReducer