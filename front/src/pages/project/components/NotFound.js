import React from 'react'
import {useIntl} from "react-intl";

const NotFound = () => {
    const intl = useIntl()

    return (
        <div className="text-center py-4">
            <h3 className="page-title">{intl.formatMessage({id: 'project.detail.not.found.project'})}</h3>
        </div>
    )
}

export default NotFound