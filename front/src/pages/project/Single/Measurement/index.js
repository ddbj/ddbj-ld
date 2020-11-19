import React, {useMemo} from 'react'
import {Redirect, Route, Switch, useLocation} from 'react-router-dom'
import {Button, Nav} from 'reactstrap'

import * as getResourceService from '../../../../services/projectBook/get/resource'

import {useProjectBook} from '../../../../hooks/project/projectBook'
import {useBuildPath, useBuildRoutePath} from '../../../../hooks/project/path'

import {NavLink} from '../../../../components/Navigation'

import MeasurementContent from './Measurement'
import RawDataFile from './RawDataFile'
import AnalyzedRawFile from './AnalyzedRawFile'
import {useIntl} from "react-intl";

const Measurement = ({match, history}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    const projectBook = useProjectBook(id)

    const menu = useMemo(() => {
        if (!projectBook || 0 === Object.keys(projectBook).length) return {}
        return {
            measurement: getResourceService.getMeasurementList(projectBook).length > 0,
            raw_data_file: getResourceService.getRawDataFileList(projectBook).length > 0,
            analyzed_raw_file: getResourceService.getAnalyzedRawFileList(projectBook).length > 0,
        }
    }, [projectBook])

    const defaultPath = useMemo(() => {
        const shownMenuNames = Object.entries(menu).filter(([, isShown]) => isShown).map(([name]) => name)
        if (shownMenuNames.length < 1) return buildPath(id)
        return buildPath(id, `/measurement/${shownMenuNames[0]}`)
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
                {menu.measurement ?
                    <React.Fragment>
                        <Button
                            outline
                            color="primary"
                            active={location.pathname.endsWith("/measurement")}
                            onClick={() => handleTab(id, '/measurement/measurement')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'sheet.measurement'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
                {menu.raw_data_file ?
                    <React.Fragment>
                        <Button
                            outline
                            color="primary"
                            active={location.pathname.endsWith("/raw_data_file")}
                            onClick={() => handleTab(id, '/measurement/raw_data_file')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'sheet.raw_data_file'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
                {menu.analyzed_raw_file ?
                    <React.Fragment>
                        <Button
                            outline
                            color="primary"
                            active={location.pathname.endsWith("/analyzed_raw_file")}
                            onClick={() => handleTab(id, '/measurement/analyzed_raw_file')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'sheet.analyzed_raw_file'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
            </Nav>
            <Switch>
                <Route path={buildRoutePath('/measurement/measurement')} component={MeasurementContent}/>
                <Route path={buildRoutePath('/measurement/raw_data_file')} component={RawDataFile}/>
                <Route path={buildRoutePath('/measurement/analyzed_raw_file')} component={AnalyzedRawFile}/>
                <Redirect to={defaultPath}/>
            </Switch>
        </>
    )
}

export default Measurement