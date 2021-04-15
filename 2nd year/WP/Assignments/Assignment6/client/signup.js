const handleLogin = () => {
    const username = document.querySelector("input.username").value;
    const password = document.querySelector("input.password").value;

    try {
        const token = await fetchFromUrl("login", {
            method: "POST",
            body: { username, password }
        });

        document.cookie = token;
        goBack();
    } catch {
        alert("An error occured, try again!");
    }
}

const handleCreateAccount = () => {
    const username = document.querySelector("input.username").value;
    const password = document.querySelector("input.password").value;

    if (!username || !password) {
        alert("Username or password is empty!");
        return;
    }

    try {
        await fetchFromUrl("signup.php", {
            method: "POST",
            body: { username, password },
            isText: true
        });

        goBack();
    } catch {
        alert("An error occured, try again!");
    }    
}

document.querySelector("button.login").addEventListener("click", handleLogin);
document.querySelector("button.create-account").addEventListener("click", handleCreateAccount);