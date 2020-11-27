import React, {useMemo} from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import {Nav, NavItem} from 'reactstrap'

import * as getResourceService from '../../../../../services/projectBook/get/resource'

import {useProjectBook} from '../../../../../hooks/project/projectBook'
import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import {NavLink} from '../../../../../components/Navigation'
import Single from './Single'
import {useLocale} from "../../../../../hooks/i18n";

const ExperimentalDesign = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    const projectBook = useProjectBook(id)
    const locale = useLocale()
    const navItems = useMemo(() => {
        if (!projectBook || 0 === Object.keys(projectBook).length) return []
        return getResourceService.getExperimentalDesignList(projectBook).map(row => ({
            label: row.id[0],
            resourceId: row.id[0],
            resourceName: 'experimental_design'
        }))
    }, [projectBook, locale])

    const defaultPath = useMemo(() => {
        if (navItems.length < 1) return buildPath(id, '/experiment')
        const {resourceName, resourceId} = navItems[0]
        return buildPath(id, `/experiment/${resourceName}/${resourceId}`)
    }, [buildPath, id, navItems])

    if (!projectBook || 0 === Object.keys(projectBook).length) {
        return null
    }

    return (
        <>
            <div className="d-flex justify-content-start">
                <Nav tabs>
                    {navItems.map(({resourceName, resourceId, label}) => (
                        <NavItem key={resourceId}>
                            <NavLink to={buildPath(id, `/experiment/${resourceName}/${resourceId}`)}>{label}</NavLink>
                        </NavItem>
                    ))}
                </Nav>
            </div>
            <Switch>
                <Route path={buildRoutePath('/experiment/experimental_design/:experimentalDesignId')}
                       component={Single}/>
                <Redirect to={defaultPath}/>
            </Switch>
        </>
    )
}

export default ExperimentalDesign