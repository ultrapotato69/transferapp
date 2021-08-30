import {deleteFromJson} from "../util/ajax.js";
import {printFirstPage} from "../mainPage.js";

export function infoAndDelete(account){
    const div = document.createElement('div')
    div.classList.add('input-group')
    div.classList.add('mb-3')
    for (let key in account) {
        let name = document.createElement('span')
        let b = document.createElement('b')
        let value = document.createElement('span')
        name.classList.add('input-group-text')
        value.classList.add('input-group-text')
        b.innerText = key + ': '
        name.append(b)
        div.append(name)
        value.innerText = account[key] + '  '
        div.append(value)
    }

    div.append(getDeleteAccountButton(account))
    return div
}



function getDeleteAccountButton(account) {
    const deleteButton = document.createElement('button')
    deleteButton.onclick = async () => {
        await deleteFromJson('/account/' + account.id)
        await printFirstPage()
    }
    deleteButton.innerText = 'delete account'
    deleteButton.classList.add('btn')
    deleteButton.classList.add('btn-dark')
    return deleteButton
}