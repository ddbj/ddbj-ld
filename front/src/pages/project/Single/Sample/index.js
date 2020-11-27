import React, {useMemo} from 'react'
import {Redirect, Route, Switch, useLocation} from 'react-router-dom'
import {Button, Nav} from 'reactstrap'

import * as getResourceService from '../../../../services/projectBook/get/resource'

import {useProjectBook} from '../../../../hooks/project/projectBook'
import {useBuildPath, useBuildRoutePath} from '../../../../hooks/project/path'

import {NavLink} from '../../../../components/Navigation'

import SampleContent from './Sample'
import SampleAnimal from './SampleAnimal'
import SamplePlant from './SamplePlant'
import SampleChemical from './SampleChemical'
import SampleClinical from './SampleClinical'
import SampleBacteria from './SampleBacteria'
import SampleEnvironment from './SampleEnvironment'
import SampleFood from './SampleFood'
import SampleControl from './SampleControl'
import SampleOther from './SampleOther'
import {useIntl} from "react-intl";

const Sample = ({match, history}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    const projectBook = useProjectBook(id)

    const menu = useMemo(() => {
        if (!projectBook || 0 === Object.keys(projectBook).length) return {}

        return {
            sample_animal: getResourceService.getSampleAnimalList(projectBook).length > 0,
            sample_plant: getResourceService.getSamplePlantList(projectBook).length > 0,
            sample_chemical: getResourceService.getSampleChemicalList(projectBook).length > 0,
            sample_clinical: getResourceService.getSampleClinicalList(projectBook).length > 0,
            sample_bacteria: getResourceService.getSampleBacteriaList(projectBook).length > 0,
            sample_environment: getResourceService.getSampleEnvironmentList(projectBook).length > 0,
            sample_food: getResourceService.getSampleFoodList(projectBook).length > 0,
            sample_control: getResourceService.getSampleControlList(projectBook).length > 0,
            sample_other: getResourceService.getSampleOtherList(projectBook).length > 0,
        }
    }, [projectBook])

    const defaultPath = useMemo(() => {
        const shownMenuNames = Object.entries(menu).filter(([, isShown]) => isShown).map(([name]) => name)
        if (shownMenuNames.length < 1) return buildPath(id)
        return buildPath(id, `/sample/${shownMenuNames[0]}`)
    }, [buildPath, id, menu])

    const intl = useIntl()
    const location = useLocation()

    if (!projectBook || 0 === Object.keys(projectBook).length) return <div>{intl.formatMessage({id: 'project.detail.not.found.metadata'})}</div>

    const handleTab = (id, url) => {
        history.push(buildPath(id, url))
    }

    return (
        <>
            <Nav pills className="mb-4">
                {menu.sample_animal ?
                    <React.Fragment>
                        <Button
                            outline
                            color="warning"
                            active={location.pathname.endsWith("sample_animal")}
                            onClick={() => handleTab(id, '/sample/sample_animal')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'project.detail.sample.tab.animal'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
                {menu.sample_plant ?
                    <React.Fragment>
                        <Button
                            outline
                            color="warning"
                            active={location.pathname.endsWith("sample_plant")}
                            onClick={() => handleTab(id, '/sample/sample_plant')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'project.detail.sample.tab.plant'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
                {menu.sample_chemical ?
                    <React.Fragment>
                        <Button
                            outline
                            color="warning"
                            active={location.pathname.endsWith("sample_chemical")}
                            onClick={() => handleTab(id, '/sample/sample_chemical')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'project.detail.sample.tab.chemical'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
                {menu.sample_clinical ?
                    <React.Fragment>
                        <Button
                            outline
                            color="warning"
                            active={location.pathname.endsWith("sample_clinical")}
                            onClick={() => handleTab(id, '/sample/sample_clinical')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'project.detail.sample.tab.clinical'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
                {menu.sample_bacteria ?
                    <React.Fragment>
                        <Button
                            outline
                            color="warning"
                            active={location.pathname.endsWith("sample_bacteria")}
                            onClick={() => handleTab(id, '/sample/sample_bacteria')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'project.detail.sample.tab.bacteria'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
                {menu.sample_environment ?
                    <React.Fragment>
                        <Button
                            outline
                            color="warning"
                            active={location.pathname.endsWith("sample_environment")}
                            onClick={() => handleTab(id, '/sample/sample_environment')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'project.detail.sample.tab.environment'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
                {menu.sample_food ?
                    <React.Fragment>
                        <Button
                            outline
                            color="warning"
                            active={location.pathname.endsWith("sample_food")}
                            onClick={() => handleTab(id, '/sample/sample_food')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'project.detail.sample.tab.food'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
                {menu.sample_control ?
                    <React.Fragment>
                        <Button
                            outline
                            color="warning"
                            active={location.pathname.endsWith("sample_control")}
                            onClick={() => handleTab(id, '/sample/sample_control')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'project.detail.sample.tab.control'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
                {menu.sample_other ?
                    <React.Fragment>
                        <Button
                            outline
                            color="warning"
                            active={location.pathname.endsWith("sample_other")}
                            onClick={() => handleTab(id, '/sample/sample_other')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'project.detail.sample.tab._other'})}
                        </Button>
                    </React.Fragment>
                    : null
                }
            </Nav>
            <Switch>
                <Route path={buildRoutePath('/sample/sample')} component={SampleContent}/>
                {menu.sample_animal ?
                    <Route path={buildRoutePath('/sample/sample_animal')} component={SampleAnimal}/> : null}
                {menu.sample_plant ?
                    <Route path={buildRoutePath('/sample/sample_plant')} component={SamplePlant}/> : null}
                {menu.sample_chemical ?
                    <Route path={buildRoutePath('/sample/sample_chemical')} component={SampleChemical}/> : null}
                {menu.sample_clinical ?
                    <Route path={buildRoutePath('/sample/sample_clinical')} component={SampleClinical}/> : null}
                {menu.sample_bacteria ?
                    <Route path={buildRoutePath('/sample/sample_bacteria')} component={SampleBacteria}/> : null}
                {menu.sample_environment ?
                    <Route path={buildRoutePath('/sample/sample_environment')} component={SampleEnvironment}/> : null}
                {menu.sample_food ? <Route path={buildRoutePath('/sample/sample_food')} component={SampleFood}/> : null}
                {menu.sample_control ?
                    <Route path={buildRoutePath('/sample/sample_control')} component={SampleControl}/> : null}
                {menu.sample_other ?
                    <Route path={buildRoutePath('/sample/sample_other')} component={SampleOther}/> : null}
                <Redirect to={defaultPath}/>
            </Switch>
        </>
    )
}

export default Sample