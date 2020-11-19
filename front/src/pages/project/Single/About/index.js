import React, {useState} from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'
import {Button, Nav, NavLink as RSLink} from 'reactstrap'

import {useBuildPath, useBuildRoutePath} from '../../../../hooks/project/path'

import {NavLink} from '../../../../components/Navigation'

import Project from './Project'
import Person from './Person'
import Reference from './Reference'
import {useIsLoading, useProjectBook} from "../../../../hooks/project/projectBook";
import {useIntl} from "react-intl";
import Loading from "../../components/Loading";
import {useLocation} from 'react-router-dom'
import {connect} from "react-redux";

const About = ({match, history, isLoading}) => {
    const {id} = match.params
    const buildPath = useBuildPath()
    const buildRoutePath = useBuildRoutePath()
    const intl = useIntl()

    const projectBook = useProjectBook(id)
    // const isLoading = useIsLoading(projectBook)
    const location = useLocation()

    const handleTab = (id, url) => {
        history.push(buildPath(id, url))
    }

    // if(isLoading && !projectBook) {
    //     return <Loading />
    // }

    if (!projectBook || 0 === Object.keys(projectBook).length) {
        return <div>{intl.formatMessage({id: 'project.detail.not.found.metadata'})}</div>
    }

    return (
        <>
            <Nav pills className="mb-4">
                <Button
                    outline
                    color="primary"
                    active={location.pathname.endsWith("/about")}
                    onClick={() => handleTab(id, '/about')}
                    style={{borderRadius: 0}}
                >
                    {intl.formatMessage({id: 'project.detail.overview.tab.overview'})}
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.endsWith("/person")}
                    onClick={() => handleTab(id, '/about/person')}
                    style={{borderRadius: 0}}
                >
                    {intl.formatMessage({id: 'project.detail.overview.tab.person'})}
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.endsWith("/reference")}
                    onClick={() => handleTab(id, '/about/reference')}
                    style={{borderRadius: 0}}
                >
                    {intl.formatMessage({id: 'project.detail.overview.tab.reference'})}
                </Button>
            </Nav>
            <Switch>
                <Route path={buildRoutePath("/about")} exact component={Project}/>
                <Route path={buildRoutePath("/about/person")} component={Person}/>
                <Route path={buildRoutePath("/about/reference")} component={Reference}/>
                <Redirect to={buildPath(id, '/about')}/>
            </Switch>
        </>
    )
}

// FIXME 本当はReduxに接続したくないが、リアルタイムにstateの変更を反映する方法が見つかり次第
// function mapStateToProps(state) {
//     return {
//         isLoading: state.projectBook.isLoading,
//     };
// }
//
// export default connect(mapStateToProps)(About);

export default About;