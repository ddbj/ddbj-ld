import React from 'react'
import {Table} from 'reactstrap'
import {useProjectSummary} from "../../../../../hooks/project/display"
import {useIntl} from "react-intl"

const ProjectSummary = ({project}) => {
    const {
        title,
        status,
        publishedDate,
        experiments,
        projectFiles
    } = useProjectSummary(project)

    const intl = useIntl()

    return (
        <>
            <Table>
                <tbody>
                <tr>
                    <th>{intl.formatMessage({id: 'project.detail.overview.description.title'})}</th>
                    <td>
                        <div dangerouslySetInnerHTML={{__html: title}}/>
                    </td>
                </tr>
                <tr>
                </tr>
                <tr>
                    <th>{intl.formatMessage({id: 'project.detail.overview.description.status'})}</th>
                    <td>{status}</td>
                </tr>
                <tr>
                    <th>{intl.formatMessage({id: 'project.detail.overview.description.publish'})}</th>
                    <td>{publishedDate}</td>
                </tr>
                </tbody>
            </Table>
            <Table>
                <thead>
                {experiments.length > 0
                    ?
                    <tr>
                        <th style={{width: 200}}>{intl.formatMessage({id: 'project.detail.overview.description.aggregate.id'})}</th>
                        <th style={{width: 200}}>{intl.formatMessage({id: 'project.detail.overview.description.aggregate.name'})}</th>
                        <th style={{width: 200}}>{intl.formatMessage({id: 'project.detail.overview.description.aggregate.sample'})}</th>
                        <th style={{width: 200}}>{intl.formatMessage({id: 'project.detail.overview.description.aggregate.measurement'})}</th>
                    </tr>
                    : null
                }
                </thead>
                <tbody>
                {experiments.map(exp => (
                    <tr>
                        <td style={{width: 200}}>{exp.experimentId}</td>
                        <td style={{width: 200}}>{exp.experimentName}</td>
                        <td style={{width: 200}}>{exp.samples}</td>
                        <td style={{width: 200}}>{exp.measurments}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
            <Table>
                <tbody>
                {
                    projectFiles.project > 0
                        ?
                        <tr>
                            <th>{intl.formatMessage({id: 'project.detail.overview.description.aggregate.supple'})}</th>
                            <td>
                                <div dangerouslySetInnerHTML={{__html: projectFiles.project}}/>
                            </td>
                        </tr>
                        : null
                }
                <tr>
                </tr>
                <tr>
                    {
                        projectFiles.raw > 0
                            ?
                            <tr>
                                <th>{intl.formatMessage({id: 'project.detail.overview.description.aggregate.raw'})}</th>
                                <td>
                                    <div dangerouslySetInnerHTML={{__html: projectFiles.raw}}/>
                                </td>
                            </tr>
                            : null
                    }
                </tr>
                <tr>
                    {
                        projectFiles.analysis > 0
                            ?
                            <tr>
                                <th>{intl.formatMessage({id: 'project.detail.overview.description.aggregate.analyzed'})}</th>
                                <td>
                                    <div dangerouslySetInnerHTML={{__html: projectFiles.analysis}}/>
                                </td>
                            </tr>
                            : null
                    }
                </tr>
                </tbody>
            </Table>
        </>
    )
}

export default ProjectSummary
