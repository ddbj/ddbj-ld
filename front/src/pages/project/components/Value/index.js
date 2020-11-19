import React from 'react'

import Container from './Container'
import Content from './Content'

import * as s from './Value.module.scss'

const Value = ({viewType, labelType, mbGoRefer, value}) => {
    if (Array.isArray(value) && value.length > 1) {
        return (
            <ul className={s.valueList}>
                {value.map((v, i) =>
                    <li key={i}>
                        <Container viewType={viewType} mbGoRefer={mbGoRefer} value={v}>
                            <Content viewType={viewType} labelType={labelType} value={v}/>
                        </Container>
                    </li>
                )}
            </ul>
        )
    }

    value = Array.isArray(value) ? value[0] : value

    return (
        <Container viewType={viewType} mbGoRefer={mbGoRefer} value={value}>
            <Content viewType={viewType} labelType={labelType} value={value}/>
        </Container>
    )
}

export const SingleViewValue = (props) => {
    return <Value viewType="single" {...props} />
}

export const ListViewValue = (props) => {
    return <Value viewType="list" {...props} />
}

export default Value