import React from 'react'
import {Link, NavLink} from 'react-router-dom'
import {Badge, Button} from 'reactstrap'

import * as s from './Header.module.scss'

import {useBuildPath} from '../../../../hooks/project/path'
import {
    useHasDraft,
    useHasEditRole,
    useIsDraft,
    useIsEditing,
    useIsApplying,
    useIsPublic,
    useIsPublished,
} from '../../../../hooks/project/status'
import {useEditable} from '../../../../hooks/auth'
import {useIntl} from "react-intl";
import {useToken} from "../../../../hooks/project/preview";

const Item = ({children, ...props}) => (
    <NavLink
        {...props}
        className={s.navigation__item} activeClassName={s['navigation__item--active']}>
        {children}
    </NavLink>
)

export const BrowseHeader = ({match, location, history}) => {
    const {id} = match.params
    const isDraft = useIsDraft()
    const isPublic = useIsPublic()
    const token = useToken()
    const hasDraft = useHasDraft(id)
    const isPublished = useIsPublished(id)
    const buildPath = useBuildPath(location)

    const editable = useEditable(id)
    const editing = useIsEditing(id)
    const intl = useIntl()

    const projectUrl = isPublic ? `/project/${id}` : `/me/project/${id}`
    const editURL = editing ? `${projectUrl}/setting/edit` : `${projectUrl}/setting/start`
    const draftUrl = token ? `${projectUrl}/draft` : `${projectUrl}/draft?token=${token}`

    const handleTab = (id, url) => {
        history.push(buildPath(id, url))
    }

    return (
        <div className={s.container}>
            <div className="d-flex">
                <div className={s.title}>
                    <div className={s.title__text}>{id}</div>
                    {isDraft && null === token ? <Badge>{intl.formatMessage({id: 'project.detail.editing'})}</Badge> : null}
                </div>
                {false == isPublic && false === isDraft && hasDraft ? <Link to={draftUrl} className="btn btn-light mx-1">{intl.formatMessage({id: 'project.detail.button.preview'})}</Link> : null}
                {false == isPublic && isDraft && isPublished ? <Link to={projectUrl} className="btn btn-light mx-1">{intl.formatMessage({id: 'project.detail.button.public'})}</Link> : null}
                {false == isPublic && editable ? <Link to={editURL} className="btn btn-light mx-1">{intl.formatMessage({id: 'project.detail.button.edit'})}</Link> : null}
            </div>
            <div className={s.navigation}>
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(id + "/about"))}
                    onClick={() => handleTab(id, '/about')}
                >
                    {intl.formatMessage({id: 'project.detail.tab.overview'})}
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(id + "/experiment"))}
                    onClick={() => handleTab(id, '/experiment')}
                >
                    {intl.formatMessage({id: 'project.detail.tab.experiment'})}
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(id + "/sample"))}
                    onClick={() => handleTab(id, '/sample')}
                >
                    {intl.formatMessage({id: 'project.detail.tab.sample'})}
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(id + "/measurement"))}
                    onClick={() => handleTab(id, '/measurement/measurement')}
                >
                    {intl.formatMessage({id: 'project.detail.tab.measurement'})}
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(id + "/method"))}
                    onClick={() => handleTab(id, '/method')}
                >
                    {intl.formatMessage({id: 'project.detail.tab.method'})}
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(id + "/file"))}
                    onClick={() => handleTab(id, '/file')}
                >
                    {intl.formatMessage({id: 'project.detail.tab.file'})}
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(id + "/other"))}
                    onClick={() => handleTab(id, '/other')}
                >
                    {intl.formatMessage({id: 'project.detail.tab.misc'})}
                </Button>
            </div>
        </div>
    )
}

export const SettingHeader = ({match}) => {
    const {id} = match.params

    const isEditing = useIsEditing(id)
    const isApplying = useIsApplying(id)
    const hasEditRole = useHasEditRole(id);
    const hasDraft = useHasDraft(id)
    const intl = useIntl()

    return (
        <div className={s.container}>
            <div className="d-flex">
                <div className={s.title}>
                    <div className={s.title__text}>{id}</div>
                </div>
                <div>
                    {hasDraft ? <Link to={`/me/project/${id}/draft`} className="btn btn-light mx-1">{intl.formatMessage({id: 'project.detail.button.preview'})}</Link> : null}
                </div>
            </div>
            <div className={s.navigation}>
                {/* FIXME このへんの表示を権限通りにする */}
                {/* FIXME プロジェクトの状態に応じて（非公開か否か）表示をハンドリングする必要がある？ */}
                {hasEditRole && false === isEditing ? <Item to={`/me/project/${id}/setting/start`}>{intl.formatMessage({id: 'project.detail.button.edit'})}</Item> : null}
                {isEditing ? <Item to={`/me/project/${id}/setting/edit`}>{intl.formatMessage({id: 'project.detail.button.edit'})}</Item> : null}
                {/*{hasEditRole ? <Item to={`/me/project/${id}/setting/member`}>関係者</Item> : null}*/}
                {hasEditRole ? <Item to={`/me/project/${id}/setting/share`}>{intl.formatMessage({id: 'project.detail.editing.tab.url'})}</Item> : null}
                {isEditing ? <Item to={`/me/project/${id}/setting/apply`}>{intl.formatMessage({id: 'project.detail.editing.request.title'})}</Item> : null}
                {/*{hasEditRole ? <Item to={`/me/project/${id}/setting/delete`}>削除</Item> : null}*/}
            </div>
        </div>
    )
}
