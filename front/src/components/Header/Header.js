<<<<<<< HEAD
import React, { useCallback } from 'react'
import {connect} from 'react-redux'
import PropTypes from 'prop-types'

import {
=======
import React, {useCallback, useState} from 'react';
import {connect} from 'react-redux';
import PropTypes from 'prop-types';
import {useIntl} from 'react-intl'
import {useCurrentUser, useLoginURL} from '../../hooks/auth'

import {useHeaderSearchForm} from '../../hooks/search'

import {
    Dropdown,
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
>>>>>>> 差分修正
    Nav,
    Navbar,
    NavItem,
    NavLink,
    UncontrolledTooltip,
<<<<<<< HEAD
} from 'reactstrap'

import { NavbarTypes } from '../../reducers/layout'
import chroma from 'chroma-js'

import s from './Header.module.scss'

const Header = ({
=======
} from 'reactstrap';
import {NavbarTypes} from '../../reducers/layout';
import chroma from 'chroma-js'

import s from './Header.module.scss';

// FIXME 新規登録
const Header = ({
                    history,
>>>>>>> 差分修正
                    location,
                    sidebarOpened,
                    closeSidebar,
                    openSidebar,
                    navbarType,
                    navbarColor
                }) => {
<<<<<<< HEAD
=======
    const intl = useIntl()
    const currentUser = useCurrentUser()
    const {
        keyword,
        updateKeyword,
        submitHandler,
    } = useHeaderSearchForm({history, location})

    const [focus, setFocus] = useState(false)

    const [dropdownOpen, setDropdownOpen] = useState(false);

    const toggleFocus = useCallback(() => {
        setFocus(!focus)
    }, [focus])
>>>>>>> 差分修正

    const switchSidebar = useCallback(() => {
        if (sidebarOpened) {
            closeSidebar()
            return;
        }
        openSidebar(location.pathname)
    }, [closeSidebar, location.pathname, openSidebar, sidebarOpened])

<<<<<<< HEAD
=======
    const loginURL = useLoginURL();

    const editProfile = () => {
        history.push(`/me/setting/profile`)
    }

    const logOut = () => {
        history.push(`/signout`)
    }

    const onSignIn = () => {
        window.location.href = loginURL
    }

>>>>>>> 差分修正
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

<<<<<<< HEAD
=======
            {/*<Form className={`d-sm-down-none ml-5 ${s.headerSearchInput}`} inline　onSubmit={submitHandler}>*/}
            {/*  <FormGroup>*/}
            {/*    <InputGroup onFocus={toggleFocus} onBlur={toggleFocus} className={*/}
            {/*      cx('input-group-no-border', {'focus' : !!focus})*/}
            {/*    }>*/}
            {/*      <InputGroupAddon addonType="prepend">*/}
            {/*        <i className="la la-search" />*/}
            {/*      </InputGroupAddon>*/}
            {/*      <Input*/}
            {/*        value={keyword} onChange={updateKeyword}*/}
            {/*        id="search-input" placeholder={intl.formatMessage({id: 'header.searchProject' })} className={cx({'focus' : !!focus})} />*/}
            {/*    </InputGroup>*/}
            {/*  </FormGroup>*/}
            {/*</Form>*/}

>>>>>>> 差分修正
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
<<<<<<< HEAD
        </Navbar>
    )
=======

            {/*<Nav className="ml-auto align-items-center">*/}
            {/*    {currentUser ? (*/}
            {/*        <>*/}
            {/*            <Dropdown*/}
            {/*                nav*/}
            {/*                isOpen={dropdownOpen}*/}
            {/*                toggle={() => setDropdownOpen(!dropdownOpen)}*/}
            {/*            >*/}
            {/*                <DropdownToggle*/}
            {/*                    className={`${chroma(navbarColor).luminance() < 0.4 ? "text-white" : ""}`}*/}
            {/*                    nav*/}
            {/*                    caret*/}
            {/*                >*/}
            {/*      <span className={`${s.avatar} rounded-circle thumb-sm float-left mr-2`}>*/}
            {/*          <span*/}
            {/*              style={*/}
            {/*                  {*/}
            {/*                      fontSize: 11,*/}
            {/*                      width: "100%",*/}
            {/*                      wordWrap: "break-word"*/}
            {/*                  }*/}
            {/*              }*/}
            {/*          >{currentUser.profile.displayName || currentUser.uid}</span>*/}
            {/*      </span>*/}
            {/*                </DropdownToggle>*/}
            {/*                <DropdownMenu*/}
            {/*                    right={true}*/}
            {/*                >*/}
            {/*                    /!* FIXME プロフィール更新は一旦保留 *!/*/}
            {/*                    /!*<DropdownItem onClick={editProfile}>*!/*/}
            {/*                    /!*  <span style={{fontSize: 13}}>*!/*/}
            {/*                    /!*    プロフィール*!/*/}
            {/*                    /!*  </span>*!/*/}
            {/*                    /!*</DropdownItem>*!/*/}
            {/*                    /!*<DropdownItem divider/>*!/*/}
            {/*                    <DropdownItem onClick={logOut}>*/}
            {/*      <span style={{fontSize: 13}}>*/}
            {/*          ログアウト*/}
            {/*      </span>*/}
            {/*                    </DropdownItem>*/}
            {/*                </DropdownMenu>*/}
            {/*            </Dropdown>*/}
            {/*        </>*/}
            {/*    ) : <a onClick={onSignIn}>サインイン</a>}*/}
            {/*</Nav>*/}
        </Navbar>
    );
>>>>>>> 差分修正
}

Header.propTypes = {
    sidebarOpened: PropTypes.bool.isRequired,
    sidebarStatic: PropTypes.bool.isRequired,
    dispatch: PropTypes.func.isRequired,
    location: PropTypes.shape({
        pathname: PropTypes.string,
    }).isRequired,
}

<<<<<<< HEAD
const mapStateToProps = (store) => {
    return {
        navbarType: store.layout.navbarType,
        navbarColor: store.layout.navbarColor,
    }
}

export default connect(mapStateToProps)(Header)
=======
function mapStateToProps(store) {
    return {
        navbarType: store.layout.navbarType,
        navbarColor: store.layout.navbarColor,
    };
}

export default connect(mapStateToProps)(Header);
>>>>>>> 差分修正
