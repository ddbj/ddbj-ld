import React, {useMemo} from 'react'
import {Redirect, Route, Switch, useLocation} from 'react-router-dom'
import {Button, Nav} from 'reactstrap'

import * as getResourceService from '../../../../services/projectBook/get/resource'

import {useProjectBook} from '../../../../hooks/project/projectBook'
import {useBuildPath, useBuildRoutePath} from '../../../../hooks/project/path'

import SupplementaryFile from './SupplementaryFile'
import RawDataFile from './RawDataFile'
import AnalyzedResultFile from './AnalyzedResultFile'

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
            supplementary_file: getResourceService.getSupplementaryFileList(projectBook).length > 0,
            raw_data_file: getResourceService.getRawDataFileList(projectBook).length > 0,
            analyzed_result_file: getResourceService.getAnalyzedResultFileList(projectBook).length > 0,
        }
    }, [projectBook])

    const defaultPath = useMemo(() => {
        const shownMenuNames = Object.entries(menu).filter(([, isShown]) => isShown).map(([name]) => name)
        if (shownMenuNames.length < 1) return buildPath(id)
        return buildPath(id, `/file/${shownMenuNames[0]}`)
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
                {menu.supplementary_file ?
                    <React.Fragment>
                        <Button
                            outline
                            color="primary"
                            active={location.pathname.endsWith("/supplementary_file")}
                            onClick={() => handleTab(id, '/file/supplementary_file')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'sheet.supplementary_file'})}
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
                            onClick={() => handleTab(id, '/file/raw_data_file')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'sheet.raw_data_file'})}
                        </Button>
                        {'　'}
                    </React.Fragment>
                    : null
                }
                {menu.analyzed_result_file ?
                    <React.Fragment>
                        <Button
                            outline
                            color="primary"
                            active={location.pathname.endsWith("/analyzed_result_file")}
                            onClick={() => handleTab(id, '/file/analyzed_result_file')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'sheet.analyzed_result_file'})}
                        </Button>
                    </React.Fragment>
                    : null
                }
            </Nav>
            <Switch>
                <Route path={buildRoutePath('/file/supplementary_file')} component={SupplementaryFile}/>
                <Route path={buildRoutePath('/file/raw_data_file')} component={RawDataFile}/>
                <Route path={buildRoutePath('/file/analyzed_result_file')} component={AnalyzedResultFile}/>
                <Redirect to={defaultPath}/>
            </Switch>
        </>
    )
}

export default File