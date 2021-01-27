import React from 'react'
import { Link } from 'react-router-dom'
import { withRouter } from 'react-router-dom'
import sideBarStyle from './Sidebar.module.scss'
import LinksGroup from './LinksGroup/LinksGroup'
import {useIsAuthorized, useAuthAction, useCurrentUser} from '../../hooks/auth'
import { useIntl, FormattedMessage } from 'react-intl'
import config from "../../config"
import { useNavigation } from "../../hooks/navigation"

const Sidebar = ({ sidebarOpened, history }) => {
    const isAuthorized = useIsAuthorized()
    const intl = useIntl()
    const currentUser = useCurrentUser()

    const {
        onSignIn,
        onSignOut,
    } = useAuthAction(history)

    const { onHelp } = useNavigation()

    return (
        <div className={`${sidebarOpened ? '' : sideBarStyle.sidebarClose} ${sideBarStyle.sidebarWrapper}`}>
            <nav
                className={sideBarStyle.root}>
                <ul className={sideBarStyle.nav}>
                    <div style={{marginTop: 10}}>
                        <Link to={`/about`}><h5 className={[sideBarStyle.navTitle, sideBarStyle.groupTitle].join(' ')}>About</h5></Link>
                        <h5 className={[sideBarStyle.navTitle, sideBarStyle.groupTitle].join(' ')}><FormattedMessage id="sidebar.account" /></h5>
                        {isAuthorized
                            ?
                            <div>
                                <h5 className={[sideBarStyle.navTitle, sideBarStyle.groupTitle].join(' ')}>Login User: {currentUser.uid}</h5>
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
                                    link="/login"
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
                                            <i className="fi flaticon-info" style={{width:20}} onClick={(e) => onHelp(e, config.jVarHelp)}/>
                                        </span>
                                    }
                                    link="/entries/jvar"
                                    iconName="flaticon-database"
                                    labelColor="info"
                                    isHeader
                                />
                                <LinksGroup
                                    header={
                                        <span style={{width: '80%', display: 'flex', justifyContent: 'space-between'}}>BioSample
                                            <i className="fi flaticon-info" style={{width:20}} onClick={(e) => onHelp(e, config.bioSampleHelp)}/>
                                        </span>
                                    }
                                    link="/entries/biosample"
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
                </ul>
            </nav>
        </div>
    )
}

export default withRouter(Sidebar)
