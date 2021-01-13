import React from 'react'
import { Button, Table } from 'reactstrap'

import * as s from './Header.module.scss'
import { useEditingInfo } from "../../../../hooks/entries/jvar"

const Header = ({match, location, history}) => {
    const { entryUUID } = match.params
    const { currentEntry } = useEditingInfo(history, entryUUID)

    const {
        uuid,
        label,
        type,
        status,
        validation_status,
        menu,
        admin_menu,
    } = currentEntry

    return (
        <div className={s.container}>
            <Table>
                <tbody>
                <tr>
                    <th>UUID</th>
                    <td>{uuid}</td>
                </tr>
                <tr>
                    <th>LABEL</th>
                    <td>{label}</td>
                </tr>
                <tr>
                    <th>TYPE</th>
                    <td>{type}</td>
                </tr>
                <tr>
                </tr>
                <tr>
                    <th>STATUS</th>
                    <td>{status}</td>
                </tr>
                <tr>
                    <th>VALIDATION STATUS</th>
                    <td>{validation_status}</td>
                </tr>
                <tr>
                    <th>MENU</th>
                    <td>
                        <Button
                            color="primary"
                            onClick={() => history.push(`/entries/jvar/${entryUUID}/validate`)}
                            disabled={currentEntry ? !menu.validate : true }
                        >
                            Validate
                        </Button>
                        {'　'}
                        <Button
                            color="primary"
                            onClick={() => history.push(`/entries/jvar/${entryUUID}/submit`)}
                            disabled={currentEntry ? !menu.submit : true }
                        >
                            Submit
                        </Button>
                        {'　'}
                        <Button
                            color="primary"
                            onClick={() => history.push(`/entries/jvar/${entryUUID}/requests/publish`)}
                            disabled={currentEntry ? !menu.request_to_public : true }
                        >
                            Request to public
                        </Button>
                        {'　'}
                        <Button
                            color="primary"
                            onClick={() => history.push(`/entries/jvar/${entryUUID}/requests/cancel`)}
                            disabled={currentEntry ? !menu.request_to_cancel : true }
                        >
                            Request to cancel
                        </Button>
                        {'　'}
                        <Button
                            color="primary"
                            onClick={() => history.push(`/entries/jvar/${entryUUID}/requests/update`)}
                            disabled={currentEntry ? !menu.request_to_update : true }
                        >
                            Request to update
                        </Button>
                    </td>
                </tr>
                {currentEntry && admin_menu ?
                    <tr>
                        <th>ADMIN MENU</th>
                        <td>
                            <Button
                                color="danger"
                                onClick={null}
                                disabled={currentEntry ? !admin_menu.to_unsubmitted : true }
                            >
                                To unsubmitted
                            </Button>
                            {'　'}
                            <Button
                                color="danger"
                                onClick={null}
                                disabled={currentEntry ? !admin_menu.to_private : true }
                            >
                                To private
                            </Button>
                            {'　'}
                            <Button
                                color="danger"
                                onClick={null}
                                disabled={currentEntry ? !admin_menu.to_public : true }
                            >
                                To public
                            </Button>
                            {'　'}
                            <Button
                                color="danger"
                                onClick={null}
                                disabled={currentEntry ? !admin_menu.to_supressed : true }
                            >
                                To suppressed
                            </Button>
                            {'　'}
                            <Button
                                color="danger"
                                onClick={null}
                                disabled={currentEntry ? !admin_menu.to_killed : true }
                            >
                                to killed
                            </Button>
                            {'　'}
                            <Button
                                color="danger"
                                onClick={null}
                                disabled={currentEntry ? !admin_menu.to_replaced : true }
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

export default Header
