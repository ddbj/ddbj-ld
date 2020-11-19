import {useCallback, useMemo, useState} from 'react'

import {useIsAuthorized} from '../auth'
import {useProject, useProjectBook} from './projectBook'
import {useDispatch, useSelector} from "react-redux"
import * as projectBookAction from "../../actions/project"
import config from '../../config'

export const ROLE_TYPE_OWNER = 'ROLE_TYPE_OWNER'
export const ROLE_TYPE_EDITOR = 'ROLE_TYPE_EDITOR'
export const ROLE_TYPE_VIEWER = 'ROLE_TYPE_VIEWER'
export const ROLE_TYPE_GUEST = 'ROLE_TYPE_GUEST'

const createUrl = (id, token) => {

    return `${config.baseURLClient}/project/${id}/draft?token=${token}`
}

export const getTemporaryMembers = (id, browseTokens) => {
    const temporaryMembers = browseTokens.map(token => {
        return {
            label: token.label,
            url: createUrl(id, token.token),
            token: token.token,
            expireDate: token.expireDate
        }
    })

    return temporaryMembers
}

// TODO: 削除
const members = [{
    id: 'XXX000001',
    name: '北海道太郎',
    start_date: '2019-10-01',
    end_date: '',
    role: ROLE_TYPE_OWNER,
    is_stopped: false
}, {
    id: 'XXX000002',
    name: '東京二郎',
    start_date: '2020-01-01',
    end_date: '',
    role: ROLE_TYPE_EDITOR,
    is_stopped: false
}, {
    id: 'XXX000003',
    name: '沖縄三郎',
    start_date: '2020-03-01',
    end_date: '',
    role: ROLE_TYPE_EDITOR,
    is_stopped: false
},
    {
        id: 'XXX000004',
        name: '京都花子',
        start_date: '2020-01-01',
        end_date: '',
        role: ROLE_TYPE_EDITOR,
        is_stopped: true
    }]

export const useUploadedFiles = (id, type) => {
    const project = useSelector(({auth}) => {
        return auth.currentUser.project
    })

    const target = project.find((prj) => prj.ids.id === id)
    const files = target.draftFileList.map((file) => {
        if (file.type === type) {
            return file
        }
    })

    return files
}

export const useDownloadEditDataURL = (id) => {
    const project = useProject(id)

    const {name, url} = useMemo(() => {
        if (!project) return {name: null, url: null}

        const target = project.draftMetadata.metadata
            ? {
                metadata: project.draftMetadata.metadata,
                token: null
            }
            :
                project.metadata ? {
                metadata: project.metadata,
                token: null
                }
                : null

        if (null == target) {
            const url = null
            const name = null

            return {url, name}
        }

        const text = JSON.stringify(target, null, 2)
        const blob = new Blob([text], {type: 'text/plain'})
        const url = window.URL.createObjectURL(blob)
        const name = `${id}.json`

        return {url, name}
    }, [id, project])

    return {url, name}
}

export const useMembers = id => {
    return useMemo(() => {
        return members
    }, [])
}

export const useMember = (id, memberId) => {
    return useMemo(() => {
        return members.find(member => member.id === memberId)
    }, [memberId])
}

export const useTemporaryMembers = (id) => {
    const project = useProject(id)
    const browseTokens = project && project.browseTokens ? project.browseTokens : []
    const [temporaryMembers, setTemporaryMembers] = useState(getTemporaryMembers(id, browseTokens))

    return {
        setTemporaryMembers,
        temporaryMembers
    }
}

export const useTemporaryMember = (id, token) => {

    const project = useProject(id)
    const browseTokens = project && project.browseTokens ? project.browseTokens : []

    return useMemo(() => {
        return getTemporaryMembers(id, browseTokens).find(t => t.token === token) || null
    }, [id, token])
}

export const useRole = id => {
    const isAuthorized = useIsAuthorized()
    const projectBook = useProjectBook(id)

    // TODO: 何らかの方法で権限を取得
    return useMemo(() => {
        if (!projectBook) return ROLE_TYPE_GUEST
        if (!isAuthorized) return ROLE_TYPE_GUEST
        return ROLE_TYPE_OWNER
    }, [isAuthorized, projectBook])
}

export const useIsOwner = id => {
    const role = useRole(id)
    return useMemo(() => role === ROLE_TYPE_OWNER, [role])
}

export const useIsEditor = id => {
    const role = useRole(id)
    return useMemo(() => (
        role === ROLE_TYPE_EDITOR
        || role === ROLE_TYPE_OWNER
    ), [role])
}

export const useIsViewer = id => {
    const role = useRole(id)
    return useMemo(() => (
        role === ROLE_TYPE_VIEWER
        || role === ROLE_TYPE_EDITOR
        || role === ROLE_TYPE_OWNER
    ), [role])
}

export const useIsGuest = id => {
    const role = useRole(id)
    return useMemo(() => role === ROLE_TYPE_GUEST, [role])
}

export const useIsEditing = id => {
    const currentUser = useSelector(({auth}) => auth.currentUser)

    if (!currentUser) {
        return false
    }

    const project = currentUser.project.find(prg => prg.ids.id === id && prg.editing)

    if (project) {
        return true
    }

    return false
}

export const useApply = id => {
    const [visible, setVisible] = useState(false)
    const [isLoading, setIsLoading] = useState(false)
    const dispatch = useDispatch()

    const submitHandler = useCallback(() => {
        setIsLoading(true)
        dispatch(projectBookAction.requestPublishProject(id, setVisible, setIsLoading))
    }, [id])

    return {
        visible,
        setVisible,
        isLoading,
        setIsLoading,
        submitHandler
    }
}