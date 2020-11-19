import React, {useMemo, useState} from 'react'
import {Button, ButtonGroup} from 'reactstrap'
import Select from 'react-select'

const SIZE = 20

const Relation = ({relation, relations, fileSets, onUpdate}) => {

    const linkedFileIds = useMemo(() => {
        return relations.reduce((ids, relation) => {
            if (!relation.file || !relation.file.value) return ids
            const id = relation.file.value
            return {...ids, [id]: true}
        }, {})
    }, [relations])

    const fileSetOptions = useMemo(() => {
        return fileSets.filter(fileSet => {
            return fileSet.files.filter(file => !linkedFileIds[file.id]).length > 0
        }).map(fileSet => ({
            label: fileSet.label,
            value: fileSet.id,
        }))
    }, [fileSets, linkedFileIds])

    const fileOptions = useMemo(() => {
        const fileSetId = relation.fileSet && relation.fileSet.value ? relation.fileSet.value : null
        const fileSet = fileSetId ? fileSets.find(fileSet => fileSet.id === fileSetId) : null
        if (!fileSet) return []
        return fileSet.files.filter(file => !linkedFileIds[file.id]).map(file => ({
            value: file.id,
            label: file.name,
        }))
    }, [fileSets, linkedFileIds, relation.fileSet])

    return (
        <div className="border-bottom d-flex py-2 align-items-center">
            <div className="flex-grow-1">{relation.label}</div>
            <div className="d-flex align-items-center">
                <div style={{width: '15rem'}}>
                    <Select
                        options={fileSetOptions}
                        value={relation.fileSet}
                        onChange={fileSet => onUpdate({
                            ...relation,
                            fileSet
                        })}/>
                </div>
                <div className="px-2">/</div>
                <div style={{width: '15rem'}}>
                    <Select
                        options={fileOptions}
                        disabled={fileOptions.length < 1}
                        value={relation.file}
                        onChange={file => onUpdate({...relation, file})}/>
                </div>
            </div>
            <button disabled={!relation.file} className="btn btn-success ml-2"
                    onClick={() => onUpdate({...relation, isSubmitted: true})}>
                <i className={`mr-1 la la-check`}/>
            </button>
        </div>
    )
}

const Link = ({relations, fileSets, updateRelation}) => {
    const [isShownAll, setIsShownAll] = useState(false)
    const [offset, setOffset] = useState(0)

    const targetRelations = useMemo(() => {
        if (isShownAll) return relations
        return relations.filter(relation => !relation.isSubmitted)
    }, [isShownAll, relations])

    const pages = useMemo(() =>
        Array
            .from({length: Math.floor(targetRelations.length / SIZE)})
            .fill(null)
            .map((_, i) => ({
                    offset: i * SIZE,
                    label: i + 1
                })
            ), [targetRelations.length])

    const renderRelations = useMemo(() => targetRelations.slice(offset, offset + SIZE), [offset, targetRelations])

    return (
        <div>
            <div className="px-2 py-3">
                <input id="shownAllCheckbox" checked={isShownAll} type="checkbox"
                       onChange={event => setIsShownAll(event.target.checked)} className="mr-2"/>
                <label htmlFor="shownAllCheckbox">すべてを表示する</label>
            </div>
            <div>
                {renderRelations.map(relation =>
                    <Relation
                        key={relation.id}
                        relation={relation}
                        relations={relations}
                        fileSets={fileSets}
                        onUpdate={updatedRelation => updateRelation(updatedRelation)}/>
                )}
            </div>
            {!isShownAll && renderRelations.length < 1 ? (
                <div className="alert alert-success">
                    すべてのファイルの紐付けを完了しました。
                </div>
            ) : null}
            <div className="mt-2">
                <ButtonGroup className="flex-wrap">
                    {pages.map(page => (
                        <Button
                            key={page.offset}
                            color="default" active={page.offset === offset}
                            onClick={event => setOffset(+event.target.value)}
                            value={page.offset}>{page.label}</Button>
                    ))}
                </ButtonGroup>
            </div>
        </div>
    )
}

export default Link