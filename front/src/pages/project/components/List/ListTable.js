import React from 'react'
import FixedHeaderTable from '../../../../components/FixedHeaderTable/FixedHeaderTable'
import {ReactTablePagination as Pagination} from '../../../../components/Pagination/Pagination'

<<<<<<< HEAD
const Thead = ({ headerGroups }) => (
    <thead>
        {headerGroups.map(headerGroup => (
            <tr {...headerGroup.getHeaderGroupProps()}>
                {headerGroup.headers.map(column => (
                    <th {...column.getHeaderProps()}>
                        {column.render('Header')}
                        <div style={{ marginTop: 10 }}>
                            {column.canFilter ? column.render('Filter') : null}
                        </div>
                    </th>
                ))}
            </tr>
        ))}
=======
const Thead = ({headerGroups}) => (
    <thead>
    {headerGroups.map(headerGroup => (
        <tr {...headerGroup.getHeaderGroupProps()}>
            {headerGroup.headers.map(column => (
                <th {...column.getHeaderProps()}>{column.render('Header')}</th>
            ))}
        </tr>
    ))}
>>>>>>> 差分修正
    </thead>
)

const Tbody = ({getTableBodyProps, page, prepareRow, renderCell}) => (
    <tbody {...getTableBodyProps()}>
    {page.map((row, i) => {
        prepareRow(row)
        return (
            <tr {...row.getRowProps()}>
                {row.cells.map(cell => {
                    return <td {...cell.getCellProps()}>{renderCell(cell)}</td>
                })}
            </tr>
        )
    })}
    </tbody>
)

const ListTable = ({
                       renderCell,
                       getTableProps,
                       getTableBodyProps,
                       headerGroups,
                       prepareRow,
                       page,
                       gotoPage,
                       setPageSize,
                       pageCount,
                       state: {pageIndex, pageSize},
                   }) => (
    <>
        <FixedHeaderTable tableProps={getTableProps()}>
            <Thead {...{headerGroups}} />
            <Tbody {...{getTableBodyProps, page, prepareRow, renderCell}} />
        </FixedHeaderTable>
        <Pagination {...{pageCount, pageIndex, pageSize, setPageSize, gotoPage}} />
    </>
)

<<<<<<< HEAD
export default ListTable
=======
export default ListTable
>>>>>>> 差分修正
