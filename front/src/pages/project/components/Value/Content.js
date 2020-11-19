import React from 'react'
import { trim } from '../../../../util/string'

const LABEL_TYPE_ID = 'id'
const LABEL_TYPE_STRING = 'string'
const LABEL_TYPE_TEXT = 'text'
const LABEL_TYPE_DATE = 'date'
const LABEL_TYPE_URI = 'uri'
const LABEL_TYPE_FLOAT = 'float'
const LABEL_TYPE_YEAR = 'year'
const LABEL_TYPE_DOI = 'doi'
const LABEL_TYPE_URL = 'url'
const LABEL_TYPE_HTML = 'html'
const LABEL_TYPE_BLANK = ''

const IdContent = ({value}) => {
    return (
        <span dangerouslySetInnerHTML={createMarkup(trim(value.content))}/>
    )
}

const StringContent = ({value}) => {
    return <span dangerouslySetInnerHTML={createMarkup(trim(value.content))}/>
}

const TextContent = ({value}) => {
    return <span dangerouslySetInnerHTML={createMarkup(trim(value.content))}/>
}

const DateContent = ({value}) => {
    return <span dangerouslySetInnerHTML={createMarkup(trim(value.content))}/>
}

const UriContent = ({value}) => {
    return <span dangerouslySetInnerHTML={createMarkup(trim(value.content))}/>
}

const FloatContent = ({value}) => {
    return <span dangerouslySetInnerHTML={createMarkup(trim(value.content))}/>
}

const YearContent = ({value}) => {
    return <span dangerouslySetInnerHTML={createMarkup(trim(value.content))}/>
}

const DoiContent = ({value}) => {
    return <span dangerouslySetInnerHTML={createMarkup(trim(value.content))}/>
}

const UrlContent = ({value}) => {
    return (
        <React.Fragment>
            <a href={trim(value.content)} target="_blank"><i className="fa fa-link"/>{' '}{trim(value.content)}</a>
        </React.Fragment>
    )
}

const createMarkup = html => ({
    __html: html
})

const HtmlContent = ({value}) => {
    return <div dangerouslySetInnerHTML={createMarkup(trim(value.content))}/>
}

const DefaultContent = ({value}) => {
    return <div dangerouslySetInnerHTML={createMarkup(trim(value.content))}/>
}

const Content = ({value, labelType}) => {
    switch (labelType) {
        case LABEL_TYPE_ID:
            return <IdContent value={value}/>
        case LABEL_TYPE_STRING:
            return <StringContent value={value}/>
        case LABEL_TYPE_TEXT:
            return <TextContent value={value}/>
        case LABEL_TYPE_DATE:
            return <DateContent value={value}/>
        case LABEL_TYPE_URI:
            return <UriContent value={value}/>
        case LABEL_TYPE_FLOAT:
            return <FloatContent value={value}/>
        case LABEL_TYPE_YEAR:
            return <YearContent value={value}/>
        case LABEL_TYPE_DOI:
            return <DoiContent value={value}/>
        case LABEL_TYPE_URL:
            return <UrlContent value={value}/>
        case LABEL_TYPE_HTML:
            return <HtmlContent value={value}/>
        case LABEL_TYPE_BLANK:
        default:
            return <DefaultContent value={value}/>
    }
}

export default Content