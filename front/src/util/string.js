export const trim = (text) => {
    return text && text.length > 200 ? (text).slice(0, 200)+"…" : text
}