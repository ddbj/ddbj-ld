export const getData = (projectBook, row, parser) => {
    if (!row) return null
    return parser(projectBook, row)
}

export const getDataList = (projectBook, rows, parser) => {
    return rows.map(row => parser(projectBook, row))
}