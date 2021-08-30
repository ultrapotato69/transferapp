import {getJsonFromAddress, postJson} from "../util/ajax.js";
import {printFirstPage} from "../mainPage.js";



export async function getTransferForm(thisId) {
    let result = await getJsonFromAddress('/account')
    const div = document.createElement('div')
    div.classList.add('input-group')
    div.classList.add('mb-3')
    const span = document.createElement('span')
    span.textContent = 'transfer from this account to : '
    span.classList.add('input-group-text')
    const amountInput = document.createElement('input')
    amountInput.type = 'text'
    amountInput.placeholder = 'amount'
    amountInput.classList.add('form-control')
    const commentInput = document.createElement('input')
    commentInput.type = 'text'
    commentInput.placeholder = 'comment'
    commentInput.classList.add('form-control')
    const submitButton = document.createElement('button')
    const select = printSelect(thisId, result)
    submitButton.onclick = () => doTransfer(thisId, result, select.value, amountInput.value, commentInput.value);
    submitButton.innerText = 'submit'
    submitButton.classList.add('btn')
    submitButton.classList.add('btn-dark')
    div.append(span)
    div.append(document.createElement('br'))
    div.append(amountInput)
    div.append(commentInput)
    div.append(select)
    div.append(submitButton)
    return div
}

async function doTransfer(fromId, array, client_name, amount, comment) {
    let toAccountID
    for (let i = 0; i < array.length; i++) {
        if (client_name == array[i].client_name) {
            toAccountID = array[i].id
        }
    }
    const transfer = {
        id: null,
        from_account_id: fromId,
        to_account_id: toAccountID,
        amount: amount,
        comment: comment,
        transfer_date: null
    }
    await postJson(transfer, '/transfer')
    await printFirstPage()
}

function printSelect(thisId, array) {
    const select = document.createElement('select')
    select.classList.add('form-select')

    for (let i = 0; i < array.length; i++) {
        if (array[i].id !== thisId) {
            let option = document.createElement('option')
            option.innerText = array[i].client_name
            select.append(option)
        }
    }
    return select
}