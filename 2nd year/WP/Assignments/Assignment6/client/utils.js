const BASE_URL = "http://localhost:80/api/recipes";

const urlParams = new URLSearchParams(window.location.search);

const fetchFromUrl = async (url, options = {}) => {
    const { method, body, isText, withoutResponse } = options;

    const response = await fetch(`${BASE_URL}/${url}`, {
        method: method ?? "GET",
        headers: {
            "Token": document.cookie ?? ""
        },
        body: JSON.stringify(body)
    });

    if (withoutResponse) {
        return;
    }

    if (isText) {
        return await response.text();
    }
    return await response.json();
}

const getSiteBaseUrl = () => {
    const url = window.location.href;

    return url.substring(0, url.lastIndexOf("\/"));
}

const goBack = () => history.back();

const getParam = (key) => urlParams.get(key);