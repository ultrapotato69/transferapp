import {getAccount} from "./account.js";
import {main, printFirstPage} from "./main.js";
import {postJson} from "./util.js";


export function createList(array) {
    const table = document.createElement('table')
    const caption = document.createElement('caption')
    caption.textContent = 'List of accounts'
    const thead = getHeadTable(array);
    const tbody = getBodyTable(array);
    table.append(caption)
    table.append(thead)
    table.append(tbody)
    main.append(table)
}

function getHeadTable(array) {
    const thead = document.createElement('thead')
    for (let key in array[0]) {
        if (key !== 'id') {
            let th = document.createElement('th')
            th.innerText = key
            thead.append(th)
        }
    }
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
                    let refButton = document.createElement('button')
                    refButton.className = 'ref'
                    refButton.onclick = getAccount(array[i]['id'], array)
                    refButton.innerText = array[i][key]
                    td.append(refButton)
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

export function createNewAccountForm() {
    const div = document.createElement('div')
    const b = document.createElement('b')
    b.textContent = 'Add new account: '
    const nameInput = document.createElement('input')
    nameInput.type = 'text'
    nameInput.placeholder = 'name'
    const amountInput = document.createElement('input')
    amountInput.type = 'text'
    amountInput.placeholder = 'amount'
    const submitButton = document.createElement('button')
    submitButton.onclick = () => sendNewAccount(nameInput.value, amountInput.value)
    submitButton.innerText = 'submit'
    div.append(document.createElement('br'))
    div.append(b)
    div.append(nameInput)
    div.append(amountInput)
    div.append(submitButton)
    main.append(div)
}

function sendNewAccount(name, amount) {
    let account = {
        id: null,
        client_name: name,
        balance: amount
    }
    postJson(account, '/account')
    printFirstPage()
}