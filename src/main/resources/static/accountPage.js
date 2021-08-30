import {main} from "./mainPage.js";
import {clearElement} from "./util/util.js";
import {getJsonFromAddress} from "./util/ajax.js";
import {getTransferHistory} from "./components/transferHistoryList.js";
import {infoAndDelete} from "./components/accounInfo.js";
import {getTransferForm} from "./components/transferForm.js";
import {goBackLink} from "./components/goBackLink.js";


export function getAccount(id, accountList) {
    return async function () {
        let result = await getJsonFromAddress('/account/' + id)
        await createAccountPage(result, accountList)
    }
}

async function createAccountPage(account, accountList) {
    clearElement(main)
    const div = document.createElement('div')
    //div.classList.add('table-responsive')
    div.append(infoAndDelete(account))
    div.append(await getTransferHistory(account, accountList))
    div.append(await getTransferForm(account.id))
    div.append(goBackLink())
    main.append(div)
}

