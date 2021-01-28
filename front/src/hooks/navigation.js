import {useCallback, useEffect, useState} from 'react'

const defaultSidebarStatic = localStorage.getItem('sidebarStatic') === 'true'
const defaultSidebarOpened = window.innerWidth > 768 ? true : false

export const useNavigation = () => {

    const [sidebarStatic, setSidebarStatic] = useState(defaultSidebarStatic)
    const [sidebarOpened, setSidebarOpened] = useState(defaultSidebarOpened)
    const [activeItem, setActiveItem] = useState(
        JSON.parse(localStorage.getItem('sidebarStatic')) ? window.location.pathname : null
    )

    const closeSidebar = useCallback(() => {
        setSidebarStatic(false)
        setSidebarOpened(false)
        setActiveItem(null)
    }, [])

    const openSidebar = useCallback(activeItem => {
        setSidebarStatic(true)
        setSidebarOpened(true)
        setActiveItem(activeItem)
    }, [])

    const toggleSidebar = useCallback(() => {
        if (sidebarOpened) {
            closeSidebar()
        } else {
            openSidebar()
        }
    }, [closeSidebar, openSidebar, sidebarOpened])

    const handleSwipe = useCallback((event) => {
        if (!('ontouchstart' in window)) return

        switch (event.direction) {
            case 4:
                openSidebar()
                return
            case 2:
                closeSidebar()
                return
            default:
        }
    }, [closeSidebar, openSidebar])

    useEffect(() => {
        const handleResize = () => {
            if (sidebarStatic || window.innerWidth > 768) return
            closeSidebar()
        }

        handleResize()
        window.addEventListener('resize', handleResize)

        const cleanup = () => {
            window.removeEventListener('resize', handleResize)
        }
        return cleanup
    }, [closeSidebar, sidebarStatic, toggleSidebar])

    useEffect(() => localStorage.setItem('sidebarOpened', sidebarOpened), [sidebarOpened])
    useEffect(() => localStorage.setItem('sidebarStatic', sidebarStatic), [sidebarStatic])

    const onHelp = useCallback((e, helpUrl) => {
        e.preventDefault()
        window.open(helpUrl)
    }, [])

    return {
        sidebarOpened,
        sidebarStatic,
        activeItem,
        setSidebarOpened,
        openSidebar,
        closeSidebar,
        toggleSidebar,
        setActiveItem,
        handleSwipe,
        onHelp,
    }
}