import React from 'react'
import {Redirect, Route, Switch, useLocation} from 'react-router-dom'
import {Button, Nav} from 'reactstrap'

import {useBuildPath, useBuildRoutePath} from '../../../../hooks/project/path'

import {NavLink} from '../../../../components/Navigation'

import SamplePrep from './SamplePrep'
import Measurement from './Measurement'
import Software from './Software'
import Analysis from './Analysis'
import {useProjectBook} from "../../../../hooks/project/projectBook";
import {useIntl} from "react-intl";

const Method = ({match, history}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()
    const intl = useIntl()

    const projectBook = useProjectBook(id)
    const location = useLocation()

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
                    active={location.pathname.match(/\/method\/sample_prep\/*/g)}
                    onClick={() => handleTab(id, '/method/sample_prep')}
                    style={{borderRadius: 0}}
                >
                    {intl.formatMessage({id: 'sheet.sample_prep'})}
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(/\/method\/measurement\/*/g)}
                    onClick={() => handleTab(id, '/method/measurement')}
                    style={{borderRadius: 0}}
                >
                    {intl.formatMessage({id: 'sheet.data_analysis_method'})}
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(/\/method\/analysis\/*/g)}
                    onClick={() => handleTab(id, '/method/analysis')}
                    style={{borderRadius: 0}}
                >
                    {intl.formatMessage({id: 'sheet.data_analysis'})}
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(/\/method\/software\/*/g)}
                    onClick={() => handleTab(id, '/method/software')}
                    style={{borderRadius: 0}}
                >
                    {intl.formatMessage({id: 'sheet.software'})}
                </Button>
            </Nav>
            <Switch>
                <Route path={buildRoutePath('/method/sample_prep')} component={SamplePrep}/>
                <Route path={buildRoutePath('/method/measurement')} component={Measurement}/>
                <Route path={buildRoutePath('/method/analysis')} component={Analysis}/>
                <Route path={buildRoutePath('/method/software')} component={Software}/>
                <Redirect to={buildPath(id, '/method/sample_prep')}/>
            </Switch>
        </>
    )
}

export default Method