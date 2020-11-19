import React from 'react'
import {Table} from 'reactstrap'

import * as s from './SingleTable.module.scss'

const SingleTable = (props) => (
    <Table {...props} className={s.table}/>
)

export default SingleTable