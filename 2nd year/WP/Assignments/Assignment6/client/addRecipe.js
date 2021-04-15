const baseSiteUrl = getSiteBaseUrl();

const onLoad = async () => {
    const categoriesContainer = document.querySelector(".recipe-types");
    const types = await fetchFromUrl("types.php");

    categoriesContainer.innerHTML = types.map(({ id, name }) => `
        <option key="${id}" value="${id}">${name}</option>
    `).join("");
}

onLoad();

document.querySelector("button.add-recipe").addEventListener("click", async () => {
    const titleElement = document.querySelector("input.title");
    const usernameElement = document.querySelector("input.username");
    const recipeTypesElement = document.querySelector("select.recipe-types");
    const decriptionElement = document.querySelector("textarea.description");

    const body = {
        title: titleElement.value,
        author: usernameElement.value,
        typeId: recipeTypesElement.value,
        description: decriptionElement.value
    }
    
    if (!body.title || !body.author || !body.typeId || !body.description) {
        alert("Fill in all fields!");
        return;
    }

    await fetchFromUrl("recipes.php", {
        method: "POST",
        body,
        withoutResponse: true
    });

    window.location.href = `${baseSiteUrl}/index.html`;
});
