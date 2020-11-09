import {useCallback} from "react"
import {useIsDraft, useIsPublic} from './status'
import {useToken} from "./preview";

export const useBuildPath = () => {
    const isDraft = useIsDraft()
    const isPublic = useIsPublic()
    const token = useToken()

    const buildPath = useCallback((id, childPath) => {
        let path = `/me/project/${id}`

        if(isPublic) path = `/project/${id}`
        if (isDraft) path += '/draft'
        if (childPath) path += childPath
        if(isDraft && token && childPath) path += `?token=${token}`

        return path
    }, [isDraft, isPublic, token])

    return buildPath
}

export const useBuildRoutePath = () => {
    const isDraft = useIsDraft()
    const isPublic = useIsPublic()

    const buildRoutePath = useCallback((childPath) => {
        let path = '/me/project/:id'

        if(isPublic) path = `/project/:id`
        if (isDraft) path += '/draft'
        if (childPath) path += childPath

        return path
    }, [isDraft, isPublic])

    return buildRoutePath
}

export const useBuildResourcePath = () => {
    const isDraft = useIsDraft()
    const isPublic = useIsPublic()
    const token = useToken()

    const buildResourcePath = useCallback((projectId, resourceName, resourceId) => {
        let path = `/me/project/${projectId}`
        if(isPublic) path = `/project/${projectId}`
        if (isDraft) path += '/draft'
        path += `/resource/${resourceName}`
        if (resourceId) path += `/${resourceId}`
        if(isDraft && token) path += `?token=${token}`

        return path
    }, [isDraft, isPublic, token])

    return buildResourcePath
}

export const useBuildNavItem = (id, basePath) => {
    const buildPath = useBuildPath()

    const buildNavItem = useCallback((label, childPath) => {
        return {
            label,
            path: buildPath(id, `${basePath}${childPath}`)
        }
    }, [basePath, buildPath, id])

    return buildNavItem
}

export const useBuildDynamicNavItemWithRows = (id, basePath) => {
    const buildPath = useBuildPath()

    const buildDynamicNavItemWithRows = useCallback((label, resourceName, rows, idKey = 'id') => {
        if (rows.length < 1) return null
        const resourceId = rows.length >= 2 ? null : rows[0][idKey][0]
        return {
            label,
            path: buildPath(id, `${basePath}/${resourceName}${resourceId ? `/${resourceId}` : ''}`)
        }
    }, [basePath, buildPath, id])

    return buildDynamicNavItemWithRows
}