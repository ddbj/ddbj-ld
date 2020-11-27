import React from 'react'
import {useIntl} from "react-intl";

const MetadataNotFound = ({id}) => {
    const intl = useIntl()

    return (
        <div className="text-center py-4">
            <h3 className="page-title">{intl.formatMessage({id: 'project.detail.not.found.metadata'})}</h3>
        </div>
    )
}

export default MetadataNotFound