import React from 'react'
import {Link} from 'react-router-dom'
import {Table} from 'reactstrap'
import {trim} from "../../util/string";
import {useIntl} from "react-intl";

const PublicProjectList = ({rows}) => {
    const intl = useIntl()

    return (
        <Table>
            <thead>
            <tr>
                <th>{intl.formatMessage({id: 'search.result.id'})}</th>
                <th>{intl.formatMessage({id: 'search.result.title'})}</th>
                <th className="text-nowrap">{intl.formatMessage({id: 'search.result.experiment'})}</th>
                <th className="text-nowrap">{intl.formatMessage({id: 'search.result.sample'})}</th>
                <th className="text-nowrap">{intl.formatMessage({id: 'search.result.measurement'})}</th>
                <th className="text-nowrap">{intl.formatMessage({id: 'search.result.contact'})}</th>
                <th className="text-nowrap">{intl.formatMessage({id: 'search.result.publish'})}</th>
            </tr>
            </thead>
            <tbody>
            {rows.map((row, index) => {
                const id = row.ids.id
                const project = row.listdata
                const title = project.titles.title
                const experiments = row.aggregate.experiments ? row.aggregate.experiments : []
                const contact = project.persons.contact
                const publishedDate = row.published_date

                const rowSpan = experiments && experiments.length > 0 ? experiments.length : 1

                return (
                    <React.Fragment key={index}>
                        <tr>
                            <td rowSpan={rowSpan}><Link to={`/project/${id}`}>{id}</Link></td>
                            <td rowSpan={rowSpan}><Link to={`/project/${id}`}>
                                <div dangerouslySetInnerHTML={{__html: trim(title)}}/>
                            </Link></td>
                            <td className="text-nowrap">{experiments && experiments.length > 0 ? experiments[0].experiment_id : null}</td>
                            <td className="text-nowrap">{experiments && experiments.length > 0 ? experiments[0].samples : null}</td>
                            <td className="text-nowrap">{experiments && experiments.length > 0 ? experiments[0].measurments : null}</td>
                            <td className="text-nowrap" rowSpan={rowSpan}>
                                <ul>
                                    <li key={contact.id}>
                                        <Link
                                            to={`/project/${id}/resource/person/${contact.id}`}>{contact.data.label}</Link>
                                    </li>
                                </ul>
                            </td>
                            <td className="text-nowrap">{publishedDate}</td>
                        </tr>
                        {experiments ? experiments.slice(1).map(experiment => (
                            <tr key={experiment.experiment_id}>
                                <td className="text-nowrap">{experiment.experiment_id}</td>
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

export default PublicProjectList
