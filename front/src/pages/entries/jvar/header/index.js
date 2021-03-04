import React from 'react'
import { Button, Table } from 'reactstrap'

import * as s from './Header.module.scss'
import { useEditingInfo } from "../../../../hooks/entries/jvar"
import {useIsCurator} from "../../../../hooks/auth"

const Header = ({ match, location, history }) => {
    const { entryUUID } = match.params

    const {
        uuid,
        label,
        type,
        status,
        validationStatus,
        validate,
        submit,
        toUnsubmitted,
        toPrivate,
        toPublic,
        toSuppressed,
        toKilled,
        toReplaced,
    } = useEditingInfo(history, entryUUID)

    const isCurator = useIsCurator()

    return (
        <div className={s.container}>
            <Table responsive={true} style={{"table-layout": "auto", width: "90%"}}>
                <tbody>
                    <tr>
                        <th>UUID</th>
                        <td>{uuid}</td>
                        <th>LABEL</th>
                        <td>{label}</td>
                        <th>TYPE</th>
                        <td>{type}</td>
                        <th>STATUS</th>
                        <td>{status}</td>
                        <th>VALIDATION STATUS</th>
                        <td>{validationStatus}</td>
                    </tr>
                </tbody>
            </Table>
            <div style={{marginBottom: 10}}>
                <Button
                    color="primary"
                    onClick={() => history.push(`/entries/jvar/${entryUUID}/validate`)}
                    disabled={false === validate}
                >
                    Validate
                </Button>
                {'　'}
                <Button
                    color="primary"
                    onClick={() => history.push(`/entries/jvar/${entryUUID}/submit`)}
                    disabled={false === submit}
                >
                    Submit
                </Button>
                {isCurator ?
                    <span>
                        {'　'}
                        <Button
                            color="danger"
                            onClick={() => history.push(`/entries/jvar/${entryUUID}/status/unsubmitted`)}
                            disabled={false === toUnsubmitted}
                        >
                            To unsubmitted
                        </Button>
                        {'　'}
                        <Button
                            color="danger"
                            onClick={() => history.push(`/entries/jvar/${entryUUID}/status/private`)}
                            disabled={false === toPrivate}
                        >
                                To private
                            </Button>
                        {'　'}
                        <Button
                            color="danger"
                            onClick={() => history.push(`/entries/jvar/${entryUUID}/status/public`)}
                            disabled={false === toPublic}
                        >
                                To public
                            </Button>
                        {'　'}
                        <Button
                            color="danger"
                            onClick={() => history.push(`/entries/jvar/${entryUUID}/status/suppressed`)}
                            disabled={false === toSuppressed}
                        >
                                To suppressed
                            </Button>
                        {'　'}
                        <Button
                            color="danger"
                            onClick={() => history.push(`/entries/jvar/${entryUUID}/status/killed`)}
                            disabled={false === toKilled}
                        >
                                To killed
                            </Button>
                        {'　'}
                        <Button
                            color="danger"
                            onClick={() => history.push(`/entries/jvar/${entryUUID}/status/replaced`)}
                            disabled={false === toReplaced}
                        >
                                To replaced
                            </Button>
                    </span>
                    :
                    null
                }
            </div>
            {/*<div className={s.navigation}>*/}
            {/*    <Button*/}
            {/*        outline*/}
            {/*        color="primary"*/}
            {/*        active={location.pathname.match(new RegExp(entryUUID + "/files"))}*/}
            {/*        onClick={() => history.push(`/entries/jvar/${entryUUID}/files`)}*/}
            {/*    >*/}
            {/*        Files*/}
            {/*    </Button>*/}
            {/*    {'　'}*/}
            {/*    <Button*/}
            {/*        outline*/}
            {/*        color="primary"*/}
            {/*        active={location.pathname.match(new RegExp(entryUUID + "/comments"))}*/}
            {/*        onClick={() => history.push(`/entries/jvar/${entryUUID}/comments`)}*/}
            {/*    >*/}
            {/*        Comments*/}
            {/*    </Button>*/}
            {/*    {'　'}*/}
            {/*    <Button*/}
            {/*        outline*/}
            {/*        color="primary"*/}
            {/*        active={location.pathname.match(new RegExp(entryUUID + "/requests"))}*/}
            {/*        onClick={() => history.push(`/entries/jvar/${entryUUID}/requests`)}*/}
            {/*    >*/}
            {/*        Requests*/}
            {/*    </Button>*/}
            {/*    {'　'}*/}
            {/*    <Button*/}
            {/*        outline*/}
            {/*        color="primary"*/}
            {/*        active={location.pathname.match(new RegExp(entryUUID + "/submitters"))}*/}
            {/*        onClick={() => history.push(`/entries/jvar/${entryUUID}/submitters`)}*/}
            {/*    >*/}
            {/*        Submitters*/}
            {/*    </Button>*/}
            {/*    {'　'}*/}
            {/*    <Button*/}
            {/*        outline*/}
            {/*        color="primary"*/}
            {/*        active={location.pathname.match(new RegExp(entryUUID + "/summary"))}*/}
            {/*        onClick={() => history.push(`/entries/jvar/${entryUUID}/summary`)}*/}
            {/*    >*/}
            {/*        Summary*/}
            {/*    </Button>*/}
            {/*</div>*/}
        </div>
    )
}

export default Header