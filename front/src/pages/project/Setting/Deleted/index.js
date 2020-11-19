import React from 'react'
import {useIsPublishedOnce} from '../../../../hooks/project/status'

const Deleted = ({match}) => {
    const {id} = match.params
    const isPublishedOnce = useIsPublishedOnce(id)

    return (
        <div className="text-center py-4">
            {isPublishedOnce ? (
                <>
                    <h3 className="page-title">非公開プロジェクト</h3>
                    <p>このプロジェクトは非公開にされています</p>
                </>
            ) : (
                <>
                    <h3 className="page-title">削除済みプロジェクト</h3>
                    <p>このプロジェクトは削除されています</p>
                </>
            )}
        </div>
    )
}

export default Deleted