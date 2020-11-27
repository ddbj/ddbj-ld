import React, {useMemo} from 'react'
import {Redirect, Route, Switch, useLocation} from 'react-router-dom'
import {Button, Nav} from 'reactstrap'

import * as getResourceService from '../../../../services/projectBook/get/resource'

import {useProjectBook} from '../../../../hooks/project/projectBook'
import {useBuildPath, useBuildRoutePath} from '../../../../hooks/project/path'

import Treatment from './Treatment'
import LightCondition from './LightCondition'
import Term from './Term'
import Organisation from './Organisation'
import DataProcessingMethodType from './DataProcessingMethodType'

import {NavLink} from '../../../../components/Navigation'
import {useIntl} from "react-intl";

const File = ({match, history}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    const projectBook = useProjectBook(id)
    const menu = useMemo(() => {
        if (!projectBook || 0 === Object.keys(projectBook).length) return {}
        return {
            treatment: getResourceService.getTreatmentList(projectBook).length > 0,
            light_condition: getResourceService.getLightConditionList(projectBook).length > 0,
            term: getResourceService.getTermList(projectBook).length > 0,
            organisation: getResourceService.getOrganisationList(projectBook).length > 0,
            data_processing_method_type: getResourceService.getDataProcessingMethodTypeList(projectBook).length > 0,
        }
    }, [projectBook])

    const defaultPath = useMemo(() => {
        const shownMenuNames = Object.entries(menu).filter(([, isShown]) => isShown).map(([name]) => name)
        if (shownMenuNames.length < 1) return buildPath(id)
        return buildPath(id, `/other/${shownMenuNames[0]}`)
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
                {menu.treatment ?
                    <React.Fragment>
                        <Button
                            outline
                            color="primary"
                            active={location.pathname.endsWith("/treatment")}
                            onClick={() => handleTab(id, '/other/treatment')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'sheet.treatment'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
                {menu.light_condition ?
                    <React.Fragment>
                        <Button
                            outline
                            color="primary"
                            active={location.pathname.endsWith("/light_condition")}
                            onClick={() => handleTab(id, '/other/light_condition')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'sheet.light_condition'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
                {menu.data_processing_method_type ?
                    <React.Fragment>
                        <Button
                            outline
                            color="primary"
                            active={location.pathname.endsWith("/data_processing_method_type")}
                            onClick={() => handleTab(id, '/other/data_processing_method_type')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'sheet.data_processing_method_type'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
                {menu.term ?
                    <React.Fragment>
                        <Button
                            outline
                            color="primary"
                            active={location.pathname.endsWith("/term")}
                            onClick={() => handleTab(id, '/other/term')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'sheet.term'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
                {menu.organisation ?
                    <React.Fragment>
                        <Button
                            outline
                            color="primary"
                            active={location.pathname.endsWith("/organisation")}
                            onClick={() => handleTab(id, '/other/organisation')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'sheet.organisation'})}
                        </Button>
                    </React.Fragment>
                    : null
                }
            </Nav>
            <Switch>
                {menu.treatment ? <Route path={buildRoutePath('/other/treatment')} component={Treatment}/> : null}
                {menu.light_condition ?
                    <Route path={buildRoutePath('/other/light_condition')} component={LightCondition}/> : null}
                {menu.data_processing_method_type ? <Route path={buildRoutePath('/other/data_processing_method_type')}
                                                           component={DataProcessingMethodType}/> : null}
                {menu.term ? <Route path={buildRoutePath('/other/term')} component={Term}/> : null}
                {menu.organisation ?
                    <Route path={buildRoutePath('/other/organisation')} component={Organisation}/> : null}
                <Redirect to={defaultPath}/>
            </Switch>
        </>
    )
}

export default File