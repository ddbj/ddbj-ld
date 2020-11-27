import React from 'react'
import {Redirect, Route, Switch, useLocation} from 'react-router-dom'
import {Button, Nav} from 'reactstrap'

import {useBuildPath, useBuildRoutePath} from '../../../../hooks/project/path'

import ExperimentContent from './Experiment'
import ExperimentalDesign from './ExperimentalDesign'
import {useProjectBook} from "../../../../hooks/project/projectBook";
import {useIntl} from "react-intl";

const Experiment = ({match, history}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()
    const intl = useIntl()
    const location = useLocation()

    const projectBook = useProjectBook(id)
    if (!projectBook || 0 === Object.keys(projectBook).length) return <div>{intl.formatMessage({id: 'project.detail.not.found.metadata'})}</div>

    const handleTab = (id, url) => {
        history.push(buildPath(id, url))
    }

    return (
        <>
            <Nav pills className="mb-4">
                <Button
                    outline
                    color="primary"
                    active={location.pathname.endsWith("experiment")}
                    onClick={() => handleTab(id, '/experiment/experiment')}
                    style={{borderRadius: 0}}
                >
                    {intl.formatMessage({id: 'project.detail.tab.experiment'})}
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(id + "/experiment/experimental_design"))}
                    onClick={() => handleTab(id, '/experiment/experimental_design')}
                    style={{borderRadius: 0}}
                >
                    {intl.formatMessage({id: 'sheet.experiment.experimental_design'})}
                </Button>
            </Nav>
            <Switch>
                <Route path={buildRoutePath('/experiment/experiment')} component={ExperimentContent}/>
                <Route path={buildRoutePath('/experiment/experimental_design')} component={ExperimentalDesign}/>
                <Redirect to={buildPath(id, '/experiment/experiment')}/>
            </Switch>
        </>
    )
}

export default Experiment