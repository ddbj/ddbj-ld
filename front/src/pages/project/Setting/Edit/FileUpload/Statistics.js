import React, {useMemo} from 'react'

const Statistics = ({relations, fileSets}) => {
    const totalCount = useMemo(() => relations.length, [relations])
    const fileCount = useMemo(() => fileSets.reduce((count, fileSet) => count + fileSet.files.filter(file => !!file.url).length, 0), [fileSets])
    const linkedCount = useMemo(() => relations.filter(relation => relation.isSubmitted).length, [relations])
    return (
        <div>
            <span>選択済み: {fileCount}</span>
            <span> / </span>
            <span>全件: {totalCount}</span>
            <span> / </span>
            <span>紐付け済み: {linkedCount}</span>
        </div>
    )
}

export default Statistics