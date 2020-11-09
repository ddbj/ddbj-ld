import React, {useMemo} from 'react'
import {Route, Switch} from 'react-router-dom'
import {Nav, NavItem} from 'reactstrap'

import * as viewService from '../../../../../../services/projectBook/view'
import * as getResourceService from '../../../../../../services/projectBook/get/resource'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'
import {useBuildDynamicNavItemWithRows, useBuildRoutePath} from '../../../../../../hooks/project/path'

import {NavLink} from '../../../../../../components/Navigation'
import Widget from '../../../../../../components/Widget'
import MeasurementMethodGcms from '../../../../components/Single/MeasurementMethodGcms'

import ConditionGc from '../ConditionGc'
import ConditionMs from '../ConditionMs'
import ConditionOther from '../ConditionOther'
import {useLocale} from "../../../../../../hooks/i18n";

const Single = ({match}) => {
    const {id, measurementMethodGcmsId} = match.params
    const buildRoutePath = useBuildRoutePath()
    const buildDynamicNavItemWithRows = useBuildDynamicNavItemWithRows(id, `/method/measurement/measurement_method_gcms/${measurementMethodGcmsId}`)

    const projectBook = useProjectBook(id)
    const measurementMethodGcms = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodGcms(projectBook, measurementMethodGcmsId)
    }, [measurementMethodGcmsId, projectBook])

    const locale = useLocale()
    const navItems = useMemo(() => {
        if (!projectBook) return []
        const measurementMethodGcms = getResourceService.getMeasurementMethodGcms(projectBook, measurementMethodGcmsId)
        if (!measurementMethodGcms) return []
        return [
            buildDynamicNavItemWithRows(
                '（設定）LC', 'condition_gc',
                (measurementMethodGcms.gc_condition_id || []).map(conditionGcId => getResourceService.getConditionGc(projectBook, conditionGcId))
            ),
            buildDynamicNavItemWithRows(
                '（設定）MS', 'condition_ms',
                (measurementMethodGcms.ms_condition_id || []).map(conditionMsId => getResourceService.getConditionMs(projectBook, conditionMsId))
            ),
            buildDynamicNavItemWithRows(
                '（設定）その他', 'condition_other',
                (measurementMethodGcms.other_condition_id || []).map(conditionOtherId => getResourceService.getConditionOther(projectBook, conditionOtherId))
            )
        ].filter(Boolean)
    }, [buildDynamicNavItemWithRows, measurementMethodGcmsId, projectBook, locale])

    return (
        <Widget>
            <MeasurementMethodGcms measurementMethodGcms={measurementMethodGcms}/>
            <Nav pills className='mt-4 mb-4'>
                {navItems.map(({label, path}, index) =>
                    <NavItem key={index}><NavLink to={path}>{label}</NavLink></NavItem>
                )}
            </Nav>
            <Switch>
                <Route
                    path={buildRoutePath(`/method/measurement/:measurementMethodType/:measurementMethodId/condition_gc/:conditionGcId`)}
                    component={ConditionGc}/>
                <Route
                    path={buildRoutePath(`/method/measurement/:measurementMethodType/:measurementMethodId/condition_ms/:conditionMsId`)}
                    component={ConditionMs}/>
                <Route
                    path={buildRoutePath(`/method/measurement/:measurementMethodType/:measurementMethodId/condition_other/:conditionOtherId`)}
                    component={ConditionOther}/>
            </Switch>
        </Widget>
    )
}

export default Single