import React from 'react'
import {Redirect, Route, Switch, useLocation} from 'react-router-dom'
import {Button, Nav} from 'reactstrap'

import Metadata from './Metadata'
import File from './File'
import Cancel from './Cancel'
import {useIsCancelable} from '../../../../hooks/project/status'
import {useProjectBook} from "../../../../hooks/project/projectBook";
import {useIntl} from "react-intl";
import {useBuildPath} from "../../../../hooks/project/path";

const Edit = ({match, history}) => {
    const {id} = match.params

    const isCancelable = useIsCancelable(id)
    const projectBook = useProjectBook(id)
    const intl = useIntl()
    const buildPath = useBuildPath()
    const location = useLocation()

    const handleTab = (id, url) => {
        history.push(buildPath(id, url))
    }

    return (
        <>
            <Nav pills className="mb-4">
                <Button
                    outline
                    color="primary"
                    active={location.pathname.endsWith("/setting/edit")}
                    onClick={() => handleTab(id, '/setting/edit')}
                    style={{borderRadius: 0}}
                >
                    {intl.formatMessage({id: 'project.detail.editing.tab.editing'})}
                </Button>
                {'　'}
                {projectBook
                    ?
                    <>
                        <Button
                            outline
                            color="primary"
                            active={location.pathname.endsWith("/setting/edit/analysed_dataset")}
                            onClick={() => handleTab(id, '/setting/edit/analysed_dataset')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'project.detail.editing.tab.analysed'})}
                        </Button>
                        {'　'}
                        <Button
                            outline
                            color="primary"
                            active={location.pathname.endsWith("/setting/edit/raw_data_file")}
                            onClick={() => handleTab(id, '/setting/edit/raw_data_file')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'project.detail.editing.tab.raw'})}
                        </Button>
                        {'　'}
                        <Button
                            outline
                            color="primary"
                            active={location.pathname.endsWith("/setting/edit/suppl_file")}
                            onClick={() => handleTab(id, '/setting/edit/suppl_file')}
                            style={{borderRadius: 0}}
                        >
                            {intl.formatMessage({id: 'project.detail.editing.tab.suppl'})}
                        </Button>
                    </>
                    : null
                }
                {/*{'　'}*/}
                {/*{isCancelable ? <NavLink to={`/me/project/${id}/setting/edit/cancel`}>{`/me/project/${id}/setting/edit/suppl_file`}>{intl.formatMessage({id: 'project.detail.editing.tab.editing.cancel'})}</NavLink> : null}*/}
                {'　'}
            </Nav>
            <Switch>
                <Route path="/me/project/:id/setting/edit" exact component={Metadata}/>
                {projectBook
                    ?
                    <>
                        <Route path="/me/project/:id/setting/edit/analysed_dataset"
                               render={props => <File match={props.match} fileType={"analysis"}/>}/>
                        <Route path="/me/project/:id/setting/edit/raw_data_file"
                               render={props => <File match={props.match} fileType={"raw"}/>}/>
                        <Route path="/me/project/:id/setting/edit/suppl_file"
                               render={props => <File match={props.match} fileType={"project"}/>}/>
                    </>
                    : null
                }
                {/*{isCancelable ? <Route path="/me/project/:id/setting/edit/cancel" exact component={Cancel}/> : null}*/}
                <Redirect to={`/me/project/${id}/setting/edit`}/>
            </Switch>
        </>
    )
}

export default Edit