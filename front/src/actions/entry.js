const GET_ENTRIES  = 'ENTRY/GET_ENTRIES'
const SET_ENTRIES  = 'ENTRY/SET_ENTRIES'
const CREATE_ENTRY = 'ENTRY/CREATE_ENTRY'

const getEntries = (history) => ({
    type: GET_ENTRIES,
    payload: {history}
})

const setEntries = (entries) => ({
    type: SET_ENTRIES,
    payload: {entries}
})

const createEntry = () => ({
    type: CREATE_ENTRY,
    payload: {}
})

export {
    GET_ENTRIES, getEntries,
    SET_ENTRIES, setEntries,
    CREATE_ENTRY, createEntry
}