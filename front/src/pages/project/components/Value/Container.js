import React from 'react'
import {Link} from 'react-router-dom'

import {useBuildResourcePath} from '../../../../hooks/project/path'

const MB_GO_REFER_NO = 'no'
const MB_GO_REFER_YES = 'yes'
const MB_GO_REFER_INNER = 'inner'
const MB_GO_REFER_LINK = 'link'
const MB_GO_REFER_LINK_A_AS_B = 'link a as b'
const MB_GO_REFER_REFERENCE = 'ref'
const MB_GO_REFER_BLANK = ''

const parseMbGoRefer = mbGoRefer => {
    switch (mbGoRefer) {
        case 'yes':
            return MB_GO_REFER_YES
        case 'no':
            return MB_GO_REFER_NO
        case 'inner':
            return MB_GO_REFER_INNER
        case 'link':
            return MB_GO_REFER_LINK
        case 'ref':
            return MB_GO_REFER_REFERENCE
        case '':
        case undefined:
            return MB_GO_REFER_BLANK
        default:
            if (mbGoRefer && mbGoRefer.match(/^link .* as .*$/)) return MB_GO_REFER_LINK_A_AS_B
            throw Error(`unexpected mb_go_refer '${mbGoRefer}'`)
    }
}

const Container = ({mbGoRefer, viewType, value, children}) => {
    if (!value) return null
    switch (parseMbGoRefer(mbGoRefer)) {
        case MB_GO_REFER_YES:
            return <ReferContaienr {...{viewType, value, children}} />
        case MB_GO_REFER_INNER:
            return <InnerReferContaienr {...{viewType, value, children}} />
        case MB_GO_REFER_LINK_A_AS_B:
        case MB_GO_REFER_LINK:
            return <LinkContainer {...{viewType, value, children}} />
        case MB_GO_REFER_REFERENCE:
            return <ReferenceContainer {...{viewType, value, children}} />
        case MB_GO_REFER_NO:
        case MB_GO_REFER_BLANK:
        default:
            return <DefaultContainer {...{viewType, value, children}} />
    }
}

const ReferContaienr = ({value, children}) => {
    return (
        <React.Fragment>
            {children}
        </React.Fragment>
    )
}

const InnerReferContaienr = ({value, children}) => {
    const {projectId, resourceName, resourceId} = value.option
    const buildPath = useBuildResourcePath()

    return (
        <Link to={buildPath(projectId, resourceName, resourceId)}>
            <span style={{fontWeight: 'bolder'}}>{children}</span>
        </Link>
    )
}

const DefaultContainer = ({children}) => {
    return (
        <>{children}</>
    )
}

const LinkContainer = ({value, children}) => {
    const {href} = value.option
    return href ? (
        <a href={href} target="_blank" rel="noopener noreferrer">
            <i className="fa fa-link"/>{' '}{children}
        </a>
    ) : (
        <span>{children}</span>
    )
}

const ReferenceContainer = ({viewType, value}) => {
    const {projectId, reference} = value.option
    const buildResourcePath = useBuildResourcePath()

    if (null === reference) {
        return null
    }

    if (viewType === 'list') {
        return (
            <Link to={buildResourcePath(projectId, 'reference', reference.id)}>
                {reference.citation_label ? reference.citation_label[0] : reference.title[0]}
            </Link>
        )
    }


    if (null == reference) {
        return null;
    }

    return (
        <>
            {reference.title[0]}<br/>
            {reference.authors.join(',')}<br/>
            <i>{reference.journal[0]}</i>
            {(reference.volume && reference.volume[0]) ? (<> <b>{reference.volume[0]}</b></>) : null}
            {(reference.issue && reference.issue[0]) ? (<> ({reference.issue[0]})</>) : null}
            {(reference.pages && reference.pages[0]) ? (<>: {reference.pages[0]}</>) : null}.
            ({reference.year[0]})
            {(reference.doi && reference.doi[0]) ? (
                <>
                    <br/>DOI: <a target="_blank" rel="noopener noreferrer"
                                 href={`https://doi.org/${reference.doi[0]}`}><i className="fa fa-link"/>{' '}{reference.doi[0]}</a>
                </>
            ) : null}
            {(reference.pmid && reference.pmid[0]) ? (
                <>
                    <br/>PMID: <a target="_blank" rel="noopener noreferrer"
                                  href={`https://pubmed.ncbi.nlm.nih.gov/${reference.pmid[0]}`}><i className="fa fa-link"/>{' '}{reference.pmid[0]}</a>
                </>
            ) : null}
        </>
    )
}

export default Container