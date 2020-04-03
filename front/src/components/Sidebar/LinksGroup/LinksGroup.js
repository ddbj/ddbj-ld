import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { NavLink, withRouter } from 'react-router-dom';
import { Collapse, Badge } from 'reactstrap';
import { Route } from 'react-router';
import classnames from 'classnames';
import { connect } from 'react-redux'

import s from './LinksGroup.module.scss';

class LinksGroup extends Component {
  /* eslint-disable */
  static propTypes = {
    header: PropTypes.node.isRequired,
    link: PropTypes.string.isRequired,
    childrenLinks: PropTypes.array,
    iconName: PropTypes.string,
    className: PropTypes.string,
    badge: PropTypes.string,
    label: PropTypes.string,
    activeItem: PropTypes.string,
    isHeader: PropTypes.bool,
    index: PropTypes.string,
    deep: PropTypes.number,
    onActiveSidebarItemChange: PropTypes.func,
    labelColor: PropTypes.string,
    exact: PropTypes.bool
  };
  /* eslint-enable */

  static defaultProps = {
    link: '',
    childrenLinks: null,
    className: '',
    isHeader: false,
    deep: 0,
    activeItem: '',
    label: '',
    exact: true
  };

  constructor(props) {
    super(props);
    this.togglePanelCollapse = this.togglePanelCollapse.bind(this);

    this.state = {
      headerLinkWasClicked: true,
    };
  }

  togglePanelCollapse(link) {
    this.props.onActiveSidebarItemChange(link);
    this.setState({
      headerLinkWasClicked: !this.state.headerLinkWasClicked ||
        (this.props.activeItem && !this.props.activeItem.includes(this.props.index)),
    });
  }

  render() {
    const isOpen = this.props.activeItem &&
      this.props.activeItem.includes(this.props.index) &&
      this.state.headerLinkWasClicked;

    const {exact} = this.props.exact;

    if (!this.props.childrenLinks) {
      if (this.props.isHeader) {
        return (
          <li className={classnames('link-wrapper', s.headerLink, this.props.className)}>
            <NavLink
              to={this.props.link}
              activeClassName={s.headerLinkActive}
              exact={exact}
              target={this.props.target}
            >
              <span className={classnames('icon', s.icon)}>
                <i className={`fi ${this.props.iconName}`} />
              </span>
              {this.props.header} {this.props.label && <sup className={`${s.headerLabel} ${s.headerUpdate} text-${this.props.labelColor || 'warning'}`}>{this.props.label}</sup>}
              {this.props.badge && <Badge className={s.badge} pill>9</Badge>}
            </NavLink>
          </li>
        );
      }
      return (
        <li>
          <NavLink
            to={this.props.link}
            activeClassName={s.headerLinkActive}
            style={{ paddingLeft: `${26 + (10 * (this.props.deep - 1))}px` }}
            onClick={(e) => {
              // able to go to link is not available(for Demo)
              if (this.props.link.includes('menu')) {
                e.preventDefault();
              }
            }}
            exact={exact}
          >
            {this.props.header} {this.props.label && <sup className={`${s.headerLabel} text-${this.props.labelColor || 'warning'}`}>{this.props.label}</sup>}
          </NavLink>
        </li>
      );
    }
    /* eslint-disable */
    return (
      <Route
        path={this.props.link}
        children={(params) => {
          const { match } = params;
          return (
            <li className={classnames('link-wrapper', { [s.headerLink]: this.props.isHeader }, this.props.className)}>
              <a className={classnames({ [s.headerLinkActive]: match }, { [s.collapsed]: isOpen }, "d-flex")}
                style={{ paddingLeft: `${this.props.deep == 0 ? 50 : 26 + 10 * (this.props.deep - 1)}px` }}
                onClick={() => this.togglePanelCollapse(this.props.link)}
              >
                {this.props.isHeader ?
                  <span className={classnames('icon', s.icon)}>
                    <i className={`fi ${this.props.iconName}`} />
                  </span> : null
                }
                {this.props.header} {this.props.label && <sup className={`${s.headerLabel} ${s.headerNode} ml-1 text-${this.props.labelColor || 'warning'}`}>{this.props.label}</sup>}
                <b className={['fa fa-angle-left', s.caret].join(' ')} />
              </a>
              {/* eslint-enable */}
              <Collapse className={s.panel} isOpen={isOpen}>
                <ul>
                  {this.props.childrenLinks &&
                    this.props.childrenLinks.map((child, ind) =>
                      <LinksGroup
                        onActiveSidebarItemChange={this.props.onActiveSidebarItemChange}
                        activeItem={this.props.activeItem}
                        header={child.header}
                        link={child.link}
                        index={child.index}
                        childrenLinks={child.childrenLinks}
                        deep={this.props.deep + 1}
                        key={ind} // eslint-disable-line
                      />,
                    )}
                </ul>
              </Collapse>
            </li>
          );
        }}
      />
    );
  }
}

function mapStateToProps(store) {
  return {
    sidebarColor: store.layout.sidebarColor,
  };
}

export default withRouter(connect(mapStateToProps)(LinksGroup));
