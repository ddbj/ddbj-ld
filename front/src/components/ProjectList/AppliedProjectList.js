import React from 'react'
import {Link} from 'react-router-dom'
import {Button, Table} from 'reactstrap'

const AppliedProjectList = ({rows}) => {
    return (
        <Table>
            <thead>
            <tr>
                <th>ID</th>
                <th>タイトル</th>
                <th className="text-nowrap">実験</th>
                <th className="text-nowrap">サンプル</th>
                <th className="text-nowrap">測定</th>
                <th className="text-nowrap">担当者</th>
                <th className="text-nowrap">リリース日</th>
                <th className="text-nowrap"></th>
            </tr>
            </thead>
            <tbody>
            {rows.map((project, index) => {
                const rowSpan = project.experiments.length
                return (
                    <React.Fragment key={index}>
                        <tr>
                            <td rowSpan={rowSpan}><Link to={`/project/${project.id}`}>{project.id}</Link></td>
                            <td rowSpan={rowSpan}><Link to={`/project/${project.id}`}>{project.title}</Link></td>
                            <td className="text-nowrap">{project.experiments[0].title}</td>
                            <td className="text-nowrap">{project.experiments[0].sample_count}</td>
                            <td className="text-nowrap">{project.experiments[0].measurement_count}</td>
                            <td className="text-nowrap" rowSpan={rowSpan}>
                                <ul>
                                    {project.person_contact.map(person => (
                                        <li key={person.id}>
                                            <Link
                                                to={`/project/${project.id}/resource/person/${person.id}`}>{person.label}</Link>
                                        </li>
                                    ))}
                                </ul>
                            </td>
                            <td className="text-nowrap" rowSpan={rowSpan}>{project.release_date}</td>
                            <td className="text-nowrap">
                                <Button color="primary">承認</Button>
                                <Button color="danger">却下</Button>
                            </td>
                        </tr>
                        {project.experiments.slice(1).map(experiment => (
                            <tr key={experiment.id}>
                                <td className="text-nowrap">{experiment.title}</td>
                                <td className="text-nowrap">{experiment.sample_count}</td>
                                <td className="text-nowrap">{experiment.measurement_count}</td>
                            </tr>
                        ))}
                    </React.Fragment>
                )
            })}
            </tbody>
        </Table>
    )
}

export default AppliedProjectList