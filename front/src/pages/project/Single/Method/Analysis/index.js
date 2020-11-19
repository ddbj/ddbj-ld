import React, {useMemo} from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import {Nav, NavItem} from 'reactstrap'

import * as getResourceService from '../../../../../services/projectBook/get/resource'

import {useProjectBook} from '../../../../../hooks/project/projectBook'
import {useBuildPath, useBuildRoutePath} from '../../../../../hooks/project/path'

import {NavLink} from '../../../../../components/Navigation'

import AnnotationMethod from './AnnotationMethod'
import DataAnalysis from './DataAnalysis'
import DataPreprocessing from './DataPreprocessing'
import DataProcessingMethod from './DataProcessingMethod'
import {useLocale} from "../../../../../hooks/i18n";
import {useIntl} from "react-intl";

// TODO: 共通化
const buildParseRow = type => {
    const parseRow = row => ({
        resourceId: row.id[0],
        label: row.name[0],
        type,
    })
    return parseRow
}

const Analysis = ({match}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()

    const projectBook = useProjectBook(id)
    const intl = useIntl()
    const locale = useLocale()
    const navItems = useMemo(() => {
        if (!projectBook) return []

        const navItems = [
            ...(getResourceService.getDataAnalysisList(projectBook).length > 0 ? [
                {label: intl.formatMessage({id: 'sheet.data_analysis'}), type: 'data_analysis'}
            ] : []),
            ...getResourceService.getDataPreprocessingList(projectBook).map(buildParseRow('data_preprocessing')),
            ...(getResourceService.getDataProcessingMethodList(projectBook).length > 0 ? [
                {label: intl.formatMessage({id: 'sheet.data_processing_method'}), type: 'data_processing_method'},
            ] : []),
            ...(getResourceService.getAnnotationMethodList(projectBook).length > 0 ? [
                {label: intl.formatMessage({id: 'sheet.annotation_method'}), type: 'annotation_method'},
            ] : [locale])
        ]

        if (navItems.lenhth < 7) return navItems

        return [
            ...(getResourceService.getDataAnalysisList(projectBook).length > 0 ? [
                {label: intl.formatMessage({id: 'sheet.data_analysis'}), type: 'data_analysis'}
            ] : []),
            ...(getResourceService.getDataPreprocessingList(projectBook).length > 0 ? [
                {label: intl.formatMessage({id: 'sheet.data_preprocessing'}), type: 'data_preprocessing'},
            ] : []),
            ...(getResourceService.getDataProcessingMethodList(projectBook).length > 0 ? [
                {label: intl.formatMessage({id: 'sheet.data_processing_method'}), type: 'data_processing_method'},
            ] : []),
            ...(getResourceService.getAnnotationMethodList(projectBook).length > 0 ? [
                {label: intl.formatMessage({id: 'sheet.annotation_method'}), type: 'annotation_method'},
            ] : [])
        ]
    }, [projectBook, locale])

    const defaultPath = useMemo(() => {
        if (navItems.length < 1) return buildPath(id, '/method')
        const {type, resourceId} = navItems[0]
        return buildPath(id, resourceId ? `/method/analysis/${type}/${resourceId}` : `/method/analysis/${type}`)
    }, [buildPath, id, navItems])

    if (!projectBook || navItems.length === 0) {
        return null
    }

    return (
        <>
            <div className="d-flex justify-content-start">
                <Nav tabs>
                    {navItems.map(({label, resourceId, type}, index) =>
                        <NavItem key={index}>
                            <NavLink
                                to={buildPath(id, resourceId ? `/method/analysis/${type}/${resourceId}` : `/method/analysis/${type}`)}>
                                {label}
                            </NavLink>
                        </NavItem>
                    )}
                </Nav>
            </div>
            <Switch>
                <Route path={buildRoutePath('/method/analysis/annotation_method')} component={AnnotationMethod}/>
                <Route path={buildRoutePath('/method/analysis/data_analysis')} component={DataAnalysis}/>
                <Route path={buildRoutePath('/method/analysis/data_preprocessing')} component={DataPreprocessing}/>
                <Route path={buildRoutePath('/method/analysis/data_processing_method')}
                       component={DataProcessingMethod}/>
                <Redirect to={defaultPath}/>
            </Switch>
        </>
    )
}

export default Analysis
