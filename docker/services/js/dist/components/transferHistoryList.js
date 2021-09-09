import {getJsonFromAddress} from "../util/ajax.js";

export async function getTransferHistory(account, accountList) {
    let transferHistory = await getJsonFromAddress('/transfer/history/' + account.id)
    const table = document.createElement('table')
    table.classList.add('table')
    table.classList.add('table-striped')
    const caption = document.createElement('caption')
    caption.textContent = 'Transfer history:'
    caption.classList.add('caption-top')
    table.append(caption)
    table.append(getHeadTable(transferHistory))
    table.append(getBodyTable(transferHistory, accountList, account))
    return table
}

function getHeadTable(transferHistory) {
    const thead = document.createElement('thead')
    thead.classList.add('table-dark')
    const tr = document.createElement('tr')

    for (let key in transferHistory[0]) {
        if (key !== 'transfer_id') {
            let th = document.createElement('th')
            th.innerText = key.replace('_id', '')
            tr.append(th)
        }
    }
    thead.append(tr)
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
                    console.log(transferHistory[i][key])
                    if (transferHistory[i][key] === 0) {
                        td.innerText = 'deleted'
                    } else if (transferHistory[i][key] === account.id) {
                        td.innerText = 'You  '
                    } else {
                        for (let j = 0; j < accountList.length; j++) {
                            if (accountList[j].id === transferHistory[i][key]) {
                                td.innerText = accountList[j].client_name + '  '
                            }
                        }
                    }
                } else if (key === 'transfer_date') {
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