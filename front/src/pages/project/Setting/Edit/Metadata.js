import React, {useState} from 'react'
import {Button, Col, Form, FormGroup, Input, Row} from 'reactstrap'
import {useDispatch} from 'react-redux'

import Widget from '../../../../components/Widget/Widget'

import {useDownloadEditDataURL} from '../../../../hooks/project/setting'
import {postDraftMetadata} from '../../../../actions/project'
import Loading from "react-loading";
import {useIntl} from "react-intl";

const Project = ({match}) => {
    const {id} = match.params
    const [uploadFile, setUploadFile] = useState(null)
    const [isLoading, setIsLoading] = useState(false)
    const {url, name} = useDownloadEditDataURL(id)
    const dispatch = useDispatch();
    const intl = useIntl()

    const handleUpload = () => {
        let reader = new FileReader()

        // 読み込み完了時の処理
        reader.onload = () => {
            const metadata = JSON.parse(reader.result)
            // FIXME バリデート
            setIsLoading(true)
            dispatch(postDraftMetadata(id, metadata, setIsLoading))
        }

        reader.readAsText(uploadFile)
    }

    return (
        <Row>
            <Col md="6">
                <Widget>
                    <h2 className="page-title">{intl.formatMessage({id: 'project.detail.editing.project.title'})}</h2>
                    <section className="mb-5 py-2 d-block">
                        <h3 className="page-title">{intl.formatMessage({id: 'project.detail.editing.project.upload.title'})}</h3>
                        <p>{intl.formatMessage({id: 'project.detail.editing.project.upload.description'})}</p>
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
                                { isLoading
                                    ? <Loading type={'spin'} height={20} width={20}/>
                                    : <span>{intl.formatMessage({id: 'project.detail.editing.project.upload.button.upload'})}</span>
                                }
                            </Button>
                            {uploadFile !== null && uploadFile.type !== "application/json"
                                ?
                                <div style={{color: "red"}}>{intl.formatMessage({id: 'project.detail.editing.project.upload.message.error'})}</div>
                                :
                                null
                            }
                        </Form>
                    </section>
                    <section className="mb-5 py-2 d-block">
                        <h3>{intl.formatMessage({id: 'project.detail.editing.project.download.title'})}</h3>
                        <p>{intl.formatMessage({id: 'project.detail.editing.project.download.description'})}</p>
                        <FormGroup>
                            {url ? (
                                <a className="btn btn-primary" href={url} download={name}>{intl.formatMessage({id: 'project.detail.editing.project.download.button.download'})}</a>
                            ) : (
                                <span className="disabled btn btn-primary">{intl.formatMessage({id: 'project.detail.editing.project.download.button.download.no.data'})}</span>
                            )}
                        </FormGroup>
                    </section>
                </Widget>
            </Col>
        </Row>
    )
}

export default Project
