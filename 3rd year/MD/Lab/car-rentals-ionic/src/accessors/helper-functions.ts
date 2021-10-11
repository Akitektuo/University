let token = "";

export const setToken = (tokenValue: string) => token = tokenValue;

export const hasToken = () => !!token;

export const post = <T>(url: string, body?: any) => new Promise<T>(async (resolve, reject) => {
    try {
        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}` 
            },
            body: JSON.stringify(body)
        });
        resolve(await response.json());
    } catch (exception) {
        reject(exception);
    }
});