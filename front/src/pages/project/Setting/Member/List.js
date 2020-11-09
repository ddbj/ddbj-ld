import React, {useCallback, useMemo} from 'react'
import {Link} from 'react-router-dom'
import {usePagination, useTable} from 'react-table'
import {Button, Table} from 'reactstrap'

import {ROLE_TYPE_EDITOR, ROLE_TYPE_OWNER, ROLE_TYPE_VIEWER, useMembers} from '../../../../hooks/project/setting'

import Widget from '../../../../components/Widget'
import {ReactTablePagination as Pagination} from '../../../../components/Pagination'

const List = ({match}) => {
    const {id} = match.params
    const members = useMembers(id)

    const restartHandler = useCallback(() => {
        alert('再開しました')
    }, [])
    const stopHandler = useCallback(() => {
        alert('停止しました')
    }, [])
    const deleteHandler = useCallback(() => {
        alert('削除しました')
    }, [])

    const columns = useMemo(() => ([
        {id: 'name', Header: 'ユーザ', accessor: 'name'},
        {id: 'id', Header: 'アカウントID', accessor: 'id'},
        {id: 'start_date', Header: '追加日', accessor: 'start_date'},
        {id: 'action', Header: '', accessor: 'role'},
    ]), [])

    const data = useMemo(() => {
        if (!members) return []
        return members.filter(member => member.role === ROLE_TYPE_EDITOR)
    }, [members])

    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        page,
        prepareRow,
        pageCount,
        gotoPage,
        setPageSize,
        state: {pageIndex, pageSize}
    } = useTable({
        columns,
        data,
        initialState: {pageIndex: 0},
    }, usePagination)

    return (
        <>
            <Widget>
                <div className="d-flex page-title">
                    <h2 className="flex-grow-1 mb-0">共同編集者一覧</h2>
                    <div className="d-flex justify-content-end -mx-2 align-items-center">
                        <Link className="btn btn-primary mx-2"
                              to={`/me/project/${id}/setting/member/invite`}>共同編集者を招待</Link>
                    </div>
                </div>
                <Table {...getTableProps()}>
                    <thead>
                    {headerGroups.map(headerGroup => (
                        <tr {...headerGroup.getHeaderGroupProps()}>
                            {headerGroup.headers.map(column => (
                                <th {...column.getHeaderProps()}>{column.render('Header')}</th>
                            ))}
                        </tr>
                    ))}
                    </thead>
                    <tbody {...getTableBodyProps()}>
                    {page.map((row) => {
                        prepareRow(row)
                        return (
                            <tr {...row.getRowProps()}>
                                {row.cells.map(cell =>
                                    <td {...cell.getCellProps()}>
                                        {(() => {
                                            switch (cell.column.id) {
                                                case 'end_date':
                                                    return cell.value || '無期限'
                                                case 'role': {
                                                    switch (cell.value) {
                                                        case ROLE_TYPE_OWNER:
                                                            return 'データ登録者'
                                                        case ROLE_TYPE_EDITOR:
                                                            return '共同編集者'
                                                        case ROLE_TYPE_VIEWER:
                                                            return '共同閲覧者'
                                                        default:
                                                            return '不明な権限種別'
                                                    }
                                                }
                                                case 'action':
                                                    const actions = []

                                                    if (cell.value === ROLE_TYPE_OWNER) return null

                                                    if (row.original.is_stopped) {
                                                        actions.push(<Button className="mx-1" key="restart"
                                                                             color="success"
                                                                             onClick={restartHandler}>再開</Button>)
                                                        actions.push(<Button className="mx-1" key="delete"
                                                                             color="danger"
                                                                             onClick={deleteHandler}>削除</Button>)
                                                    } else {
                                                        if (row.original.role === ROLE_TYPE_VIEWER) {
                                                            actions.push(
                                                                <Link key="edit" className="btn btn-light mx-1"
                                                                      to={`/me/project/${id}/setting/member/${row.original.id}/edit`}>編集</Link>
                                                            )
                                                        }
                                                        actions.push(<Button className="mx-1" key="stop" color="warning"
                                                                             onClick={stopHandler}>停止</Button>)
                                                    }

                                                    return actions
                                                default:
                                                    return cell.render('Cell')
                                            }
                                        })()}
                                    </td>
                                )}
                            </tr>
                        )
                    })}
                    </tbody>
                </Table>
                <Pagination {...{pageCount, pageIndex, pageSize, gotoPage, setPageSize}} />
            </Widget>
            <Widget>
                <h3 className="page-title">データ登録者</h3>
                <div>
                    {members.filter(member => member.role === ROLE_TYPE_OWNER).map(member =>
                        <p key={member.id}>{member.name}</p>
                    )}
                </div>
            </Widget>
        </>
    )
}

export default List