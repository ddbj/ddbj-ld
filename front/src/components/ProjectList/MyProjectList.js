import React from 'react'
import {Link} from 'react-router-dom'
import {Table} from 'reactstrap'
import {trim} from "../../util/string";
import {useIntl} from "react-intl";

const MyProjectList = ({rows}) => {
    const intl = useIntl()

    return (
        <Table>
            <thead>
            <tr>
                <th>{intl.formatMessage({id: 'me.project.list.id'})}</th>
                <th>{intl.formatMessage({id: 'me.project.list.title'})}</th>
                <th className="text-nowrap">{intl.formatMessage({id: 'me.project.list.experiment'})}</th>
                <th className="text-nowrap">{intl.formatMessage({id: 'me.project.list.sample'})}</th>
                <th className="text-nowrap">{intl.formatMessage({id: 'me.project.list.measurement'})}</th>
                <th className="text-nowrap">{intl.formatMessage({id: 'me.project.list.contact'})}</th>
                <th className="text-nowrap">{intl.formatMessage({id: 'me.project.list.status'})}</th>
                <th className="text-nowrap">{intl.formatMessage({id: 'me.project.list.editing'})}</th>
                <th className="text-nowrap">{intl.formatMessage({id: 'me.project.list.publish'})}</th>
            </tr>
            </thead>
            <tbody>
            {rows.map((project, index) => {
                const rowSpan = project.experiments && project.experiments.length > 0 ? project.experiments.length : 1
                const status = project.hidden
                    ? intl.formatMessage({id: 'me.project.list.status.private'})
                    : project.published_date
                        ? intl.formatMessage({id: 'me.project.list.status.publish'})
                        : intl.formatMessage({id: 'me.project.list.status.unpublish'})

                const isEditing = project.editing
                    ? intl.formatMessage({id: 'me.project.list.editing.exists'})
                    : intl.formatMessage({id: 'me.project.list.editing.nothing'})

                const contactPersonId = project.person_contact ? project.person_contact.id : null;
                const contactPerson = project.person_contact ? project.person_contact.label : null;
                const url = project.editing ? `/me/project/${project.id}/draft` : `/me/project/${project.id}`

                return (
                    <React.Fragment key={index}>
                        <tr>
                            <td rowSpan={rowSpan}><Link to={url}>{project.id}</Link></td>
                            <td rowSpan={rowSpan}><Link to={url}>
                                <div dangerouslySetInnerHTML={{__html: trim(project.title)}}/>
                            </Link></td>
                            <td className="text-nowrap">{project.experiments && project.experiments.length > 0 ? project.experiments[0].experimentId : null}</td>
                            <td className="text-nowrap">{project.experiments && project.experiments.length > 0 ? project.experiments[0].samples : null}</td>
                            <td className="text-nowrap">{project.experiments && project.experiments.length > 0 ? project.experiments[0].measurments : null}</td>
                            <td className="text-nowrap" rowSpan={rowSpan}>
                                <Link
                                    to={`${url}/resource/person/${contactPersonId}`}>{contactPerson}</Link>
                            </td>
                            <td className="text-nowrap" rowSpan={rowSpan}>{status}</td>
                            <td className="text-nowrap" rowSpan={rowSpan}>{isEditing}</td>
                            <td className="text-nowrap"
                                rowSpan={rowSpan}>{project.published_date ? project.published_date : null}</td>
                        </tr>
                        {project.experiments && project.experiments.length > 0 ? project.experiments.slice(1).map(experiment => (
                            <tr key={experiment.id}>
                                <td className="text-nowrap">{experiment.experimentId}</td>
                                <td className="text-nowrap">{experiment.samples}</td>
                                <td className="text-nowrap">{experiment.measurments}</td>
                            </tr>
                        )) : null}
                    </React.Fragment>
                )
            })}
            </tbody>
        </Table>
    )
}

export default MyProjectList
