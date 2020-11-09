export const useToken = () => {
    const params = window.location.search;
    const regex = new RegExp('[\\?&]token=([^&#]*)')
    const results = regex.exec(params)

    if (null === results) {
        return null
    }

    return results[1].replace(/\+/g, ' ')
}