import {useDispatch, useSelector} from 'react-redux'
import {useIsDraft, useIsPublic} from "./status";
import * as projectBookAction from "../../actions/project";
import {useToken} from "./preview";

export const useProjectBook = (id, history = null) => {
    const isDraft = useIsDraft()
    const isPublic = useIsPublic()
    const currentViewProject = useProjectView(id, history)
    const token = useToken()

    const currentUser = useSelector(({auth}) => {
        return auth.currentUser
    })

    if(isDraft && token) {
        return currentViewProject
            && currentViewProject.metadata
            && currentViewProject.metadata.sheets
            && currentViewProject.ids.id === id
            ? currentViewProject.metadata.sheets
            : []
    }

    if(isPublic) {
        if (currentViewProject
            && currentViewProject.metadata
            && currentViewProject.metadata.sheets
            && currentViewProject.ids.id === id
        ) {
            return currentViewProject.metadata.sheets
        } else {
            return null
        }
    }

    if (currentUser) {
        const project = currentUser.project;
        const target = project.find((prj) => prj.ids.id === id)

        // FIXME
        //  - ここで権限チェックをしたほうがよい？
        //  - とりあえず、現状ユーザーの情報にプロジェクトがある = 見る権限があるなので保留
        //  - あとコレとdraftのlistdata、fileListもあったほうがよい
        if(isDraft && target.draftMetadata && target.draftAggregate) {
            return target.draftMetadata.metadata
        }

        if (target && target.metadata && target.metadata.sheets) {
            return target.metadata.sheets
        } else if (target) {
            // nullを返すとNotFoundに飛ぶため、新規作成したばかりの空のプロジェクトのために空のオブジェクトを返す
            return {}
        }
    } else {
        return null
    }
}

export const useProject = (id) => {
    const isPublic = useIsPublic()
    const currentView = useProjectView(id);

    const project = useSelector(({auth}) => {
        if(isPublic) {
            return currentView
        }

        const currentUser = auth.currentUser

        let target = null
        if (currentUser) {
            target = currentUser.project.find(p => p.ids.id === id)
        }

        return target
    })

    return project
}

export const useProjectView = (id, history = null) => {
    const dispatch = useDispatch()

    const currentProjectView = useSelector(({projectBook}) => {
        return projectBook.currentProjectView ? projectBook.currentProjectView : null
    })
    const isPublic = useIsPublic()
    const isDraft = useIsDraft()
    const token = useToken()

    if(isDraft && token) {
        if(currentProjectView && currentProjectView.ids.id === id) {

        } else {
            dispatch(projectBookAction.accessProjectPreviewData(id, token, history))
        }
    }

    if (currentProjectView && currentProjectView.ids.id === id) {

    } else if(isPublic && null === token) {
        dispatch(projectBookAction.getProject(id, history))
    }

    return useCurrentProjectView()
}

export const useCurrentProjectView = () => {
    const currentProjectView = useSelector(({projectBook}) => {
        return projectBook.currentProjectView ? projectBook.currentProjectView : null
    })

    return currentProjectView
}

// FIXME useSelectorがリアルタイムに動作しないため、改善方法が判明しないと使わない
export const useIsLoading = (projectBook) => {
    const isLoading = useSelector(({projectBook}) => {
        return projectBook.isLoading
    }, [projectBook])

    const isPublic = useIsPublic()

    return isPublic && isLoading
}