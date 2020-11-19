import React, {useMemo} from 'react'
import {Redirect, Route, Switch, useLocation} from 'react-router-dom'
import {Button, Nav} from 'reactstrap'

import {useIsApplying, useIsShowDispublishApplyMenu, useIsShowPublishApplyMenu} from '../../../../hooks/project/status'

import {NavLink} from '../../../../components/Navigation'

import Publish from './Publish'
import Dispublish from './Dispublish'
import {useIntl} from "react-intl";
import {useBuildPath} from "../../../../hooks/project/path";

const Apply = ({match, history}) => {
    const {id} = match.params

    const isApplying = useIsApplying(id)

    const defaultPath = useMemo(() => {
        return `/me/project/${id}/setting/apply/publish`
    }, [isApplying, id])

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
                    active={location.pathname.endsWith("/setting/apply/publish")}
                    onClick={() => handleTab(id, '/setting/apply/publish')}
                    style={{borderRadius: 0}}
                >
                    {intl.formatMessage({id: 'project.detail.editing.request.public.title'})}
                </Button>
                {'　'}
                {/*{isShowDispublishApplyMenu ?*/}
                {/*    <NavLink to={`/me/project/${id}/setting/apply/dispublish`}>非公開</NavLink> : null}*/}
            </Nav>
            <Switch>
                <Route path="/me/project/:id/setting/apply/publish" component={Publish}/>
                {/*{isShowDispublishApplyMenu ?*/}
                {/*    <Route path="/me/project/:id/setting/apply/dispublish" component={Dispublish}/> : null}*/}
                <Redirect to={defaultPath}/>
            </Switch>
        </>
    )
}

export default Apply