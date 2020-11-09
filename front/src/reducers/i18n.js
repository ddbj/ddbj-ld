import {SET_LOCALE} from '../actions/i18n'

const defaultState = {
    locale: null
}

export default function i18nReducer(state = defaultState, action) {
    switch (action.type) {
        case SET_LOCALE:
            const {locale} = action.payload
            return {...state, locale}
        default:
            return state
    }
}