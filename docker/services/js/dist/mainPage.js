import {clearElement} from "./util/util.js";
import {createList} from "./components/accountsList.js";
import {createNewAccountForm} from "./components/newAccountForm.js";
import {getJsonFromAddress} from "./util/ajax.js";

export const main = document.getElementById('main');

(async () => { await printFirstPage() })()

export async function printFirstPage() {
    clearElement(main)
    let accountList = await getJsonFromAddress('/account')
    createList(accountList)
    createNewAccountForm()
}