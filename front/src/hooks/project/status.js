import {useMemo} from 'react'
import {useLocation} from 'react-router-dom'
import {useCurrentUser} from '../auth'
import {useProject} from "./projectBook";

export const PROJECT_VIEW_STATUS_READY = 'PROJECT_VIEW_STATUS_READY'
export const PROJECT_VIEW_STATUS_NOT_FOUND = 'PROJECT_VIEW_STATUS_NOT_FOUND'
export const PROJECT_VIEW_STATUS_FORBIDDEN = 'PROJECT_VIEW_STATUS_FORBIDDEN'

export const PROJECT_STATUS_CREATED = 'PROJECT_STATUS_CREATED'
export const PROJECT_STATUS_DELETED = 'PROJECT_STATUS_DELETED'
export const PROJECT_STATUS_EDITING = 'PROJECT_STATUS_EDITING'
export const PROJECT_STATUS_PUBLISH_APPLYING = 'PROJECT_STATUS_PUBLISH_APPLYING'
export const PROJECT_STATUS_REFLECTING = 'PROJECT_STATUS_REFLECTING'
export const PROJECT_STATUS_PUBLISHED = 'PROJECT_STATUS_PUBLISHED'
export const PROJECT_STATUS_DISPUBLISH_APPLYING = 'PROJECT_STATUS_DISPUBLISH_APPLYING'
export const PROJECT_STATUS_DISPUBLISHED = 'PROJECT_STATUS_DISPUBLISHED'

export const useIsMe = () => {
    const {pathname} = useLocation()

    const isMe = useMemo(() => {
        if (!!pathname.match(/^\/me\/project\/(.*)/)) {
            return true
        }
        return false
    }, [pathname])

    return isMe
}

export const useIsDraft = () => {
    const {pathname} = useLocation()

    const isDraft = useMemo(() => {
        if (!!pathname.match(/\/(.*)\/draft/)) {
            return true
        }
        return false
    }, [pathname])

    return isDraft
}

export const useIsPublic = () => {
    const {pathname} = useLocation()

    const isPublic = useMemo(() => {
        if (!!pathname.match(/^\/project\/(.*)/)) {
            return true
        }
        return false
    }, [pathname])

    return isPublic
}

export const useProjectStatus = id => {
    const project = useProject(id)

    const status = useMemo(() => {
        if(project && project.hidden) {
            return "hidden"
        }

        if(project && project.publishedDate) {
            return "published"
        }
    }, [id])
    return status
}

