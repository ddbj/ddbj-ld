import React from 'react'
import {Badge, Button, Table} from 'reactstrap'

import * as s from './Header.module.scss'
import {useIntl} from "react-intl";
import {useEditingInfo} from "../../../../hooks/entries/jvar";

const Header = ({match, location, history}) => {
    const { entryUUID } = match.params
    const { currentEntry } = useEditingInfo(history, entryUUID)

    return (
        <div className={s.container}>
            <Table>
                <tbody>
                <tr>
                    <th>Uuid</th>
                    <td>{currentEntry ? currentEntry.uuid : null}</td>
                </tr>
                <tr>
                    <th>Label</th>
                    <td>{currentEntry ? currentEntry.label : null}</td>
                </tr>
                <tr>
                    <th>Type</th>
                    <td>{currentEntry ? currentEntry.type : null}</td>
                </tr>
                <tr>
                </tr>
                <tr>
                    <th>Status</th>
                    <td>{currentEntry ? currentEntry.status: null}</td>
                </tr>
                <tr>
                    <th>Validation Status</th>
                    <td>{currentEntry ? currentEntry.validation_status : null}</td>
                </tr>
                <tr>
                    <th>Menu</th>
                    <td>
                        <Button
                            color="primary"
                            onClick={() => history.push(`/entries/jvar/${entryUUID}/validate`)}
                            disabled={currentEntry ? !currentEntry.menu.validate : true }
                        >
                            Validate
                        </Button>
                        {'　'}
                        <Button
                            color="primary"
                            onClick={null}
                            disabled={currentEntry ? !currentEntry.menu.submit : true }
                        >
                            Submit
                        </Button>
                        {'　'}
                        <Button
                            color="primary"
                            onClick={null}
                            disabled={currentEntry ? !currentEntry.menu.request_to_public : true }
                        >
                            Request to public
                        </Button>
                        {'　'}
                        <Button
                            color="primary"
                            onClick={null}
                            disabled={currentEntry ? !currentEntry.menu.request_to_cancel : true }
                        >
                            Request to cancel
                        </Button>
                        {'　'}
                        <Button
                            color="primary"
                            onClick={null}
                            disabled={currentEntry ? !currentEntry.menu.request_to_update : true }
                        >
                            Request to update
                        </Button>
                    </td>
                </tr>
                {currentEntry && currentEntry.admin_menu ?
                    <tr>
                        <th>Admin Menu</th>
                        <td>
                            <Button
                                color="danger"
                                onClick={null}
                                disabled={currentEntry ? !currentEntry.admin_menu.to_unsubmitted : true }
                            >
                                To unsubmitted
                            </Button>
                            {'　'}
                            <Button
                                color="danger"
                                onClick={null}
                                disabled={currentEntry ? !currentEntry.admin_menu.to_private : true }
                            >
                                To private
                            </Button>
                            {'　'}
                            <Button
                                color="danger"
                                onClick={null}
                                disabled={currentEntry ? !currentEntry.admin_menu.to_public : true }
                            >
                                To public
                            </Button>
                            {'　'}
                            <Button
                                color="danger"
                                onClick={null}
                                disabled={currentEntry ? !currentEntry.admin_menu.to_supressed : true }
                            >
                                To suppressed
                            </Button>
                            {'　'}
                            <Button
                                color="danger"
                                onClick={null}
                                disabled={currentEntry ? !currentEntry.admin_menu.to_killed : true }
                            >
                                to killed
                            </Button>
                            {'　'}
                            <Button
                                color="danger"
                                onClick={null}
                                disabled={currentEntry ? !currentEntry.admin_menu.to_replaced : true }
                            >
                                to replaced
                            </Button>
                        </td>
                    </tr>
                    :
                        null
                }
                </tbody>
            </Table>
            <div className={s.navigation}>
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(entryUUID + "/files"))}
                    onClick={() => history.push(`/entries/jvar/${entryUUID}/files`)}
                >
                    Files
                </Button>
                {/*{'　'}*/}
                {/*<Button*/}
                {/*    outline*/}
                {/*    color="primary"*/}
                {/*    active={location.pathname.match(new RegExp(uuid + "/summary"))}*/}
                {/*    onClick={() => history.push(`/entries/jvar/${uuid}/summary`)}*/}
                {/*>*/}
                {/*    Validation Summary*/}
                {/*</Button>*/}
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(entryUUID + "/comments"))}
                    onClick={() => history.push(`/entries/jvar/${entryUUID}/comments`)}
                >
                    Comments
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(entryUUID + "/requests"))}
                    onClick={() => history.push(`/entries/jvar/${entryUUID}/requests`)}
                >
                    Requests
                </Button>
            </div>
        </div>
    )
}

const FileHeader = ({match, location, history}) => {
    const { entryUUID } = match.params
    const intl = useIntl()

    return (
        <div className={s.container}>
            <div className={s.navigation}>
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(entryUUID + "/files/upload"))}
                    onClick={() => history.push(`/entries/jvar/${entryUUID}/files/upload`)}
                >
                    Metadata & Files
                </Button>
                {'　'}
                <Button
                    outline
                    color="primary"
                    active={location.pathname.match(new RegExp(entryUUID + "/files/download"))}
                    onClick={() => history.push(`/entries/jvar/${entryUUID}/files/download`)}
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
