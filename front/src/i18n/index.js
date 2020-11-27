import React from 'react'
import {IntlProvider} from 'react-intl'

import en from './en.json'
import ja from './ja.json'

import {useLocale, useMessages} from '../hooks/i18n'

const allMessages = {ja, en}

export const Provider = ({children, ...props}) => {
    const locale = useLocale()
    const messages = useMessages(allMessages)
    return (
        <IntlProvider {...props} locale={locale} messages={messages}>
            {children}
        </IntlProvider>
    )
}