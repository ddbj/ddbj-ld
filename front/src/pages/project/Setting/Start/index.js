import React from 'react'
import {useDispatch} from 'react-redux'
import {Button} from 'reactstrap'

import {beginEditProject} from '../../../../actions/project'
import {useIntl} from "react-intl";

const Start = ({match, history}) => {
    const {id} = match.params
    const dispatch = useDispatch()
    const intl = useIntl()

    // FIXME アカウントのロールにより制御する仕組み

    const onEdit = () => {
        dispatch(beginEditProject(id, history))
    }

    return (
        <div className="text-center py-4">
            <>
                <h3 className="page-title">{intl.formatMessage({id: 'project.detail.editing.begin'})}</h3>
                <Button
                    onClick={onEdit}
                    color="primary"
                >
                    {intl.formatMessage({id: 'project.detail.editing.begin.button'})}
                </Button>
            </>
        </div>
    )
}

export default Start