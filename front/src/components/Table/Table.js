import {
  Table as RSTable
} from 'reactstrap';

import * as s from './Table.module.scss';

import Pagination from './Pagination';

export default function Table ({
  getTableProps,
  getTableBodyProps,
  headerGroups,
  prepareRow,
  page,
  canPreviousPage,
  canNextPage,
  pageCount,
  gotoPage,
  nextPage,
  previousPage,
  setPageSize,
  state: { pageIndex, pageSize },

  customRowProps = function () {
    return {};
  },
}) {
  return (
    <>
      <RSTable className={s.table} responsive hover {...getTableProps()}>
        <thead>
          {headerGroups.map(headerGroup => (
            // eslint-disable-next-line react/jsx-key
            <tr {...headerGroup.getHeaderGroupProps()}>
              {headerGroup.headers.map(column => (
                // eslint-disable-next-line react/jsx-key
                <th {...column.getHeaderProps()}>
                  <div className="d-flex flex-column gap-1">
                    {column.render('Header')}
                    {column.canFilter && column.render('Filter')}
                  </div>
                </th>
              ))}
            </tr>
          ))}
        </thead>
        <tbody {...getTableBodyProps()}>
          {page.map((row) => prepareRow(row) || (
          // eslint-disable-next-line react/jsx-key
            <tr {...row.getRowProps(customRowProps(row))}>
              {row.cells.map(cell => (
                // eslint-disable-next-line react/jsx-key
                <td {...cell.getCellProps()}>
                  {cell.render('Cell')}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </RSTable>
      <Pagination {...{
        canPreviousPage,
        canNextPage,
        pageCount,
        gotoPage,
        nextPage,
        previousPage,
        setPageSize,
        pageIndex,
        pageSize
      }} />
    </>
  );
}

export function EmptyTable ({ children }) {
  return (
    <div className="py-5 d-flex flex-column px-4 justify-content-center align-items-center">
      <div className="text-muted">{children}</div>
    </div>
  );
}
