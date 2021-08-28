import {main, printFirstPage} from "./main.js";
import {clearElement, deleteFromJson, getJsonFromAddress, postJson} from "./util.js";

export function getAccount(id, accountList) {
    return async function () {
        let result = await getJsonFromAddress('/account/' + id)
        await createAccountPage(result, accountList)
    }
}

async function createAccountPage(account, accountList) {
    clearElement(main)
    const div = document.createElement('div')
    const accountInfo = printAccountInfo(account)
    const transferHistory = await getTransferHistory(account, accountList)
    const transferForm = await getTransferForm(account.id)
    const deleteButton = getDeleteAccountButton(account)
    const backRefButton = goBackRef();
    div.append(accountInfo)
    div.append(document.createElement('br'))
    div.append(transferHistory)
    div.append(document.createElement('br'))
    div.append(transferForm)
    div.append(document.createElement('br'))
    div.append(deleteButton)
    div.append(document.createElement('br'))
    div.append(document.createElement('br'))
    div.append(backRefButton)
    main.append(div)
}

function printAccountInfo(account) {
    const table = document.createElement('table')
    const tbody = document.createElement('tbody')
    const thead = document.createElement('thead')
    for (let key in account) {
        let th = document.createElement('th')
        let td = document.createElement('td')
        th.innerText = key
        thead.append(th)
        td.innerText = account[key] + '  '
        tbody.append(td)
    }
    table.append(tbody)
    table.append(thead)
    return table
}

function getDeleteAccountButton(account) {
    const deleteButton = document.createElement('button')
    deleteButton.onclick = async () => {
        await deleteFromJson('/account/' + account.id)
        printFirstPage()
    }
    deleteButton.innerText = 'delete account'
    return deleteButton
}

async function getTransferHistory(account, accountList) {
    let transferHistory = await getJsonFromAddress('/transfer/history/' + account.id)
    const table = document.createElement('table')
    const caption = document.createElement('caption')
    caption.textContent = 'Transfer history:'
    const thead = getHeadTable(transferHistory);
    const tbody = getBodyTable(transferHistory, accountList, account);
    table.append(caption)
    table.append(thead)
    table.append(tbody)
    return table
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

    for (let i = 0; i < array.length; i++) {
        if(array[i].id !== thisId){
            let option = document.createElement('option')
            option.innerText = array[i].client_name
            select.append(option)
        }
    }
    return select
}
async function getTransferForm(thisId) {
    let result = await getJsonFromAddress('/account')
    const div = document.createElement('div')
    const b = document.createElement('b')
    b.textContent = 'transfer from this account to : '
    const amountInput = document.createElement('input')
    amountInput.type = 'text'
    amountInput.placeholder = 'amount'
    const commentInput = document.createElement('input')
    commentInput.type = 'text'
    commentInput.placeholder = 'comment'
    const select = printSelect(thisId, result)
    const submitButton = document.createElement('button')
    submitButton.onclick = () => doTransfer(thisId, result, select.value, amountInput.value, commentInput.value);
    submitButton.innerText = 'submit'
    div.append(b)
    div.append(document.createElement('br'))
    div.append(amountInput)
    div.append(commentInput)
    div.append(select)
    div.append(submitButton)
    return div
}

function goBackRef() {
    const backRefButton = document.createElement('BUTTON')
    backRefButton.onclick = () => printFirstPage()
    backRefButton.innerText = "<< back to list"
    backRefButton.className = 'ref'
    return backRefButton;
}

function getHeadTable(transferHistory) {
    const thead = document.createElement('thead')
    for (let key in transferHistory[0]) {
        if (key !== 'transfer_id') {
            let th = document.createElement('th')
            th.innerText = key.replace('_id', '')
            thead.append(th)
        }
    }
    return thead;
}

function getBodyTable(transferHistory, accountList, account) {

    const tbody = document.createElement('tbody')
    for (let i = 0; i < transferHistory.length; i++) {
        let tr = document.createElement('tr')
        for (let key in transferHistory[i]) {
            if (key !== 'transfer_id') {
                let td = document.createElement('td')
                if (key === 'from_account_id' || key === 'to_account_id') {
                    if (transferHistory[i][key] === account.id) {
                        td.innerText = 'You  '
                    } else {
                        for (let j = 0; j < accountList.length; j++) {
                            if(accountList[j].id === transferHistory[i][key]) {
                                console.log(accountList[j].id + " " + transferHistory[i][key])
                                td.innerText = accountList[j].client_name + '  '
                            }
                        }
                    }
                } else if(key === 'transfer_date') {
                    td.innerText = transferHistory[i][key].slice(0, 19).replace("T", " ")
                } else {
                    td.innerText = transferHistory[i][key] + '  '
                }
                tr.append(td)
            }
        }
        tbody.append(tr)
    }
    return tbody;
}