export const useProjectViewStatus = (id) => {
    const isDraft = useIsDraft()
    return useMemo(() => {
        if (isDraft) {
            switch (id) {
                case 'RMM10001':
                    return PROJECT_VIEW_STATUS_NOT_FOUND // PROJECT_STATUS_CREATED
                case 'RMM10002':
                    return PROJECT_VIEW_STATUS_NOT_FOUND // PROJECT_STATUS_DELETED
                case 'RMM10003':
                    return PROJECT_VIEW_STATUS_READY // PROJECT_STATUS_EDITING
                case 'RMM10004':
                    return PROJECT_VIEW_STATUS_READY // PROJECT_STATUS_PUBLISH_APPLYING
                case 'RMM10005':
                    return PROJECT_VIEW_STATUS_READY // PROJECT_STATUS_REFLECTING
                case 'RMM10006':
                    return PROJECT_VIEW_STATUS_READY // PROJECT_STATUS_PUBLISHED
                case 'RMM10007':
                    return PROJECT_VIEW_STATUS_READY // PROJECT_STATUS_EDITING
                case 'RMM10008':
                    return PROJECT_VIEW_STATUS_NOT_FOUND // PROJECT_STATUS_DISPUBLISH_APPLYING
                case 'RMM10009':
                    return PROJECT_VIEW_STATUS_NOT_FOUND // PROJECT_STATUS_DISPUBLISHED

                case 'RMM00404':
                    return PROJECT_VIEW_STATUS_NOT_FOUND
                case 'RMM0403':
                    return PROJECT_VIEW_STATUS_FORBIDDEN

                case 'RMM00001':
                case 'RMM00002':
                case 'RMM00003':
                case 'RMM00004':
                    return PROJECT_VIEW_STATUS_NOT_FOUND
                default:
                    return PROJECT_VIEW_STATUS_READY
            }
        }

        switch (id) {
            case 'RMM10001':
                return PROJECT_VIEW_STATUS_NOT_FOUND // PROJECT_STATUS_CREATED
            case 'RMM10002':
                return PROJECT_VIEW_STATUS_NOT_FOUND // PROJECT_STATUS_DELETED
            case 'RMM10003':
                return PROJECT_VIEW_STATUS_NOT_FOUND // PROJECT_STATUS_EDITING
            case 'RMM10004':
                return PROJECT_VIEW_STATUS_NOT_FOUND // PROJECT_STATUS_PUBLISH_APPLYING
            case 'RMM10005':
                return PROJECT_VIEW_STATUS_NOT_FOUND // PROJECT_STATUS_REFLECTING
            case 'RMM10006':
                return PROJECT_VIEW_STATUS_READY // PROJECT_STATUS_PUBLISHED
            case 'RMM10007':
                return PROJECT_VIEW_STATUS_READY // PROJECT_STATUS_EDITING
            case 'RMM10008':
                return PROJECT_VIEW_STATUS_NOT_FOUND // PROJECT_STATUS_DISPUBLISH_APPLYING
            case 'RMM10009':
                return PROJECT_VIEW_STATUS_NOT_FOUND // PROJECT_STATUS_DISPUBLISHED

            case 'RMM00404':
                return PROJECT_VIEW_STATUS_NOT_FOUND
            case 'RMM0403':
                return PROJECT_VIEW_STATUS_FORBIDDEN
            default:
                return PROJECT_VIEW_STATUS_READY
        }
    }, [id, isDraft])
}

export const useIsEditable = (id) => {
    const status = useProjectStatus(id)
    const isEditable = useMemo(() => {
        // FIXME これも本当はあってはいけない
        return status === PROJECT_STATUS_EDITING
    }, [status])
    return isEditable
}

export const useIsPublishedOnce = id => {
    const isPublishedOnce = useMemo(() => {
        switch (id) {
            case 'RMM10001':
                return false // PROJECT_STATUS_CREATED
            case 'RMM10002':
                return false // PROJECT_STATUS_DELETED
            case 'RMM10003':
                return false // PROJECT_STATUS_EDITING
            case 'RMM10004':
                return false // PROJECT_STATUS_PUBLISH_APPLYING
            case 'RMM10005':
                return false // PROJECT_STATUS_REFLECTING
            case 'RMM10006':
                return true // PROJECT_STATUS_PUBLISHED
            case 'RMM10007':
                return true // PROJECT_STATUS_EDITING
            case 'RMM10008':
                return true // PROJECT_STATUS_DISPUBLISH_APPLYING
            case 'RMM10009':
                return true // PROJECT_STATUS_DISPUBLISHED

            case 'RMM00001':
            case 'RMM00002':
            case 'RMM00003':
            case 'RMM00004':
            default:
                return true
        }
    }, [id])
    return isPublishedOnce
}

export const useIsEditing = id => {
    const currentUser = useCurrentUser()
    const target = currentUser && currentUser.project ? currentUser.project.find(pr => pr.ids.id === id) : null

    return target ? target.editing : false
}

export const useIsApplying = id => {
    const currentUser = useCurrentUser()
    const hasEditRole = useHasEditRole(id)
    const target = currentUser && currentUser.project ? currentUser.project.find(pr => pr.ids.id === id) : null

    return target ? hasEditRole && null == target.issueNo : false
}

export const useIsPublishApplicable = id => {
    const status = useProjectStatus(id)
    return useMemo(() => status === PROJECT_STATUS_EDITING, [status])
}

