import React, {useMemo} from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import {Nav, NavItem} from 'reactstrap'

import * as getResourceService from '../../../../../services/projectBook/get/resource'

import {useProjectBook} from '../../../../../hooks/project/projectBook'
import {useBuildDynamicNavItemWithRows, useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import {NavLink} from '../../../../../components/Navigation'

import SamplePrepContent from './SamplePrep'
import SamplePrepAnimal from './SamplePrepAnimal'
import SamplePrepPlant from './SamplePrepPlant'
import SamplePrepChemical from './SamplePrepChemical'
import SamplePrepOther from './SamplePrepOther'
import SamplePrepClinical from './SamplePrepClinical'
import SamplePrepBacteria from './SamplePrepBacteria'
import SamplePrepEnvironment from './SamplePrepEnvironment'
import SamplePrepFood from './SamplePrepFood'
import SamplePrepControl from './SamplePrepControl'
import {useIntl} from "react-intl";
import {useLocale} from "../../../../../hooks/i18n";

const BASE_PATH = '/method/sample_prep'

const SamplePrep = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    const buildDynamicNavItemWithRows = useBuildDynamicNavItemWithRows(id, BASE_PATH)

    const projectBook = useProjectBook(id)
    const intl = useIntl()
    const locale = useLocale()
    const navItems = useMemo(() => {
        if (!projectBook) return []
        if (!projectBook || 0 === Object.keys(projectBook).length) return []
        return [
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.sample_prep_animal'}), 'sample_prep_animal', getResourceService.getSamplePrepAnimalList(projectBook)),
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.sample_prep_plant'}), 'sample_prep_plant', getResourceService.getSamplePrepPlantList(projectBook)),
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.sample_prep_chemical'}), 'sample_prep_chemical', getResourceService.getSamplePrepChemicalList(projectBook)),
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.sample_prep_other'}), 'sample_prep_other', getResourceService.getSamplePrepOtherList(projectBook)),
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.sample_prep_clinical'}), 'sample_prep_clinical', getResourceService.getSamplePrepClinicalList(projectBook)),
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.sample_prep_bacteria'}), 'sample_prep_bacteria', getResourceService.getSamplePrepBacteriaList(projectBook)),
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.sample_prep_environment'}), 'sample_prep_environment', getResourceService.getSamplePrepEnvironmentList(projectBook)),
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.sample_prep_food'}), 'sample_prep_food', getResourceService.getSamplePrepFoodList(projectBook)),
            buildDynamicNavItemWithRows(intl.formatMessage({id: 'sheet.sample_prep_control'}), 'sample_prep_control', getResourceService.getSamplePrepControlList(projectBook))
        ].filter(Boolean)
    }, [buildDynamicNavItemWithRows, projectBook, locale])

    const defaultPath = useMemo(() => {
        if (navItems.length < 1) return buildPath(id, '/method')
        return navItems[0].path
    }, [buildPath, id, navItems])

    if (!projectBook || 0 === Object.keys(projectBook).length || 0 === navItems.length) return null

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
                <Route path={buildRoutePath('/method/sample_prep/sample_prep')} component={SamplePrepContent}/>
                <Route path={buildRoutePath('/method/sample_prep/sample_prep_animal')} component={SamplePrepAnimal}/>
                <Route path={buildRoutePath('/method/sample_prep/sample_prep_plant')} component={SamplePrepPlant}/>
                <Route path={buildRoutePath('/method/sample_prep/sample_prep_chemical')}
                       component={SamplePrepChemical}/>
                <Route path={buildRoutePath('/method/sample_prep/sample_prep_other')} component={SamplePrepOther}/>
                <Route path={buildRoutePath('/method/sample_prep/sample_prep_clinical')}
                       component={SamplePrepClinical}/>
                <Route path={buildRoutePath('/method/sample_prep/sample_prep_bacteria')}
                       component={SamplePrepBacteria}/>
                <Route path={buildRoutePath('/method/sample_prep/sample_prep_environment')}
                       component={SamplePrepEnvironment}/>
                <Route path={buildRoutePath('/method/sample_prep/sample_prep_food')} component={SamplePrepFood}/>
                <Route path={buildRoutePath('/method/sample_prep/sample_prep_control')} component={SamplePrepControl}/>
                <Redirect to={defaultPath}/>
            </Switch>
        </>
    )
}

export default SamplePrep
