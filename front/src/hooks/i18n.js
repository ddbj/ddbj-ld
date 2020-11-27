import {useCallback, useMemo} from 'react'
import {useDispatch, useSelector} from 'react-redux'

import * as i18nAction from '../actions/i18n'
import {useIntl} from "react-intl";

const LOCALE_EN = "en"
const LOCALE_JA = "ja"

const parseLocale = locale => locale.split('-')[0]

export const useLocale = () => {
    const locale = useSelector(({i18n}) => i18n.locale)
    return locale || LOCALE_EN
}

export const useChangeLocale = () => {
    const dispatch = useDispatch()
    const changeLocale = useCallback(a => {
        const locale = a && a.target && a.target.value ? a.target.value : a
        dispatch(i18nAction.setLocale(parseLocale(locale)))
    }, [dispatch])
    return changeLocale
}

export const useMessages = (allMessages) => {
    const locale = useLocale()
    switch (locale) {
        case LOCALE_JA:
            return allMessages.ja
        default:
            return allMessages.en
    }
}

export const useMessage = (id) => {
    const intl = useIntl()

    const message = useMemo(() => {
        return intl.formatMessage({ id })
    }, [id])

    return message
}