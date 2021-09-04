import {printFirstPage} from "../pages/mainPage.js";

export function goBackLink() {
    const breadcrumb = document.createElement('div')
    breadcrumb.classList.add('link-primary')

    const backLink = document.createElement('a')
    backLink.href="#"
    backLink.onclick = () => printFirstPage()
    backLink.innerText = '<< back to list'
    breadcrumb.append(backLink)
    return breadcrumb;
}