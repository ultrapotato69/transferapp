import {main} from "../mainPage.js";
import {getAccount} from "../accountPage.js";

export function createList(array) {
    const table = document.createElement('table')
    const caption = document.createElement('caption')
    table.classList.add('table')
    table.classList.add('table-striped')
    caption.textContent = 'List of accounts'
    caption.classList.add('caption-top')
    table.append(caption)
    table.append(getHeadTable(array))
    table.append(getBodyTable(array))
    main.append(table)
}

function getHeadTable(array) {
    const thead = document.createElement('thead')
    thead.classList.add('table-dark')
    const tr = document.createElement('tr')
    for (let key in array[0]) {
        if (key !== 'id') {
            let th = document.createElement('th')
            th.setAttribute('scope', 'col')
            th.innerText = key
            tr.append(th)
        }
    }
    thead.append(tr)
    return thead;
}

function getBodyTable(array) {
    const tbody = document.createElement('tbody')
    for (let i = 0; i < array.length; i++) {
        let tr = document.createElement('tr')
        for (let key in array[i]) {
            if (key !== 'id') {
                let td = document.createElement('td')
                if (key === 'client_name') {
                    let linkButton = document.createElement('button')
                    linkButton.classList.add('btn')
                    linkButton.classList.add('btn-outline-primary')
                    linkButton.onclick = getAccount(array[i]['id'], array)
                    linkButton.innerText = array[i][key]
                    td.append(linkButton)
                } else {
                    td.innerText = array[i][key] + '  '
                }
                tr.append(td)
            }
        }
        tbody.append(tr)
    }
    return tbody;
}