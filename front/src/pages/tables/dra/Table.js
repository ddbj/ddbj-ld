import React from 'react';
import {
  Progress,
  Dropdown,
  DropdownMenu,
  DropdownToggle,
  DropdownItem,
} from 'reactstrap';

import {
  BootstrapTable,
  TableHeaderColumn,
} from 'react-bootstrap-table';

import { reactBootstrapTableData } from './data';
import s from './Table.modules.scss';

class Table extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      reactBootstrapTable: reactBootstrapTableData(),
    };
  }

  renderSizePerPageDropDown = (props) => {
    const limits = [];
    props.sizePerPageList.forEach((limit) => {
      limits.push(<DropdownItem key={limit} onClick={() => props.changeSizePerPage(limit)}>{ limit }</DropdownItem>);
    });

    return (
      <Dropdown isOpen={props.open} toggle={props.toggleDropDown}>
        <DropdownToggle color="default" caret>
          { props.currSizePerPage }
        </DropdownToggle>
        <DropdownMenu>
          { limits }
        </DropdownMenu>
      </Dropdown>
    );
  };

  render() {
    const options = {
      sizePerPage: 10,
      paginationSize: 3,
      sizePerPageDropDown: this.renderSizePerPageDropDown,
    };

    function infoFormatter(cell) {
      return (
        <div>
          <small>
            Type:&nbsp;<span className="fw-semi-bold">{cell.type}</span>
          </small>
          <br />
          <small>
            Dimensions:&nbsp;<span className="fw-semi-bold">{cell.dimensions}</span>
          </small>
        </div>
      );
    }

    function descriptionFormatter(cell) {
      return (
        <button className="btn-link">
          {cell}
        </button>
      );
    }

    function progressFormatter(cell) {
      return (
        <Progress style={{ height: '15px' }} color={cell.type} value={cell.progress} />
      );
    }

    function progressSortFunc(a, b, order) {
      if (order === 'asc') {
        return a.status.progress - b.status.progress;
      }
      return b.status.progress - a.status.progress;
    }

    function dateSortFunc(a, b, order) {
      if (order === 'asc') {
        return new Date(a.date).getTime() - new Date(b.date).getTime();
      }
      return new Date(b.date).getTime() - new Date(a.date).getTime();
    }

      return (
          <div>
              <h2 className="page-title">DRA Search - <span className="fw-semi-bold">Result</span></h2>
              <BootstrapTable
                  data={this.state.reactBootstrapTable}
                  version="4"
                  pagination options={options}
                  search
                  tableContainerClass={`table-striped table-hover ${s.bootstrapTable}`}
                  exportCSV
              >
                  <TableHeaderColumn className={`width-100 ${s.columnHead}`} columnClassName="width-100"
                                     dataField="accession" isKey dataSort>
                      <span className="fs-sm" style={{fontSize: 10}}>Accession</span>
                  </TableHeaderColumn>
                  <TableHeaderColumn className={`${s.columnHead}`} dataField="name" dataSort>
                      <span className="fs-sm" style={{fontSize: 10}}>Sample ID</span>
                  </TableHeaderColumn>
                  <TableHeaderColumn className={`d-md-table-cell ${s.columnHead}`} columnClassName="d-md-table-cell"
                                     dataField="description" dataFormat={descriptionFormatter}>
                      <span className="fs-sm" style={{fontSize: 10}}>title</span>
                  </TableHeaderColumn>
                      <TableHeaderColumn className={`d-md-table-cell ${s.columnHead}`} columnClassName="d-md-table-cell"
                                     dataField="description" dataFormat={descriptionFormatter}>
                      <span className="fs-sm" style={{fontSize: 10}}>organism name</span>
                  </TableHeaderColumn>
                  <TableHeaderColumn className={`width-150 ${s.columnHead}`} columnClassName="width-150"
                                     dataField="description" dataFormat={descriptionFormatter}>
                      <span className="fs-sm" style={{fontSize: 10}}>organism identifier</span>
                  </TableHeaderColumn>
                  <TableHeaderColumn className={`${s.columnHead}`} dataField="name" dataSort>
                      <span className="fs-sm" style={{fontSize: 10}}>BioProject ID</span>
                  </TableHeaderColumn>
                  <TableHeaderColumn className={`${s.columnHead}`} dataField="name" dataSort>
                      <span className="fs-sm" style={{fontSize: 10}}>SRA ID</span>
                  </TableHeaderColumn>
                  <TableHeaderColumn className={`${s.columnHead}`} dataField="name" dataSort>
                      <span className="fs-sm" style={{fontSize: 10}}>SRR ID</span>
                  </TableHeaderColumn>
                  <TableHeaderColumn className={`${s.columnHead}`} dataField="name" dataSort>
                      <span className="fs-sm" style={{fontSize: 10}}>BioSample ID</span>
                  </TableHeaderColumn>
                  <TableHeaderColumn className={`width-150 ${s.columnHead}`} columnClassName="width-150"
                                     dataField="date" dataSort sortFunc={dateSortFunc}>
                      <span className="fs-sm" style={{fontSize: 10}}>published Date</span>
                  </TableHeaderColumn>
              </BootstrapTable>
          </div>
      );
  }

}

export default Table;
