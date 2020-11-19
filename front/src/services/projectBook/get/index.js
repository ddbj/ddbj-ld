import {SHEET_ID_PROJECT} from '../constant'
import * as debug from '../debug'
import * as constant from "../constant";

export const getSheet = (projectBook, sheetId) => {
    const sheet = projectBook.find(sheet => sheet && sheet.sheet_id === sheetId)
    if (!sheet) return null
    return sheet
}

export const getSheetData = (projectBook, sheetId) => {
    const sheet = getSheet(projectBook, sheetId)
    if (!sheet) return {}
    const data = sheet ? sheet.data[0] : {}
    return data
}

export const getSheetRows = (projectBook, sheetId) => {
    const data = getSheetData(projectBook, sheetId) || {}
    return Object.values(data)
}

export const getSheetRow = (projectBook, sheetId, rowId) => {
    const data = getSheetData(projectBook, sheetId) || {}
    const row = data[rowId] || null
    if (!row) debug.warn(`Access to non-existent row(sheet_id="${sheetId}", rowId="${rowId}")`)
    return row
}

export const getSheetRowWithKeyAndValue = (projectBook, sheetId, key, value, condition = null) => {
    const rows = getSheetRows(projectBook, sheetId)
    const row = (condition ? (
        rows.filter(row => row[condition.labelId][0] === condition.value)
    ) : rows).find(row => row[key] && row[key][0] === value) || null

    if (!row) debug.warn(`row(sheetId=${sheetId}, key=${key}, value=${value} condition=${JSON.stringify(condition)}) does not exists`)
    return row
}

export const getResourceId = (projectBook, sheetId, key, value, condition = null) => {
    const row = getSheetRowWithKeyAndValue(projectBook, sheetId, key, value, condition)

    if (!row) return null

    // const idKey = Object.keys(row)[0]
    const resourceId = row["id"][0]

    return resourceId
}

export const getSheetId = (projectBook, sheetId, key, value, condition = null) => {
    const row = getSheetRowWithKeyAndValue(projectBook, sheetId, key, value, condition)
    if (!row) return null

    const idKey = Object.keys(row)[2]
    const id = row[idKey][0]

    return id
}

export const getId = projectBook => {
    const data = getSheetData(projectBook, SHEET_ID_PROJECT)
    return String(Object.values(data)[0].id[0])
}
