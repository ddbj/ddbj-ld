import React from 'react'
import {getProject} from '../../../../services/projectBook/view'

import {useProject, useProjectBook} from '../../../../hooks/project/projectBook'

import Widget from '../../../../components/Widget'

import ProjectSummary from '../../components/Single/Customezied/ProjectSummary'
import ProjectSingle from '../../components/Single/Project'
import {useIntl} from "react-intl";

const Project = ({match}) => {
    const {id} = match.params

    const project = useProject(id)
    const projectBook = useProjectBook(id)
    const intl = useIntl()

    const projectSheetInfo = projectBook ? getProject(projectBook) : null

    return (
        <>
            <Widget>
                <h2 className="page-title">{intl.formatMessage({id: 'project.detail.overview.title'})}</h2>
                <ProjectSummary project={project}/>
            </Widget>
            {projectSheetInfo ?
                <Widget>
                    <h2 className="page-title">{intl.formatMessage({id: 'project.detail.info.title'})}</h2>
                    <ProjectSingle project={projectSheetInfo}/>
                </Widget>
                : null
            }
        </>
    )
}

export default Project