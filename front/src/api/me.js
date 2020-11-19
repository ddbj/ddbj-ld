// TODO: Adminがエンドポイントわけるかは不明
import {getProjectBookByIndex} from './data'

const DEFAULT_OFFSET = 0
const DEFAULT_COUNT = 30

export const getProjects = async ({offset = DEFAULT_OFFSET, count = DEFAULT_COUNT}) => {
    return {
        rows: Array.from({length: count}).fill(null).map((__, i) => {
            const index = offset + i
            const projectBook = getProjectBookByIndex(index)
            return projectBook
        }),
        total: 30
    }
}

export const updateProfile = async ({email, name}) => {
    return {email, name}
}