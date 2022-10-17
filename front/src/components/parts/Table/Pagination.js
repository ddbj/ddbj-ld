import { useCallback, useMemo } from 'react';
import { FormattedMessage } from 'react-intl';
import { Nav, NavItem, Input, NavLink } from 'reactstrap';
import { usePagesWithDots, COUNT_OPTIONS, DOTS } from './hooks/pagination';

export default function Pagination ({
  canPreviousPage,
  canNextPage,
  pageCount,
  gotoPage,
  nextPage,
  previousPage,
  setPageSize,
  pageIndex,
  pageSize
}) {
  const current = useMemo(() => pageIndex + 1, [pageIndex]);
  const pagesWithDots = usePagesWithDots(current, pageCount, 2, DOTS);

  const handleGotoPage = useCallback(event => {
    gotoPage(+event.target.value);
  }, [gotoPage]);

  const handleSetPageSize = useCallback(event => {
    setPageSize(+event.target.value);
  }, [setPageSize]);

  return (
    <div className="p-3 d-flex gap-2">
      <div className="d-flex align-items-center flex-shrink-0 gap-2">
        <span style={{ whiteSpace: 'pre' }}>
          <FormattedMessage id="table.page_size" />
        </span>
        <Input
          type="select"
          value={pageSize}
          onChange={handleSetPageSize}
          style={{ width: 80 }}>
          {COUNT_OPTIONS.map(size =>
            <option key={size} value={size}>{size}</option>
          )}
        </Input>
      </div>
      <div className="flex-grow-1 d-flex flex-row justify-content-end">
        <Nav pills className="mb-xs flex-wrap">
          <NavItem>
            <NavLink tag="button" onClick={previousPage} disabled={!canPreviousPage}>
              <FormattedMessage id="table.go_back" />
            </NavLink>
          </NavItem>
          {pagesWithDots.map((item, index) => (
            <NavItem key={index}>
              {(item === DOTS) ? (
                { item }
              ) : (
                <NavLink tag="button"
                  active={item === current}
                  onClick={handleGotoPage} value={item - 1}>
                  {item}
                </NavLink>
              )}
            </NavItem>
          ))}
          <NavItem>
            <NavLink onClick={nextPage} disabled={!canNextPage}>
              <FormattedMessage id="table.go_next" />
            </NavLink>
          </NavItem>
        </Nav>
      </div>
    </div>
  );
};
