import React, {useCallback, useEffect, useState} from 'react'
import PropTypes from 'prop-types'
import {connect, useDispatch, useSelector} from 'react-redux'
import {Redirect, Route, Switch, withRouter} from 'react-router-dom'
// import { TransitionGroup, CSSTransition } from 'react-transition-group';
import Hammer from 'rc-hammerjs';

import Search from '../../pages/search';
import Admin from '../../pages/admin';
import About from '../../pages/about';
import Me from '../../pages/me';
import Download from '../../pages/download';
// import Profile from '../../pages/profile';
// import UIButtons from '../../pages/ui-elements/buttons';
// import UIIcons from '../../pages/ui-elements/icons';
// import UITabsAccordion from '../../pages/ui-elements/tabs-accordion/';
// import UINotifications from '../../pages/ui-elements/notifications';
// import UIListGroups from '../../pages/ui-elements/list-groups';
// import FormsElements from '../../pages/forms/elements';
// import FormsValidation from '../../pages/forms/validation';
// import FormsWizard from '../../pages/forms/wizard';
// import TablesStatic from '../../pages/tables/static';
// import TablesDynamic from '../../pages/tables/dynamic';
// import MapsGoogle from '../../pages/maps/google';
// import MapsVector from '../../pages/maps/vector';
// import ExtraCalendar from '../../pages/extra/calendar';
// import ExtraInvoice from '../../pages/extra/invoice';
// import ExtraSearch from '../../pages/extra/search';
// import ExtraTimeline from '../../pages/extra/timeline';
// import ExtraGallery from '../../pages/extra/gallery';
// import Grid from '../../pages/grid';
// import ChatPage from '../../pages/chat';
// import Widgets from '../../pages/widgets';
// import Products from '../../pages/products';
// import Management from '../../pages/management';
// import Product from '../../pages/product';
// import Package from '../../pages/package';
// import Email from '../../pages/email';
// import CoreTypography from '../../pages/core/typography';
// import CoreColors from '../../pages/core/colors';
// import CoreGrid from '../../pages/core/grid';
// import UIAlerts from '../../pages/ui-elements/alerts';
// import UIBadge from '../../pages/ui-elements/badge';
// import UICard from '../../pages/ui-elements/card';
// import UICarousel from '../../pages/ui-elements/carousel';
// import UIJumbotron from '../../pages/ui-elements/jumbotron';
// import UIModal from '../../pages/ui-elements/modal';
import UIProgress from '../../pages/ui-elements/progress';
// import UINavbar from '../../pages/ui-elements/navbar';
// import UINav from '../../pages/ui-elements/nav';
// import UIPopovers from '../../pages/ui-elements/popovers';
// import Charts from '../../pages/charts';
// import ApexCharts from '../../pages/charts/apex';
// import Echarts from '../../pages/charts/echarts';
// import HighCharts from '../../pages/charts/highcharts';
// import DashboardAnalytics from '../../pages/analytics';
// import Dashboard from '../../pages/dashboard';
import {DashboardThemes, SidebarTypes} from '../../reducers/layout';
import Header from '../Header';
import Sidebar from '../Sidebar';
// import Helper from '../Helper';
import s from './Layout.module.scss';
// import ProductEdit from '../../pages/management/components/productEdit';
// import BreadcrumbHistory from '../BreadcrumbHistory';
import {useNavigation} from '../../hooks/navigation';
import {useIsAdmin, useIsAuthorized} from '../../hooks/auth';
import {useLocale} from '../../hooks/i18n'
import {Col, Row} from "react-bootstrap";
import Project from "../../pages/project/Project";
import Entries from "../../pages/entries"
import {useIntl} from "react-intl"
import {toast} from 'react-toastify'
import * as layoutAction from '../../actions/layout'
import AuthErrorPage from "../../pages/error/401";
import NotFound from "../../pages/error";
import Authorize from "../../pages/authorize"
import { setCurrentProjectView } from "../../actions/project"

