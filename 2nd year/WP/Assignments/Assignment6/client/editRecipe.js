const baseSiteUrl = getSiteBaseUrl();

const titleElement = document.querySelector("input.title");
const usernameElement = document.querySelector("input.username");
const recipeTypesElement = document.querySelector("select.recipe-types");
const decriptionElement = document.querySelector("textarea.description");

const setupSave = (id) =>
    document.querySelector("button.save-recipe").addEventListener("click", async () => {
        const body = {
            id,
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
            method: "PUT",
            body,
            withoutResponse: true
        });

        window.location.href = `${baseSiteUrl}/recipes.html?type=${body.typeId}`;
    });

const onLoad = async () => {
    const id = getParam("id");

    setupSave(id);

    const categoriesContainer = document.querySelector(".recipe-types");
    const types = await fetchFromUrl("types.php");

    categoriesContainer.innerHTML = types.map(({ id, name }) => `
        <option key="${id}" value="${id}">${name}</option>
    `).join("");

    const recipes = await fetchFromUrl(`recipes.php?id=${id}`);
    const [{ title, author, typeId, description }] = recipes;

    titleElement.value = title
    usernameElement.value = author
    recipeTypesElement.value = typeId
    decriptionElement.value = description
}

onLoad();
