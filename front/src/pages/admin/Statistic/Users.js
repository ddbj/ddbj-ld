import React from 'react'
import {Table} from 'reactstrap'

import {useUsersStatistic} from '../../../hooks/admin'
import Widget from '../../../components/Widget'

const Users = () => {

    const statistic = useUsersStatistic()

    if (!statistic) return null

    return (
        <>
            <h2 className="page-title">プロジェクトの統計</h2>
            <Widget>
                <Table>
                    <thead>
                    <tr>
                        <th>項目名</th>
                        <th>値</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th>総数</th>
                        <td>{statistic.count}</td>
                    </tr>
                    </tbody>
                </Table>
            </Widget>
        </>
    )
}

export default Users