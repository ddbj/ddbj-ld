import React from 'react'

import {useAllProjects} from '../../../hooks/admin'

import Widget from '../../../components/Widget/Widget'
import ProjectList from '../../../components/ProjectList/AppliedProjectList'
import Pagination from '../../../components/Pagination/Pagination'

const base = '/admin/project/draft'

const Draft = ({history, location}) => {
    const {
        count, selectCount,
        pages, selectOffset,
        total, rows,
    } = useAllProjects({base, history, location})

    return (
        <Widget>
            <h2 className="page-title">公開申請中のプロジェクト</h2>
            {rows ? (
                <>
                    <div className="text-right">該当項目数: {total}件</div>
                    <ProjectList rows={rows}/>
                    <Pagination
                        pages={pages} count={count}
                        onOffsetChange={selectOffset} onCountChange={selectCount}/>
                </>
            ) : null}
        </Widget>
    )
}

export default Draft