const Layout = ({dashboardTheme, location, history, sidebarType, errorKey}) => {
    const isAdmin = useIsAdmin()
    const isAuthorized = useIsAuthorized()
    const locale = useLocale()

    const navigation = useNavigation()
    const intl = useIntl()
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(setCurrentProjectView(null))
    }, [])

    // エラーのポップアップの設定
    useEffect(() => {
        if(errorKey) {
            toast.error(intl.formatMessage({id: errorKey}))
        }

        dispatch(layoutAction.setErrorKey(null))
    }, [errorKey])

    const {sidebarStatic, sidebarOpened, handleSwipe} = navigation
    return (
        <div
            className={[
                s.root,
                sidebarStatic ? s.sidebarStatic : '',
                sidebarOpened ? '' : s.sidebarClose,
                'sing-dashboard',
                `dashboard-${(sidebarType === SidebarTypes.TRANSPARENT) ? "light" : dashboardTheme}`,
            ].join(' ')}
        >
            <Sidebar {...navigation} />
            <div className={s.wrap}>
                <Header
                    {...navigation}
                    history={history} location={location}/>
                {/* <Helper /> */}
                <Hammer onSwipe={handleSwipe}>
                    <main className={s.content}>
                        {/* <BreadcrumbHistory url={location.pathname} /> */}
                        {/* <TransitionGroup> */}
                        {/* <CSSTransition
                  key={location.key}
                  classNames="fade"
                  timeout={200}
                > */}
                        <Switch>
                            {/* FIXME このあたり不要なものを削除していく */}
                            <Route path="/about" component={About}/>
                            <Route path="/search" component={Search}/>
                            <Route path="/project" component={Project}/>
                            <Route path="/entries" component={Entries}/>
                            <Route path="/download" component={Download}/>
                            {isAuthorized ? (<Route path="/me" component={Me}/>) : null}
                            {isAdmin ? (<Route path="/admin" component={Admin}/>) : null}
                            <Route path="/401" component={AuthErrorPage}/>
                            <Route path="/404" component={NotFound}/>
                            <Route path="/authorize" component={Authorize}/>
                            <Redirect to="/me"/>
                            {/* <Route path="/app/main" exact render={() => <Redirect to="/app/main/analytics" />} />
                    <Route path="/app/main/dashboard" exact component={Dashboard} />
                    <Route path="/app/main/widgets" exact component={Widgets} />
                    <Route path="/app/main/analytics" exact component={DashboardAnalytics} />
                    <Route path="/app/ecommerce/management" exact component={Management} />
                    <Route path="/app/ecommerce/management/:id" exact component={ProductEdit} />
                    <Route path="/app/ecommerce/management/create" exact component={ProductEdit} />
                    <Route path="/app/ecommerce/products" exact component={Products} />
                    <Route path="/app/ecommerce/product" exact component={Product} />
                    <Route path="/app/ecommerce/product/:id" exact component={Product} />
                    <Route path="/app/profile" exact component={Profile} />
                    <Route path="/app/inbox" exact component={Email} />
                    <Route path="/app/ui" exact render={() => <Redirect to="/app/ui/components" />} />
                    <Route path="/app/ui/buttons" exact component={UIButtons} />
                    <Route path="/app/ui/icons" exact component={UIIcons} />
                    <Route path="/app/ui/tabs-accordion" exact component={UITabsAccordion} />
                    <Route path="/app/ui/notifications" exact component={UINotifications} />
                    <Route path="/app/ui/list-groups" exact component={UIListGroups} />
                    <Route path="/app/ui/alerts" exact component={UIAlerts} />
                    <Route path="/app/ui/badge" exact component={UIBadge} />
                    <Route path="/app/ui/card" exact component={UICard} />
                    <Route path="/app/ui/carousel" exact component={UICarousel} />
                    <Route path="/app/ui/jumbotron" exact component={UIJumbotron} />
                    <Route path="/app/ui/modal" exact component={UIModal} />
                    <Route path="/app/ui/popovers" exact component={UIPopovers} />
                    <Route path="/app/ui/progress" exact component={UIProgress} />
                    <Route path="/app/ui/navbar" exact component={UINavbar} />
                    <Route path="/app/ui/nav" exact component={UINav} />
                    <Route path="/app/grid" exact component={Grid} />
                    <Route path="/app/chat" exact component={ChatPage} />
                    <Route path="/app/package" exact component={Package} />
                    <Route path="/app/forms" exact render={() => <Redirect to="/app/forms/elements" />} />
                    <Route path="/app/forms/elements" exact component={FormsElements} />
                    <Route path="/app/forms/validation" exact component={FormsValidation} />
                    <Route path="/app/forms/wizard" exact component={FormsWizard} />
                    <Route path="/app/charts/" exact render={() => <Redirect to="/app/charts/overview" />} />
                    <Route path="/app/charts/overview" exact component={Charts} />
                    <Route path="/app/charts/apex" exact component={ApexCharts} />
                    <Route path="/app/charts/echarts" exact component={Echarts} />
                    <Route path="/app/charts/highcharts" exact component={HighCharts} />
                    <Route path="/app/tables" exact render={() => <Redirect to="/app/tables/static" />} />
                    <Route path="/app/tables/static" exact component={TablesStatic} />
                    <Route path="/app/tables/dynamic" exact component={TablesDynamic} />
                    <Route path="/app/extra" exact render={() => <Redirect to="/app/extra/calendar" />} />
                    <Route path="/app/extra/calendar" exact component={ExtraCalendar} />
                    <Route path="/app/extra/invoice" exact component={ExtraInvoice} />
                    <Route path="/app/extra/search" exact component={ExtraSearch} />
                    <Route path="/app/extra/timeline" exact component={ExtraTimeline} />
                    <Route path="/app/extra/gallery" exact component={ExtraGallery} />
                    <Route path="/app/maps" exact render={() => <Redirect to="/app/maps/google" />} />
                    <Route path="/app/maps/google" exact component={MapsGoogle} />
                    <Route path="/app/maps/vector" exact component={MapsVector} />
                    <Route path="/app/core" exact render={() => <Redirect to="/app/core/typography" />} />
                    <Route path="/app/core/typography" exact component={CoreTypography} />
                    <Route path="/app/core/colors" exact component={CoreColors} />
                    <Route path="/app/core/grid" exact component={CoreGrid} /> */}
                        </Switch>
                        {/* </CSSTransition> */}
                        {/* </TransitionGroup> */}
                    </main>
                </Hammer>
            </div>
        </div>
    );
}

Layout.propTypes = {
    dashboardTheme: PropTypes.string
}

Layout.defaultProps = {
    dashboardTheme: DashboardThemes.DARK
}

function mapStateToProps(store) {
    return {
        dashboardTheme: store.layout.dashboardTheme,
        sidebarType: store.layout.sidebarType,
        errorKey: store.layout.errorKey
    };
}

export default withRouter(connect(mapStateToProps)(Layout));
