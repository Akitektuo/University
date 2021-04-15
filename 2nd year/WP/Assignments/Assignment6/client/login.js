const handleLogin = async () => {
    const username = document.querySelector("input.username").value;
    const password = document.querySelector("input.password").value;

    try {
        const token = await fetchFromUrl("login.php", {
            method: "POST",
            body: { username, password },
            isText: true
        });

        if (!token) {
            return alert("An error occured, try again!");
        }

        goBack();
    } catch {
        alert("An error occured, try again!");
    }
}

const handleCreateAccount = () => {
    window.location.href = `${baseSiteUrl}/signup.html`;
}

document.querySelector("button.login").addEventListener("click", handleLogin);
document.querySelector("button.create-account").addEventListener("click", handleCreateAccount);