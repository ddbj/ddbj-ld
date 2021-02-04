import React, { useCallback } from 'react'
import {connect} from 'react-redux'
import PropTypes from 'prop-types'

import {
    Nav,
    Navbar,
    NavItem,
    NavLink,
    UncontrolledTooltip,
} from 'reactstrap'

import { NavbarTypes } from '../../reducers/layout'
import chroma from 'chroma-js'

import s from './Header.module.scss'

const Header = ({
                    location,
                    sidebarOpened,
                    closeSidebar,
                    openSidebar,
                    navbarType,
                    navbarColor
                }) => {

    const switchSidebar = useCallback(() => {
        if (sidebarOpened) {
            closeSidebar()
            return;
        }
        openSidebar(location.pathname)
    }, [closeSidebar, location.pathname, openSidebar, sidebarOpened])

    return (
        <Navbar className={`${s.root} d-print-none ${navbarType === NavbarTypes.FLOATING ? s.navbarFloatingType : ''}`}
                style={{backgroundColor: navbarColor}}>
            <Nav>
                <NavItem>
                    <NavLink className="d-md-down-none ml-5" id="toggleSidebar" onClick={switchSidebar}>
                        <i className={`la la-bars ${chroma(navbarColor).luminance() < 0.4 ? "text-white" : ""}`}/>
                    </NavLink>
                    <UncontrolledTooltip placement="bottom" target="toggleSidebar">
                        Turn on/off<br/>sidebar<br/>collapsing
                    </UncontrolledTooltip>
                    <NavLink className="fs-lg d-lg-none" onClick={switchSidebar}>
          <span
              className={`rounded rounded-lg d-md-none d-sm-down-block`}>
              <i
                  className="la la-bars"
                  style={{
                      fontSize: 30, color: navbarColor === "#ffffff"
                          ? "#ffffff"
                          : chroma(navbarColor).luminance() < 0.4 ? "#ffffff" : ""
                  }}
              />
            </span>
                        <i className={`la la-bars ml-3 d-sm-down-none ${chroma(navbarColor).luminance() < 0.4 ? "text-white" : ""}`}/>
                    </NavLink>
                </NavItem>
            </Nav>

            <NavLink
                className={`${s.navbarBrand} ${chroma(navbarColor).luminance() < 0.4 ? "text-white" : ""}`}>
                <i className="fa fa-circle text-primary mr-n-sm"/>
                <i className="fa fa-circle text-danger"/>
                &nbsp;
                DDBJ Web Service
                &nbsp;
                <i className="fa fa-circle text-danger mr-n-sm"/>
                <i className="fa fa-circle text-primary"/>
            </NavLink>
        </Navbar>
    )
}

Header.propTypes = {
    sidebarOpened: PropTypes.bool.isRequired,
    sidebarStatic: PropTypes.bool.isRequired,
    dispatch: PropTypes.func.isRequired,
    location: PropTypes.shape({
        pathname: PropTypes.string,
    }).isRequired,
}

const mapStateToProps = (store) => {
    return {
        navbarType: store.layout.navbarType,
        navbarColor: store.layout.navbarColor,
    }
}

export default connect(mapStateToProps)(Header)
