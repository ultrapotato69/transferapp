import {main, printFirstPage} from "../mainPage.js";
import {postJson} from "../util/ajax.js";

export function createNewAccountForm() {
    const div = document.createElement('div')
    div.classList.add('input-group')
    div.classList.add('mb-3')
    const span = document.createElement('span')
    span.classList.add('input-group-text')
    span.textContent = 'Add new account: '
    const nameInput = document.createElement('input')
    nameInput.type = 'text'
    nameInput.placeholder = 'name'
    nameInput.classList.add('form-control')
    const amountInput = document.createElement('input')
    amountInput.type = 'text'
    amountInput.placeholder = 'amount'
    amountInput.classList.add('form-control')
    const submitButton = document.createElement('button')
    submitButton.classList.add('btn')
    submitButton.classList.add('btn-dark')
    submitButton.onclick = () => sendNewAccount(nameInput.value, amountInput.value)
    submitButton.innerText = 'submit'
    div.append(document.createElement('br'))
    div.append(span)
    div.append(nameInput)
    div.append(amountInput)
    div.append(submitButton)
    main.append(div)
}

async function sendNewAccount(name, amount) {
    let account = {
        id: null,
        client_name: name,
        balance: amount
    }
    await postJson(account, '/account')
    await printFirstPage()
}