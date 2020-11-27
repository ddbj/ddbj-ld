import React from 'react'
import {Badge, Button, Table} from 'reactstrap'

import * as s from './Header.module.scss'
import {useIntl} from "react-intl";
import {useEditingInfo} from "../../../../../hooks/entries/biosample";

const Header = ({match, location, history}) => {
    const { uuid } = match.params
    const intl = useIntl()
    const { data } = useEditingInfo(uuid)

    return (
        <div className={s.container}>
            <Table>
                <tbody>
                <tr>
                    <th>Uuid</th>
                    <td>{data ? data.uuid : null}</td>
                </tr>
                <tr>
                    <th>Title</th>
                    <td>{data ? data.title : null}</td>
                </tr>
                <tr>
                    <th>Description</th>
                    <td>{data ? data.description : null}</td>
                </tr>
                <tr>
                </tr>
                <tr>
                    <th>Entry Status</th>
                    <td>{data ? data.entryStatus: null}</td>
                </tr>
                <tr>
                    <th>Validation Status</th>
                    <td>{data ? data.validationStatus : null}</td>
                </tr>
                <tr>
                    <th>Menu</th>
                    <td>
                        <Button
                            color="primary"
                            onClick={null}
                        >
                            Validate
                        </Button>
                        {'　'}
                        <Button
                            color="primary"
                            onClick={null}
                        >
                            Submit
                        </Button>
                        {'　'}
                        <Button
                            color="primary"
                            onClick={null}
                        >
                            Request to public
                        </Button>
                        {'　'}
                        <Button
                            color="primary"
                            onClick={null}
                        >
                            Request to cancel
                        </Button>
                        {'　'}
                        <Button
                            color="primary"
                            onClick={null}
                        >
                            Request to update
                        </Button>
                    </td>
                </tr>
                <tr>
                    <th>Admin Menu</th>
                    <td>
                        <Button
                            color="danger"
                            onClick={null}
                        >
                            To unsubmitted
                        </Button>
                        {'　'}
                        <Button
                            color="danger"
                            onClick={null}
                        >
                            To private
                        </Button>
                        {'　'}
                        <Button
                            color="danger"
                            onClick={null}
                        >
                            To public
                        </Button>
                        {'　'}
                        <Button
                            color="danger"
                            onClick={null}
                        >
                            To suppressed
                        </Button>
                        {'　'}
                        <Button
                            color="danger"
                            onClick={null}
                        >
                            to killed
                        </Button>
<<<<<<< HEAD
                        {'　'}
                        <Button
                            color="danger"
                            onClick={null}
                        >
                            to replaced
                        </Button>
=======
>>>>>>> 差分修正
                    </td>
                </tr>
                </tbody>
            </Table>
            <div className={s.navigation}>
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(uuid + "/files"))}
                    onClick={() => history.push(`/entries/biosample/${uuid}/files`)}
                >
                    Metadata & Files
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(uuid + "/summary"))}
                    onClick={() => history.push(`/entries/biosample/${uuid}/summary`)}
                >
                    Validation Summary
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(uuid + "/comment"))}
                    onClick={() => history.push(`/entries/biosample/${uuid}/comment`)}
                >
                    Comment
                </Button>
            </div>
        </div>
    )
}

const FileHeader = ({match, location, history}) => {
    const { uuid } = match.params
    const intl = useIntl()

    return (
        <div className={s.container}>
            <div className={s.navigation}>
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(uuid + "/files/upload"))}
                    onClick={() => history.push(`/entries/biosample/${uuid}/files/upload`)}
                >
                    Metadata & Files
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(uuid + "/files/download"))}
                    onClick={() => history.push(`/entries/biosample/${uuid}/files/download`)}
                >
                    Validation Summary
                </Button>
            </div>
        </div>
    )
}

export {
    Header,
    FileHeader
}
