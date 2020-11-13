import {requestPost} from "./common"
import config from "../config"

export const login = async (code) => {
    const url = config.loginApi.replace("{code}", code)
    return requestPost(null, url, null)
}

export const loginWithJVar = async (id, password) => {
    // TODO 廃止予定
    const currentUser = {
        id,
        name: 'テストユーザー',
        type: 'viewer',
    }

    return currentUser
}

export const loginWithDDBJ = async () => {
    // TODO 廃止予定
    const currentUser = {
        id: 'admin',
        name: '管理者',
        type: 'admin',
        email: 'admin@example.com'
    }

    return currentUser
}

export const logOut = async () => {
    // TODO 廃止予定
}

export const refreshAccessToken = async (accountUuid) => {
    const url = config.refreshAccessTokenApi.replace("{account-uuid}", accountUuid)
    return requestPost(null, url, null)
}