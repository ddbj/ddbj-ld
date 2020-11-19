import _ from 'lodash'

import baseProjectBook1 from './RMM00002_d001_ID_020716_export_mb.json'
import baseProjectBook2 from './RPMM0002_06_ID_020716_export_mb.json'
import baseProjectBook3 from './RPMM0001_ID_020716_export_mb.json'
import baseProjectBook4 from './RPMM0005_02_ID_020716_export_mb.json'

const getBaseProjectBook = (index = 0) => {
    switch (index % 4) {
        case 0:
            return baseProjectBook1
        case 1:
            return baseProjectBook2
        case 2:
            return baseProjectBook3
        case 3:
        default:
            return baseProjectBook4
    }
}

export const newId = (number, length = -5) => {
    return `RMM${`00000${number}`.slice(length)}`
}

export const getProjectBookByIndex = index => {
    const projectBook = _.cloneDeep(getBaseProjectBook(index))

    const id = newId(index + 1)
    const projectSheetIndex = projectBook.findIndex(sheet => {
        return sheet && sheet.sheet_id === 'project'
    })
    const body = Object.values(projectBook[projectSheetIndex].data[0])[0]

    body.Project = id
    body['id'] = [id]

    projectBook[projectSheetIndex].data[0] = {
        [id]: body
    }

    return projectBook
}

const idToIndex = id => {
    return +id.match(/(\d*)$/)[0] || 0
}

export const getProjectBookById = id => {
    const index = idToIndex(id)
    return getProjectBookByIndex(index - 1)
}