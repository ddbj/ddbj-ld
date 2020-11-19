import React from 'react'
import {ButtonGroup} from 'reactstrap'
import {NavLink} from 'react-router-dom'

const Navigation = ({id, name}) => (
    <ButtonGroup className="w-100">
        <NavLink to={`/me/project/${id}/setting/edit/${name}/select`} className="btn btn-default w-50">ファイルの選択</NavLink>
        <NavLink to={`/me/project/${id}/setting/edit/${name}/link`} className="btn btn-default w-50">紐付け</NavLink>
    </ButtonGroup>
)

export default Navigation