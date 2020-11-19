const mode = 'cors';

const requestPost = (accessToken, url, params) => {
    const method = "POST"
    const body = params ? JSON.stringify(params) : "{}";

    let headers = {
        Accept: 'application/json',
        'Content-Type': 'application/json'
    }

    if (accessToken) {
        headers = Object.assign(headers, {Authorization: 'Bearer ' + accessToken})
    }

    return fetch(url
        , {method, headers, body, mode})
        .then(response => {
            return response
        })
        .catch(error => ({error}))
}

const requestGet = (accessToken, url) => {
    const method = "GET"

    let headers = {
        Accept: 'application/json',
        'Content-Type': 'application/json'
    }

    if (accessToken) {
        headers = Object.assign(headers, {Authorization: 'Bearer ' + accessToken})
    }

    return fetch(url, {method, headers, mode})
        .then(response => {
            return response
        })
        .catch(error => ({error}))
}

const requestDelete = (accessToken, url) => {
    const method = "DELETE"

    let headers = {
        Accept: 'application/json',
        Authorization: 'Bearer ' + accessToken,
    }

    return fetch(url, {method, headers, mode})
        .then(response => {
            return response
        })
        .catch(error => ({error}))
}

export {
    requestPost,
    requestGet,
    requestDelete
}