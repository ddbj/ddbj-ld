const isFunction = obj => typeof obj === 'function'

const parseSheetValue = (sheetValue) => {
    if (!sheetValue) return []
    return sheetValue.filter(Boolean)
}

export const parse = (sheetValue, viewData) => {
    sheetValue = parseSheetValue(sheetValue)

    return sheetValue.map(value => {
        if (!viewData) return {content: value}
        if (isFunction(viewData)) return viewData(value)
        return viewData
    })
}
