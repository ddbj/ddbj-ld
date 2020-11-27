import React, {useMemo} from 'react'

import {Button, ButtonGroup, Col, Row,} from 'reactstrap'

import {COUNT_OPTIONS} from '../../hooks/pagination'
import {useIntl} from "react-intl";

const ReactTablePagination = ({pageCount, pageIndex, pageSize, gotoPage, setPageSize}) => {
    const pages = useMemo(() => (
        Array.from({length: pageCount}).map((_, index) => ({
            offset: index * pageSize,
            isCurrent: index === pageIndex,
            number: index + 1
        }))
    ), [pageCount, pageIndex, pageSize])

    const offset = useMemo(() => pageIndex * pageSize, [pageIndex, pageSize])

    return (
        <Pagination
            pages={pages}
            offset={offset}
            count={pageSize}
            onCountChange={count => setPageSize(count)}
            onOffsetChange={offset => gotoPage(Math.floor(offset / pageSize))}
        />
    )
}

const Pagination = ({pages, count, onCountChange, onOffsetChange}) => {
    const intl = useIntl()
    const currentPage = pages.find(p => p.isCurrent)
    const prevPage = currentPage ? pages[currentPage.number - 2] : null
    const nextPage = currentPage ? pages[currentPage.number] : null

    return (
        <Row className="justify-between">
            <Col md="3">
                <div className="d-flex align-items-center">
<<<<<<< HEAD
                    <span className="mr-2" style={{whiteSpace: 'pre'}}>Displayed results:</span>
                    <select style={{width: 60}} lassName="form-control" value={count} onChange={event => onCountChange(event.target.value)}>
=======
                    <span className="mr-2" style={{whiteSpace: 'pre'}}>{intl.formatMessage({id: 'search.result.displayed.results'})}:</span>
                    <select className="form-control" value={count} onChange={event => onCountChange(event.target.value)}>
>>>>>>> 差分修正
                        {COUNT_OPTIONS.map(count => <option key={count} value={count}>{count}</option>)}
                    </select>
                </div>
            </Col>
            <Col md="9" className="py-4 d-flex justify-content-end">
                <ButtonGroup className="mb-xs flex-wrap">
                    <Button
                        key="prev"
                        onClick={prevPage ? () => onOffsetChange(prevPage.offset) : null}
                        disabled={null == prevPage}
                        color="default">
                        {intl.formatMessage({id: 'pagination.prev.button'})}
                    </Button>
                    {pages.map(page => {
                        return renderPage(page, pages, currentPage, onOffsetChange)
                    })}
                    <Button
                        key="prev"
                        onClick={nextPage ? () => onOffsetChange(nextPage.offset) : null}
                        disabled={null == nextPage}
                        color="default"
                    >
                        {intl.formatMessage({id: 'pagination.next.button'})}
                    </Button>
                </ButtonGroup>
            </Col>
        </Row>
    )
}

const renderPage = (page, pages, currentPage, onOffsetChange) => {
    const firstPage = 1
    const lastPage = pages.length

    // ページ数を表示するの最初のページと最後のページとカレントページとカレントページの1個前と1個後
    // ...で表示して良いのはカレントページの2個前から4個前と2個後から4個後、カレントページが2~4だったら(4),5,6,7,8,9、データが9個以内
    // 9個を超えていてでラスト4個手前以内のページだったら9ページ表示を維持する

    if(page.isCurrent
        || page.number === firstPage
        || page.number === lastPage
        || page.number === (currentPage.number + 1)
        || page.number === (currentPage.number - 1)
        || (currentPage.number < (firstPage + 4) && page.number < 6)
    ) {
        return <Button
            key={page.offset}
            onClick={() => onOffsetChange(page.offset)}
            active={page.isCurrent}
            color="default">
                {page.number}
            </Button>
    } else if(
        (page.number < (currentPage.number - 1)
        &&  page.number > (currentPage.number - 4))
        ||
            (page.number > (currentPage.number + 1)
                &&  page.number < (currentPage.number + 4))
        ||
            (currentPage.number > 1 && currentPage.number < 5 && page.number < 9 )
        ||
            (currentPage.number === 1 && page.number < 9)
        ||
            pages.length === 9
        ||
            (currentPage.number > (lastPage - 4) && page.number  > (lastPage - 8))
    ){
        return <Button
            key={page.offset}
            onClick={() => onOffsetChange(page.offset)}
            active={page.isCurrent}
            color="default">
            ...
        </Button>
    } else {
        return null
    }
}

export default Pagination

export {
    ReactTablePagination
<<<<<<< HEAD
}
=======
}
>>>>>>> 差分修正
