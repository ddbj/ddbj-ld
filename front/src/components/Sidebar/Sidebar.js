import React, { useCallback } from 'react'
import { Link } from 'react-router-dom'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { withRouter } from 'react-router-dom'
import sideBarStyle from './Sidebar.module.scss'
import LinksGroup from './LinksGroup/LinksGroup'
import isScreen from '../../core/screenHelper'
import { useIsAdmin, useIsAuthorized, useLoginURL } from '../../hooks/auth'
import { useIntl, FormattedMessage } from 'react-intl'
import ButtonGroup from "react-bootstrap/ButtonGroup";
import Button from "react-bootstrap/Button";
import {Col} from "react-bootstrap";
import {useChangeLocale, useLocale} from "../../hooks/i18n";
import config from "../../config"

const Sidebar = ({ sidebarOpened, activeItem, setSidebarOpened, setActiveItem, history }) => {
    const isAdmin = useIsAdmin()
    const isAuthorized = useIsAuthorized()
    const intl = useIntl()
    const loginURL = useLoginURL();
    const locale = useLocale()
    const changeLocale = useChangeLocale()

    const mouseEnterHandler = useCallback(() => {
        if (!isScreen('lg') && !isScreen('xl')) return
        setSidebarOpened(true)
    }, [setSidebarOpened])

    const onSignIn = useCallback(() => {
        window.location.href = loginURL
    }, [])

    const onSignOut = useCallback(() => {
        history.push(`/signout`)
    }, [])

    return (
        <div className={`${sidebarOpened ? '' : sideBarStyle.sidebarClose} ${sideBarStyle.sidebarWrapper}`}>
            <nav
                className={sideBarStyle.root}>
                <ul className={sideBarStyle.nav}>
                    { isAuthorized ? (
                        <div style={{marginTop: 10}}>
                            <Link to={`/about`}><h5 className={[sideBarStyle.navTitle, sideBarStyle.groupTitle].join(' ')}>About</h5></Link>
                            <h5 className={[sideBarStyle.navTitle, sideBarStyle.groupTitle].join(' ')}><FormattedMessage id="sidebar.account" /></h5>
                            {isAuthorized
                                ?
                                <div>
                                    <h5 className={[sideBarStyle.navTitle, sideBarStyle.groupTitle].join(' ')}>Login User: testUser</h5>
                                    <LinksGroup
                                        header="My Page"
                                        link="/me"
                                        isHeader
                                        labelColor="info"
                                        iconName="flaticon-database"
                                    >
                                    </LinksGroup>
                                    <div onClick={onSignOut}>
                                        <LinksGroup
                                            header={intl.formatMessage({id: 'sidebar.signout'})}
                                            isHeader
                                            labelColor="info"
                                            iconName="flaticon-exit-1"
                                        >
                                        </LinksGroup>
                                    </div>
                                </div>
                                :
                                <div onClick={onSignIn}>
                                    <LinksGroup
                                        header={intl.formatMessage({id: 'sidebar.signin'})}
                                        isHeader
                                        labelColor="info"
                                        iconName="flaticon-login"
                                    >
                                    </LinksGroup>
                                </div>
                            }
                            {isAuthorized ?
                                <>
                                    <h5 className={[sideBarStyle.navTitle, sideBarStyle.groupTitle].join(' ')}>Entries</h5>
                                    <LinksGroup
                                        header={
                                            <span style={{width: '80%', display: 'flex', justifyContent: 'space-between'}}>JVar
                                                <a href="https://www.ddbj.nig.ac.jp/jvar" target="_blank" style={{width:20}}>
                                                    <i className="fi flaticon-info" />
                                                </a>
                                            </span>
                                        }
                                        link="/entries/jvar"
                                        iconName="flaticon-database"
                                        labelColor="info"
                                        isHeader
                                    />
                                    <LinksGroup
                                        header={
                                            <span style={{width: '80%', display: 'flex', justifyContent: 'space-between'}}>BioProject
                                                <a href="https://www.ddbj.nig.ac.jp/bioproject" target="_blank" style={{width:20}}>
                                                    <i className="fi flaticon-info" />
                                                </a>
                                            </span>
                                        }
                                        link="/entries/bioproject"
                                        iconName="flaticon-database"
                                        labelColor="info"
                                        isHeader
                                    />
                                    <LinksGroup
                                        header={
                                            <span style={{width: '80%', display: 'flex', justifyContent: 'space-between'}}>BioSample
                                                <a href="https://www.ddbj.nig.ac.jp/biosample" target="_blank" style={{width:20}}>
                                                    <i className="fi flaticon-info" />
                                                </a>
                                            </span>
                                        }
                                        link="/entries/biosample"
                                        iconName="flaticon-database"
                                        labelColor="info"
                                        isHeader
                                    />
                                    <LinksGroup
                                        header={
                                            <span style={{width: '80%', display: 'flex', justifyContent: 'space-between'}}>Trad
                                                <a href="https://www.ddbj.nig.ac.jp/biosample" target="_blank" style={{width:20}}>
                                                    <i className="fi flaticon-info" />
                                                </a>
                                            </span>
                                        }
                                        link="/entries/trad"
                                        iconName="flaticon-database"
                                        labelColor="info"
                                        isHeader
                                    />
                                </>
                                : null
                            }
                            <h5 className={[sideBarStyle.navTitle, sideBarStyle.groupTitle].join(' ')}>Resources</h5>
                            <LinksGroup
                                header="Search"
                                link="/search"
                                iconName="flaticon-search"
                                labelColor="info"
                                isHeader
                            />
                        </div>
                    ) : null}
                </ul>
            </nav>
        </div>
    )
}

Sidebar.propTypes = {
    sidebarOpened: PropTypes.bool,
    dispatch: PropTypes.func.isRequired,
    activeItem: PropTypes.string,
    location: PropTypes.shape({
        pathname: PropTypes.string,
    }).isRequired,
}

Sidebar.defaultProps = {
    sidebarOpened: false,
    activeItem: '',
}

function mapStateToProps(store) {
    return {
        navbarType: store.layout.navbarType,
        sidebarColor: store.layout.sidebarColor,
    }
}

export default withRouter(connect(mapStateToProps)(Sidebar))
