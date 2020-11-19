import React, {useMemo} from 'react'
import {Route, Switch} from 'react-router-dom'
import {Nav, NavItem} from 'reactstrap'

import * as viewService from '../../../../../../services/projectBook/view'
import * as getResourceService from '../../../../../../services/projectBook/get/resource'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'
import {useBuildDynamicNavItemWithRows, useBuildRoutePath} from '../../../../../../hooks/project/path'

import {NavLink} from '../../../../../../components/Navigation'
import Widget from '../../../../../../components/Widget'
import MeasurementMethodLcms from '../../../../components/Single/MeasurementMethodLcms'

import ConditionLc from '../ConditionLc'
import ConditionMs from '../ConditionMs'
import ConditionOther from '../ConditionOther'
import {useLocale} from "../../../../../../hooks/i18n";

const Single = ({match}) => {
    const {id, measurementMethodLcmsId} = match.params
    const buildRoutePath = useBuildRoutePath()
    const buildDynamicNavItemWithRows = useBuildDynamicNavItemWithRows(id, `/method/measurement/measurement_method_lcms/${measurementMethodLcmsId}`)

    const projectBook = useProjectBook(id)
    const measurementMethodLcms = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodLcms(projectBook, measurementMethodLcmsId)
    }, [measurementMethodLcmsId, projectBook])

    const locale = useLocale()
    const navItems = useMemo(() => {
        if (!projectBook) return []
        const measurementMethodLcms = getResourceService.getMeasurementMethodLcms(projectBook, measurementMethodLcmsId)
        if (!measurementMethodLcms) return []
        return [
            buildDynamicNavItemWithRows(
                '（設定）LC', 'condition_lc',
                (measurementMethodLcms.lc_condition_id || []).map(conditionLcId => getResourceService.getConditionLc(projectBook, conditionLcId))
            ),
            buildDynamicNavItemWithRows(
                '（設定）MS', 'condition_ms',
                (measurementMethodLcms.ms_condition_id || []).map(conditionMsId => getResourceService.getConditionMs(projectBook, conditionMsId))
            ),
            buildDynamicNavItemWithRows(
                '（設定）その他', 'condition_other',
                (measurementMethodLcms.other_condition_id || []).map(conditionOtherId => getResourceService.getConditionOther(projectBook, conditionOtherId))
            )
        ].filter(Boolean)
    }, [buildDynamicNavItemWithRows, measurementMethodLcmsId, projectBook, locale])

    return (
        <Widget>
            <MeasurementMethodLcms measurementMethodLcms={measurementMethodLcms}/>
            <Nav pills className='mt-4 mb-4'>
                {navItems.map(({label, path}, index) =>
                    <NavItem key={index}><NavLink to={path}>{label}</NavLink></NavItem>
                )}
            </Nav>
            <Switch>
                <Route
                    path={buildRoutePath(`/method/measurement/:measurementMethodType/:measurementMethodId/condition_lc/:conditionLcId`)}
                    component={ConditionLc}/>
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