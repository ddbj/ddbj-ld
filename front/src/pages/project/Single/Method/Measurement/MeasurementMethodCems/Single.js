import React, {useMemo} from 'react'
import {Route, Switch} from 'react-router-dom'
import {Nav, NavItem} from 'reactstrap'

import * as viewService from '../../../../../../services/projectBook/view'
import * as getResourceService from '../../../../../../services/projectBook/get/resource'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'
import {useBuildDynamicNavItemWithRows, useBuildRoutePath} from '../../../../../../hooks/project/path'

import {NavLink} from '../../../../../../components/Navigation'
import Widget from '../../../../../../components/Widget'
import MeasurementMethodCems from '../../../../components/Single/MeasurementMethodCems'

import ConditionCe from '../ConditionCe'
import ConditionMs from '../ConditionMs'
import ConditionOther from '../ConditionOther'
import {useLocale} from "../../../../../../hooks/i18n";

const Single = ({match}) => {
    const {id, measurementMethodCemsId} = match.params
    const buildRoutePath = useBuildRoutePath()
    const buildDynamicNavItemWithRows = useBuildDynamicNavItemWithRows(id, `/method/measurement/measurement_method_cems/${measurementMethodCemsId}`)

    const projectBook = useProjectBook(id)
    const measurementMethodCems = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodCems(projectBook, measurementMethodCemsId)
    }, [measurementMethodCemsId, projectBook])

    const locale = useLocale()
    const navItems = useMemo(() => {
        if (!projectBook) return []
        const measurementMethodCems = getResourceService.getMeasurementMethodCems(projectBook, measurementMethodCemsId)
        if (!measurementMethodCems) return []
        return [
            buildDynamicNavItemWithRows(
                '（設定）CE', 'condition_ce',
                (measurementMethodCems.ce_condition_id || []).map(conditionCeId => getResourceService.getConditionCe(projectBook, conditionCeId))
            ),
            buildDynamicNavItemWithRows(
                '（設定）MS', 'condition_ms',
                (measurementMethodCems.ms_condition_id || []).map(conditionMsId => getResourceService.getConditionMs(projectBook, conditionMsId))
            ),
            buildDynamicNavItemWithRows(
                '（設定）その他', 'condition_other',
                (measurementMethodCems.other_condition_id || []).map(conditionOtherId => getResourceService.getConditionOther(projectBook, conditionOtherId))
            )
        ].filter(Boolean)
    }, [buildDynamicNavItemWithRows, measurementMethodCemsId, projectBook, locale])

    return (
        <Widget>
            <MeasurementMethodCems measurementMethodCems={measurementMethodCems}/>
            <Nav pills className='mt-4 mb-4'>
                {navItems.map(({label, path}, index) =>
                    <NavItem key={index}><NavLink to={path}>{label}</NavLink></NavItem>
                )}
            </Nav>
            <Switch>
                <Route
                    path={buildRoutePath(`/method/measurement/:measurementMethodType/:measurementMethodId/condition_ce/:conditionCeId`)}
                    component={ConditionCe}/>
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