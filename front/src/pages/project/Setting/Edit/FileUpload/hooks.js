import {v4 as uuid} from 'uuid'
import {useCallback, useEffect, useState} from 'react'

const FILE_STATUS_UPLOADING = 'FILE_STATUS_UPLOADING'
const FILE_STATUS_UPLOADED = 'FILE_STATUS_UPLOADED'

const parseFileSets = fileSets => {
    return fileSets.map(fileSet => {
        fileSet.files = fileSet.files.map(file => ({
            ...file,
            status: FILE_STATUS_UPLOADED,
        }))
        return fileSet
    })
}

const fileToDataUrl = file => {
    return new Promise((reolve, reject) => {
        const reader = new FileReader()
        reader.addEventListener("load", function () {
            const {result} = reader
            reolve(result)
        }, false)
        reader.readAsDataURL(file)
    })
}

export const useFileUpload = (rows, defaultFilesets) => {
    const [fileSets, setFileSets] = useState(parseFileSets(defaultFilesets))
    const [relations, setRelations] = useState([])

    const createFileSet = useCallback(() => {
        setFileSets([
            ...fileSets,
            createBlankFileSet()
        ])
    }, [fileSets])

    const deleteFileSet = useCallback(id => {
        setFileSets(fileSets.filter(fileSet => fileSet.id !== id))
    }, [fileSets])

    const updateFileSet = useCallback(updatedFileSet => {
        setFileSets(fileSets.map(fileSet => fileSet.id === updatedFileSet.id ? updatedFileSet : fileSet))
    }, [fileSets])

    const updateFile = useCallback((fileSetId, updatedFile) => {
        const fileSet = fileSets.find(fileSet => fileSet.id === fileSetId)
        fileSet.files = fileSet.files.map(file => file.id === updatedFile.id ? updatedFile : file)
        setFileSets(fileSets.map(fs => fs.id === fileSet.id ? fileSet : fs))
    }, [fileSets])

    const uploadFiles = useCallback((fileSetId, selectedFiles) => {
        const fileSet = fileSets.find(fileSet => fileSet.id === fileSetId)

        const files = selectedFiles.map(selectedFile => {
            const id = uuid()

            const file = {
                id,
                name: selectedFile.name,
                url: null,
                status: FILE_STATUS_UPLOADING
            }

            fileToDataUrl(selectedFile).then(url => {
                updateFile(fileSetId, {
                    ...file,
                    url,
                    status: FILE_STATUS_UPLOADED
                })
            })

            return file
        })

        fileSet.files = [
            ...fileSet.files,
            ...files
        ]

        setFileSets(fileSets.map(fs => fs.id === fileSet.id ? fileSet : fs))
    }, [fileSets, updateFile])

    const deleteFile = useCallback((fileSetId, fileId) => {
        const fileSet = fileSets.find(fileSet => fileSet.id === fileSetId)
        fileSet.files = fileSet.files.filter(file => file.id !== fileId)
        setFileSets(fileSets.map(fs => fs.id === fileSetId ? fileSet : fileSet))
    }, [fileSets])

    const updateRelation = useCallback(updatedRelation => {
        const newRelations = relations.map(relation => relation.id === updatedRelation.id ? updatedRelation : relation)
        setRelations(newRelations)
    }, [relations])

    useEffect(() => setFileSets(parseFileSets(defaultFilesets)), [defaultFilesets])

    useEffect(() => {
        const urlToFileSetOption = {}
        const urlToFileOption = {}
        defaultFilesets.forEach(fileSet => {
            fileSet.files.forEach(file => {
                const {url} = file
                urlToFileSetOption[url] = {label: fileSet.label, value: fileSet.id}
                urlToFileOption[url] = {label: file.name, value: file.url}
            })
        })

        const relations = rows.map(row => {
            const {url} = row
            const fileSet = urlToFileSetOption[url] || null
            const file = urlToFileOption[url] || null
            const isSubmitted = fileSet && file

            return {
                ...row,
                fileSet,
                file,
                isSubmitted
            }
        })

        setRelations(relations)
    }, [rows, defaultFilesets])

    return {
        fileSets,
        relations,
        createFileSet,
        updateFileSet,
        deleteFileSet,
        uploadFiles,
        deleteFile,
        updateRelation
    }
}

export const createBlankFileSet = () => ({
    id: uuid(),
    label: 'ファイルセット',
    files: []
})