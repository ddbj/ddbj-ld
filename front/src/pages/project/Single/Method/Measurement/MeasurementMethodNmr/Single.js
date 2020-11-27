import React, {useMemo} from 'react'
import {Route, Switch} from 'react-router-dom'
import {Nav, NavItem} from 'reactstrap'

import * as viewService from '../../../../../../services/projectBook/view'
import * as getResourceService from '../../../../../../services/projectBook/get/resource'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'
import {useBuildDynamicNavItemWithRows, useBuildRoutePath} from '../../../../../../hooks/project/path'

import {NavLink} from '../../../../../../components/Navigation'
import Widget from '../../../../../../components/Widget'
import MeasurementMethodNmr from '../../../../components/Single/MeasurementMethodNmr'

import ConditionNmr from '../ConditionNmr'
import ConditionOther from '../ConditionOther'
import {useLocale} from "../../../../../../hooks/i18n";

const Single = ({match}) => {
    const {id, measurementMethodNmrId} = match.params
    const buildRoutePath = useBuildRoutePath()
    const buildDynamicNavItemWithRows = useBuildDynamicNavItemWithRows(id, `/method/measurement/measurement_method_nmr/${measurementMethodNmrId}`)

    const projectBook = useProjectBook(id)
    const measurementMethodNmr = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodNmr(projectBook, measurementMethodNmrId)
    }, [measurementMethodNmrId, projectBook])

    const locale = useLocale()
    const navItems = useMemo(() => {
        if (!projectBook) return []
        const measurementMethodNmr = getResourceService.getMeasurementMethodNmr(projectBook, measurementMethodNmrId)
        if (!measurementMethodNmr) return []
        return [
            buildDynamicNavItemWithRows(
                '（設定）NMR', 'condition_nmr',
                (measurementMethodNmr.nmr_condition_id || []).map(conditionNmrId => getResourceService.getConditionNmr(projectBook, conditionNmrId))
            ),
            buildDynamicNavItemWithRows(
                '（設定）その他', 'condition_other',
                (measurementMethodNmr.other_condition_id || []).map(conditionOtherId => getResourceService.getConditionOther(projectBook, conditionOtherId))
            )
        ].filter(Boolean)
    }, [buildDynamicNavItemWithRows, measurementMethodNmrId, projectBook, locale])

    return (
        <Widget>
            <MeasurementMethodNmr measurementMethodNmr={measurementMethodNmr}/>
            <Nav pills className='mt-4 mb-4'>
                {navItems.map(({label, path}, index) =>
                    <NavItem key={index}><NavLink to={path}>{label}</NavLink></NavItem>
                )}
            </Nav>
            <Switch>
                <Route
                    path={buildRoutePath(`/method/measurement/:measurementMethodType/:measurementMethodId/condition_ms/:conditionNmrId`)}
                    component={ConditionNmr}/>
                <Route
                    path={buildRoutePath(`/method/measurement/:measurementMethodType/:measurementMethodId/condition_other/:conditionOtherId`)}
                    component={ConditionOther}/>
            </Switch>
        </Widget>
    )
}

export default Single