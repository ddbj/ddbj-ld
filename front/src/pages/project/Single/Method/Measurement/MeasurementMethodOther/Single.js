import React, {useMemo} from 'react'
import {Route, Switch} from 'react-router-dom'
import {Nav, NavItem} from 'reactstrap'

import * as viewService from '../../../../../../services/projectBook/view'
import * as getResourceService from '../../../../../../services/projectBook/get/resource'

import {useProjectBook} from '../../../../../../hooks/project/projectBook'
import {useBuildDynamicNavItemWithRows, useBuildRoutePath} from '../../../../../../hooks/project/path'

import {NavLink} from '../../../../../../components/Navigation'
import Widget from '../../../../../../components/Widget'
import MeasurementMethodOther from '../../../../components/Single/MeasurementMethodOther'

import ConditionOther from '../ConditionOther'
import {useLocale} from "../../../../../../hooks/i18n";

const Single = ({match}) => {
    const {id, measurementMethodOtherId} = match.params
    const buildRoutePath = useBuildRoutePath()
    const buildDynamicNavItemWithRows = useBuildDynamicNavItemWithRows(id, `/method/measurement/measurement_method_other/${measurementMethodOtherId}`)

    const projectBook = useProjectBook(id)
    const measurementMethodOther = useMemo(() => {
        if (!projectBook) return null
        return viewService.getMeasurementMethodOther(projectBook, measurementMethodOtherId)
    }, [measurementMethodOtherId, projectBook])

    const locale = useLocale()
    const navItems = useMemo(() => {
        if (!projectBook) return []
        const measurementMethodOther = getResourceService.getMeasurementMethodOther(projectBook, measurementMethodOtherId)
        if (!measurementMethodOther) return []
        return [
            buildDynamicNavItemWithRows(
                '（設定）その他', 'condition_other',
                (measurementMethodOther.other_condition_id || []).map(conditionOtherId => getResourceService.getConditionOther(projectBook, conditionOtherId))
            )
        ].filter(Boolean)
    }, [buildDynamicNavItemWithRows, measurementMethodOtherId, projectBook, locale])

    return (
        <Widget>
            <MeasurementMethodOther measurementMethodOther={measurementMethodOther}/>
            <Nav pills className='mt-4 mb-4'>
                {navItems.map(({label, path}, index) =>
                    <NavItem key={index}><NavLink to={path}>{label}</NavLink></NavItem>
                )}
            </Nav>
            <Switch>
                <Route
                    path={buildRoutePath(`/method/measurement/:measurementMethodType/:measurementMethodId/condition_other/:conditionOtherId`)}
                    component={ConditionOther}/>
            </Switch>
        </Widget>
    )
}

export default Single