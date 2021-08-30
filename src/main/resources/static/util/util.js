export function clearElement(elem) {
    while (elem.firstChild) {
        elem.removeChild(elem.lastChild)
    }
}

