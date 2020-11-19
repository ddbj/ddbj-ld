import React, {useMemo, useState} from 'react'

import * as getResourceService from '../../../../services/projectBook/get/resource'

import {useProjectBook} from '../../../../hooks/project/projectBook'
import {useUploadedFiles} from '../../../../hooks/project/setting'

import Widget from '../../../../components/Widget'
import {Link, Select, useFileUpload} from './FileUpload'
import {Button, Form, FormGroup, Input} from "reactstrap";
import Loading from "react-loading";

const SupplFile = ({match}) => {
    const {id} = match.params

    const [uploadFile, setUploadFile] = useState(null)
    const [isLoading, setIsLoading] = useState(false)

    const uploadedFiles = useUploadedFiles(id, 'supplementary_file')
    const projectBook = useProjectBook(id)

    const rows = useMemo(() => {
        if (!projectBook) return null
        const rows = getResourceService.getSupplementaryFileList(projectBook)

        return rows.map((row, index) => {
            return {
                id: row.id[0],
                label: row.title[0],
                url: index === 0 ? "https://example.com/files/1" : null
            }
        })
    }, [projectBook])

    const {
        fileSets,
        relations,
        createFileSet,
        updateFileSet,
        deleteFileSet,
        uploadFiles,
        deleteFile,
        updateRelation
    } = useFileUpload(rows, uploadedFiles)

    const select = () => (
        <Select {...{
            fileSets,
            createFileSet,
            deleteFileSet,
            updateFileSet,
            uploadFiles,
            deleteFile
        }} />)
    const link = () => (
        <Link {...{
            relations,
            fileSets,
            updateRelation
        }} />
    )

    const handleUpload = () => {
        let reader = new FileReader()

        // 読み込み完了時の処理
        reader.onload = () => {
            // const metadata = JSON.parse(reader.result).metadata
            // FIXME バリデート
            setIsLoading(true)
            // dispatch(postDraftMetadata(id, metadata, setIsLoading))
        }

        // reader.readAsText(uploadFile)
    }

    return (
        <Widget>
            <div className="d-flex align-items-center w-100 mb-4">
                <h3 className="page-title flex-grow-1 mb-0">添付ファイルのアップロード</h3>
            </div>
            <section className="mb-5 py-2 d-block">
                <Form>
                    <FormGroup>
                        <Input
                            type="file"
                            onChange={(e) => setUploadFile(e.target.files[0])}
                        />
                    </FormGroup>
                    <Button
                        color="primary"
                        disabled={uploadFile === null || uploadFile.type !== "application/json"}
                        onClick={handleUpload}
                    >
                        {isLoading
                            ? <Loading type={'spin'} height={20} width={20}/>
                            : <span>アップロード</span>
                        }
                    </Button>
                    {uploadFile !== null && uploadFile.type !== "application/json"
                        ?
                        <div style={{color: "red"}}>Json形式のファイルを選択してください</div>
                        :
                        null
                    }
                </Form>
            </section>
        </Widget>
    )
}

export default SupplFile