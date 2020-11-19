import React from 'react'

import Widget from '../../components/Widget'
import {useIntl} from "react-intl";

const Editor = () => {
    const intl = useIntl()

    return (
        <Widget>
            <h2 className="page-title">{intl.formatMessage({id: 'editor.title'})}</h2>
            <div className="border-bottom d-flex">
                <div className="flex-grow-1">
                    <h5>{intl.formatMessage({id: 'editor.name'})}</h5>
                    <p>{intl.formatMessage({id: 'editor.os'})}</p>
                </div>
                <div className="text-right">
                    {/* FIXME 参照URLを変更 */}
                    <a className="btn btn-primary" href="./" download="editor.xslx">{intl.formatMessage({id: 'editor.button.download'})}</a>
                </div>
            </div>
        </Widget>
    )
}


export default Editor