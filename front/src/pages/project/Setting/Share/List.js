import React, {useCallback, useEffect, useMemo, useRef} from 'react'
import {Link} from 'react-router-dom'
import {usePagination, useTable} from 'react-table'
import {Button, Table} from 'reactstrap'

import {getTemporaryMembers, useTemporaryMembers} from '../../../../hooks/project/setting'

import Widget from '../../../../components/Widget'
import {ReactTablePagination as Pagination} from '../../../../components/Pagination'
import {useIntl} from "react-intl";
import {useLocale} from "../../../../hooks/i18n";
import {connect} from "react-redux";

const CopiableText = ({text}) => {
    const inputEle = useRef(null)

    const copyToClipBoard = useCallback(() => {
        const input = inputEle.current
        input.select()
        document.execCommand("copy")
    }, [inputEle])

    return (
        <div className="d-flex">
            <textarea className="flex-grow-1 border-0 bg-white d-block w-100 mr-2" ref={inputEle} value={text}
                      onChange={() => {
                      }}/>
            <Button color="light" onClick={() => copyToClipBoard()}>
                <i className="fa fa-copy"/>
            </Button>
        </div>
    )
}

const List = ({match, project}) => {
    const {id} = match.params
    const {
        setTemporaryMembers,
        temporaryMembers
    } = useTemporaryMembers(project, id)

    const intl = useIntl()
    const locale = useLocale()

    const columns = useMemo(() => ([
        {id: 'label', Header: intl.formatMessage({id: 'project.detail.editing.url.table.label'}), accessor: 'label'},
        {id: 'url', Header: intl.formatMessage({id: 'project.detail.editing.url.table.url'}), accessor: 'url'},
        {id: 'expireDate', Header: intl.formatMessage({id: 'project.detail.editing.url.table.expire.date'}), accessor: 'expireDate'},
        {id: 'action', Header: '', accessor: 'action'},
    ]), [locale])

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
        data: temporaryMembers,
        initialState: {pageIndex: 0},
    }, usePagination)

    useEffect(() => {
        const { browseTokens } = project.find(pr => pr.ids.id === id)
        setTemporaryMembers(getTemporaryMembers(id, browseTokens))
    }, [project])

    return (
        <Widget>
            <div className="d-flex page-title">
                <h2 className="flex-grow-1 mb-0">{intl.formatMessage({id: 'project.detail.editing.url.title'})}</h2>
                <div className="d-flex justify-content-end -mx-2 align-items-center">
                    <Link className="btn btn-primary mx-2"
                          to={`/me/project/${id}/setting/share/create`}>{intl.formatMessage({id: 'project.detail.editing.url.button.create'})}</Link>
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
                                            case 'url': {
                                                return <CopiableText text={cell.value}/>
                                            }
                                            case 'action': {
                                                return (
                                                    <>
                                                        <Link className="mx-1 btn btn-primary" key="edit"
                                                              to={`/me/project/${id}/setting/share/${row.original.token}/edit`}>{intl.formatMessage({id: 'project.detail.editing.url.button.edit'})}</Link>
                                                        <Link className="mx-1 btn btn-danger" key="delete"
                                                              to={`/me/project/${id}/setting/share/${row.original.token}/delete`}>{intl.formatMessage({id: 'project.detail.editing.url.button.delete'})}</Link>
                                                    </>
                                                )
                                            }
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
    )
}


const mapStateToProps = (store) => {
    return {
        project: store.auth.currentUser.project,
    };
}

export default connect(mapStateToProps)(List);