import React from 'react'

import {NavLink as RRNavLink} from 'react-router-dom'
import {NavLink as RSLink} from 'reactstrap'

export const NavLink = ({children, ...props}) => (
    <RSLink tag={RRNavLink} activeClassName="active" {...props}>{children}</RSLink>
)