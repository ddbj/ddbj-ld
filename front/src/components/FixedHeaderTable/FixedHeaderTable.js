import React from 'react'
import {Table} from 'reactstrap'

import * as s from './FixedHeaderTable.module.scss'

const FixedHeaderTable = ({tableProps, children, ...props}) => {
    return (
        <div {...props} className={s.container}>
            <Table {...tableProps}>
                {children}
            </Table>
        </div>
    )
}

export default FixedHeaderTable