import React, {useEffect} from 'react'
import {Redirect, Route, Switch} from 'react-router-dom'

import {useProjectBook} from '../../../hooks/project/projectBook'
import {useBuildPath, useBuildRoutePath} from '../../../hooks/project/path'

import {BrowseHeader as Header} from '../components/Header'

import NotFound from '../components/NotFound'

import About from './About'
import Experiment from './Experiment'
import Sample from './Sample'
import Method from './Method'
import Measurement from './Measurement'
import File from './File'
import Other from './Other'
import Resource from './Resource'
import {connect} from "react-redux";
import Loading from "../components/Loading";
import {useToken} from "../../../hooks/project/preview";

const Single = ({match, history, isLoading}) => {
    const {id} = match.params
    const buildRoutePath = useBuildRoutePath()
    const buildPath = useBuildPath()

    const projectBook = useProjectBook(id, history)
    const token = useToken()

    if(isLoading) {
        return <Loading />
    }

    // FIXME マイページからだとこれが表示されるが公開プロジェクトだと/404に遷移するので将来的に統一
    //  - Layoutにも埋め込めるようにする
    if (!projectBook) {
        return <NotFound id={id}/>
    }

    return (
        <>
            <Route path={buildRoutePath()} component={Header}/>
            <div className="pt-4">
                <Switch>
                    <Route path={buildRoutePath('/about')} component={About}/>
                    <Route path={buildRoutePath('/experiment')} component={Experiment}/>
                    <Route path={buildRoutePath('/sample')} component={Sample}/>
                    <Route path={buildRoutePath('/measurement')} component={Measurement}/>
                    <Route path={buildRoutePath('/method')} component={Method}/>
                    <Route path={buildRoutePath('/file')} component={File}/>
                    <Route path={buildRoutePath('/other')} component={Other}/>
                    <Route path={buildRoutePath('/resource')} component={Resource}/>
                    <Redirect path="*" to={buildPath(id, '/about')}/>
                </Switch>
            </div>
        </>
    )
}

// FIXME 本当はReduxに接続したくないが、リアルタイムにstateの変更を反映する方法が見つかり次第
const mapStateToProps = (state) => {
    return {
        isLoading: state.projectBook.isLoading,
    };
}

export default connect(mapStateToProps)(Single);
