import React from 'react'
import {Badge} from 'reactstrap'
import {useIntl} from "react-intl";

export const RequiredBadge = () => {
    const intl = useIntl()

    return (<Badge color="primary">{intl.formatMessage({id: 'project.detail.editing.url.create.must'})}</Badge>)
}