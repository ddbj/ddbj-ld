import React, {useMemo} from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import {Nav, NavItem} from 'reactstrap'

import * as getResourceService from '../../../../../services/projectBook/get/resource'

import {useProjectBook} from '../../../../../hooks/project/projectBook'
import {
    useBuildDynamicNavItemWithRows,
    useBuildNavItem,
    useBuildPath,
    useBuildRoutePath
} from '../../../../../hooks/project/path'

import {NavLink} from '../../../../../components/Navigation'

import ExtractionMethod from './ExtractionMethod'
import MeasurementMethodLcms from './MeasurementMethodLcms'
import MeasurementMethodGcms from './MeasurementMethodGcms'
import MeasurementMethodCems from './MeasurementMethodCems'
import MeasurementMethodMs from './MeasurementMethodMs'
import MeasurementMethodNmr from './MeasurementMethodNmr'
import MeasurementMethodOther from './MeasurementMethodOther'
import Instrument from './Instrument'
import Column from './Column'
import {useIntl} from "react-intl";
import {useLocale} from "../../../../../hooks/i18n";

const BASE_PATH = '/method/measurement'

const Measurement = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    const buildNavItem = useBuildNavItem(id, BASE_PATH)
    const buildDynamicNavItemWithRows = useBuildDynamicNavItemWithRows(id, BASE_PATH)

    const projectBook = useProjectBook(id)
    const intl = useIntl()
    const locale = useLocale()
    const navItems = useMemo(() => {
        if (!projectBook || 0 === Object.keys(projectBook).length) return []

        return [
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.extraction_method'}), 'extraction_method', getResourceService.getExtractionMethodList(projectBook)),
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.measurement_method_lcms'}), 'measurement_method_lcms', getResourceService.getMeasurementMethodLcmsList(projectBook)),
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.measurement_method_gcms'}), 'measurement_method_gcms', getResourceService.getMeasurementMethodGcmsList(projectBook)),
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.measurement_method_cems'}), 'measurement_method_cems', getResourceService.getMeasurementMethodCemsList(projectBook)),
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.measurement_method_ms'}), 'measurement_method_ms', getResourceService.getMeasurementMethodMsList(projectBook)),
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.measurement_method_nmr'}), 'measurement_method_nmr', getResourceService.getMeasurementMethodNmrList(projectBook)),
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.measurement_method_other'}), 'measurement_method_other', getResourceService.getMeasurementMethodOtherList(projectBook)),
            buildNavItem(intl.formatMessage({id: 'sheet.condition_lc.instrument'}), '/instrument'),
            buildNavItem(intl.formatMessage({id: 'sheet.condition_lc.column'}), '/column')
        ].filter(Boolean)
    }, [buildDynamicNavItemWithRows, buildNavItem, projectBook, locale])

    const defaultPath = useMemo(() =>
            navItems.length > 0 ? navItems[0].path : buildPath(id, '/method')
        , [buildPath, id, navItems])

    if (!projectBook || 0 === Object.keys(projectBook).length) return null

    return (
        <>
            <div className="d-flex justify-content-start">
                <Nav tabs>
                    {navItems.map(({label, path}, index) =>
                        <NavItem key={index}>
                            <NavLink to={path}>{label}</NavLink>
                        </NavItem>
                    )}
                </Nav>
            </div>
            <Switch>
                <Route path={buildRoutePath('/method/measurement/extraction_method')} component={ExtractionMethod}/>
                <Route path={buildRoutePath('/method/measurement/measurement_method_lcms')}
                       component={MeasurementMethodLcms}/>
                <Route path={buildRoutePath('/method/measurement/measurement_method_gcms')}
                       component={MeasurementMethodGcms}/>
                <Route path={buildRoutePath('/method/measurement/measurement_method_cems')}
                       component={MeasurementMethodCems}/>
                <Route path={buildRoutePath('/method/measurement/measurement_method_ms')}
                       component={MeasurementMethodMs}/>
                <Route path={buildRoutePath('/method/measurement/measurement_method_nmr')}
                       component={MeasurementMethodNmr}/>
                <Route path={buildRoutePath('/method/measurement/measurement_method_other')}
                       component={MeasurementMethodOther}/>
                <Route path={buildRoutePath('/method/measurement/instrument')} component={Instrument}/>
                <Route path={buildRoutePath('/method/measurement/column')} component={Column}/>
                <Redirect to={defaultPath}/>
            </Switch>
        </>
    )
}

export default Measurement
