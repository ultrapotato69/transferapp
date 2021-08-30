export async function getJsonFromAddress(url) {
    let response = await fetch(url)
    if (response.ok) {
        return await response.json()
    } else {
        return "Ошибка HTTP: " + response.status
    }
}

export async function postJson(object, url) {
    let response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(object),
    });
    return response
}

export async function deleteFromJson(url) {
    let response = await fetch(url, {
        method: 'DELETE',
    })
    return response
}