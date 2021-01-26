import React from "react"

const VisibilityColumnFilter = ({
                                column: { filterValue, setFilter, preFilteredRows },
                            }) => {

    const options = React.useMemo(() => {
        const options = new Set()
        preFilteredRows.forEach(row => {
            options.add(row.values["admin"])
        })
        return [...options.values()]
    }, [preFilteredRows])

    return (
        <select
            value={filterValue}
            onChange={e => {
                setFilter(e.target.value || undefined)
            }}
        >
            <option value="">All</option>
            <option value={true}>
                DDBJ Only
            </option>
            <option value={false}>
                General
            </option>
        </select>
    )
}

export default VisibilityColumnFilter