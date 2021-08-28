import {clearElement, getJsonFromAddress} from "./util.js";
import {createList, createNewAccountForm} from "./accountList.js";

export const main = document.getElementById('main');

(async () => { await printFirstPage() })()

export async function printFirstPage() {
    clearElement(main)
    let accountList = await getJsonFromAddress('/account')
    createList(accountList)
    createNewAccountForm()
}