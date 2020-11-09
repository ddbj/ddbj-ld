import {requestPost} from "./common"
import config from "../config"

export const signIn = async (code) => {
    const url = config.loginApi.replace("{code}", code)
    return requestPost(null, url, null)
}

export const signInWithJVar = async (id, password) => {
    // TODO 廃止予定
    const currentUser = {
        id,
        name: 'テストユーザー',
        type: 'viewer',
    }

    return currentUser
}

export const signInWithDDBJ = async () => {
    // TODO 廃止予定
    const currentUser = {
        id: 'admin',
        name: '管理者',
        type: 'admin',
        email: 'admin@example.com'
    }

    return currentUser
}

export const signOut = async () => {
    // TODO 廃止予定
}

export const updateAccessToken = async (accountUuid) => {
    const url = config.updateAccessTokenApi.replace("{account-uuid}", accountUuid)
    return requestPost(null, url, null)
}