// TODO: Adminがエンドポイントわけるかは不明
import {getProjectBookByIndex} from '../data'

const DEFAULT_OFFSET = 0
const DEFAULT_COUNT = 30

export const getAllProjects = async ({offset = DEFAULT_OFFSET, count = DEFAULT_COUNT}) => {
    console.info('get all projects with', {offset, count})
    return {
        rows: Array.from({length: count}).fill(null).map((__, i) => {
            const projectBook = getProjectBookByIndex(offset + i)
            return projectBook
        }),
        total: 500
    }
}