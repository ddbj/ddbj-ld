import React from 'react'
import {Route, Switch} from 'react-router-dom'

import {
    useIsDeleted,
    useIsEditing, useIsPublished,
} from '../../../hooks/project/status'
import {useEditable} from '../../../hooks/auth'

import {SettingHeader as Header} from '../components/Header'
import Forbidden from '../components/Forbidden'

import Start from './Start'
import Edit from './Edit'
import Member from './Member'
import Share from './Share'
import Apply from './Apply'
import Delete from './Delete'
import Deleted from './Deleted'

const Setting = ({match}) => {
    const {id} = match.params

    const isDeleted = useIsDeleted(id)

    const editable = useEditable(id)
    const isEditing = useIsEditing(id)

    if (!editable) return <Forbidden/>

    // FIXME isStartableとisShowApplyMenu、isDeletedの条件、Editingも追加する
    // FIXME Editingとか細かいアクセス制御は検索APIまでできたらのほうが効率よさげ

    return (
        <>
            <Route path="/me/project/:id/setting" component={Header}/>
            <div className="pt-4">
                <Switch>
                    {editable && false === isEditing ? <Route path="/me/project/:id/setting/start" component={Start}/> : null}
                    {editable ? <Route path="/me/project/:id/setting/edit" component={Edit}/> : null}
                    {editable ? <Route path="/me/project/:id/setting/member" component={Member}/> : null}
                    {editable ? <Route path="/me/project/:id/setting/share" component={Share}/> : null}
                    {editable ? <Route path="/me/project/:id/setting/apply" component={Apply}/> : null}
                    {editable ? <Route path="/me/project/:id/setting/delete" component={Delete}/> : null}
                    {isDeleted ? <Route path="/me/project/:id/setting/deleted" component={Deleted}/> : null}
                </Switch>
            </div>
        </>
    )
}

export default Setting