export const useIsDispublishApplicable = id => {
    const status = useProjectStatus(id)
    const isPublishedOnce = useIsPublishedOnce(id)
    return useMemo(() => (
        isPublishedOnce &&
        (status === PROJECT_STATUS_EDITING || status === PROJECT_STATUS_PUBLISHED)
    ), [status, isPublishedOnce])
}

export const useIsShowPublishApplyMenu = id => {
    const status = useProjectStatus(id)
    return useMemo(() => (
        status === PROJECT_STATUS_EDITING
        || status === PROJECT_STATUS_PUBLISH_APPLYING
        || status === PROJECT_STATUS_REFLECTING
    ), [status])
}

export const useIsShowDispublishApplyMenu = id => {
    const status = useProjectStatus(id)
    const isPublishedOnce = useIsPublishedOnce(id)
    return useMemo(() => (
        isPublishedOnce &&
        (
            status === PROJECT_STATUS_EDITING
            || status === PROJECT_STATUS_PUBLISHED
            || status === PROJECT_STATUS_DISPUBLISH_APPLYING
        )
    ), [isPublishedOnce, status])
}

export const useIsShowApplyMenu = id => {
    const isShowPublishApplyMenu = useIsShowPublishApplyMenu(id)
    const isShowDispublishApplyMenu = useIsShowDispublishApplyMenu(id)

    return useMemo(() => (
        isShowPublishApplyMenu
        || isShowDispublishApplyMenu
    ), [isShowDispublishApplyMenu, isShowPublishApplyMenu])
}

export const useIsPublishApplying = id => {
    const status = useProjectStatus(id)
    return useMemo(() => status === PROJECT_STATUS_PUBLISH_APPLYING, [status])
}

export const useIsDispublishApplying = id => {
    const status = useProjectStatus(id)
    return useMemo(() => status === PROJECT_STATUS_DISPUBLISH_APPLYING, [status])
}

export const useIsDeletable = id => {
    const status = useProjectStatus(id)
    const isPublisedOnce = useIsPublishedOnce(id)

    return useMemo(() => {
        if (isPublisedOnce) return false
        if (status === PROJECT_STATUS_CREATED || status === PROJECT_STATUS_EDITING) return true
        return false
    }, [isPublisedOnce, status])
}

export const useIsReflecting = id => {
    const status = useProjectStatus(id)
    return useMemo(() => status === PROJECT_STATUS_REFLECTING, [status])
}

export const useIsCancelable = id => {
    const isPublisedOnce = useIsPublishedOnce(id)
    const isEditable = useIsEditable(id)
    return useMemo(() => isPublisedOnce && isEditable, [isPublisedOnce, isEditable])
}

export const useIsDeleted = id => {
    const status = useProjectStatus(id)
    return useMemo(() => status === PROJECT_STATUS_DELETED || status === PROJECT_STATUS_DISPUBLISHED, [status])
}

export const useIsNotFound = id => {
    const viewStatus = useProjectViewStatus(id)
    return useMemo(() => viewStatus === PROJECT_VIEW_STATUS_NOT_FOUND, [viewStatus])
}

export const useIsForbidden = id => {
    const viewStatus = useProjectViewStatus(id)
    return useMemo(() => viewStatus === PROJECT_VIEW_STATUS_FORBIDDEN, [viewStatus])
}

export const useHasEditRole = id => {
    const {role} = useCurrentUser()
    const target = role.find(r => r.projectId === id)

    if (target) {
        return target.owner || target.writable
    }

    return false
}

export const useIsPublished = id => {
    const status = useProjectStatus(id)

    if("published" === status) {
        return true
    }

    return false
}

export const useHasDraft = id => {
    const project = useProject(id)

    return useMemo(() => (
        !!project && !!project.draftMetadata && !!project.draftMetadata.metadata && !!project.draftListdata && !!project.draftFileList && project.editing
    ), [project])
}