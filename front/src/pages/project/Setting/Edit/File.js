import React, {useCallback, useMemo, useRef} from 'react'
import {Button, Form, FormGroup, Input, Table} from 'reactstrap'

import {useFile} from '../../../../hooks/file'

import Widget from '../../../../components/Widget'
import Loading from "react-loading"
import {usePagination, useTable} from 'react-table'
import {ReactTablePagination as Pagination} from '../../../../components/Pagination'
import {useIntl} from "react-intl";

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

const File = ({match, fileType}) => {
    const {id} = match.params

    const {
        title,
        setUploadFile,
        uploadFile,
        isLoading,
        uploadedFiles,
        handleUpload,
        handleCopy,
        handleUrlsCopy,
        handleDownLoad,
        handleDelete,
        isDownloadLoading,
        isDeleteLoading,
        currentTargetName,
    } = useFile(id, fileType)

    const intl = useIntl()

    const columns = useMemo(() => ([
        {id: 'name', Header: intl.formatMessage({id: 'project.detail.editing.file.upload.header.name'}), accessor: 'name'},
        {id: 'url', Header: intl.formatMessage({id: 'project.detail.editing.file.upload.header.url'}), accessor: 'url'},
        {id: 'buttons', Header: '', accessor: 'buttons'},
    ]), [])

    const data = useMemo(() => {
        if (!uploadedFiles) return []
        return uploadedFiles
    }, [uploadedFiles])

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
        <Widget>
            <div className="d-flex align-items-center w-100 mb-4">
                <h3 className="page-title flex-grow-1 mb-0">{title}</h3>
            </div>
            <section className="mb-5 py-2 d-block">
                <Form enctype="multipart/form-data">
                    <FormGroup>
                        <Input
                            type="file"
                            onChange={(e) => setUploadFile(e.target.files[0])}
                        />
                    </FormGroup>
                    <Button
                        color="primary"
                        disabled={uploadFile === null}
                        onClick={handleUpload}
                    >
                        {isLoading
                            ? <Loading type={'spin'} height={20} width={20}/>
                            : <span>{intl.formatMessage({id: 'project.detail.editing.file.upload.button.upload'})}</span>
                        }
                    </Button>
                </Form>
            </section>
            {uploadedFiles && uploadedFiles.length > 0
                ?
                <Button
                    color="primary"
                    onClick={() => handleUrlsCopy(uploadedFiles)}
                    disabled={isLoading || isDownloadLoading || isDeleteLoading}
                    cssModule={{marginBottom: 10}}
                >
                    {intl.formatMessage({id: 'project.detail.editing.file.upload.button.copy.all'})}
                </Button>
                : null
            }

            {page && page.length > 0 ?
                <React.Fragment>
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
                                                    case "buttons":
                                                        return (
                                                            <React.Fragment>
                                                                <Button
                                                                    color="info"
                                                                    onClick={() => handleDownLoad(row.original.name, row.original.url, row.original.external)}
                                                                    disabled={null === row.original.url|| isLoading || isDownloadLoading || isDeleteLoading}
                                                                >
                                                                    {isDownloadLoading && currentTargetName === row.original.name
                                                                        ? <Loading type={'spin'} height={20} width={20}/>
                                                                        : <span>{intl.formatMessage({id: 'project.detail.editing.file.upload.button.download'})}</span>
                                                                    }
                                                                </Button>
                                                                {' '}
                                                                <Button
                                                                    color="danger"
                                                                    onClick={() => handleDelete(row.original.name)}
                                                                    disabled={row.original.external || isLoading || isDownloadLoading || isDeleteLoading}
                                                                >
                                                                    {isDeleteLoading && currentTargetName === row.original.name
                                                                        ? <Loading type={'spin'} height={20} width={20}/>
                                                                        : <span>{intl.formatMessage({id: 'project.detail.editing.file.upload.button.delete'})}</span>
                                                                    }
                                                                </Button>
                                                            </React.Fragment>
                                                        )
                                                    case "url":
                                                        return (
                                                            <CopiableText text={row.original.url} />
                                                        )
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
                </React.Fragment>
            : null
            }
        </Widget>
    )
}

export default File