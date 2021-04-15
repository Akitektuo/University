const baseSiteUrl = getSiteBaseUrl();

const setupEdit = (recipeId) =>
    document.querySelector("button.edit-recipe").addEventListener("click", () =>
        window.location.href = `${baseSiteUrl}/editRecipe.html?id=${recipeId}`);

const onLoad = async () => {
    const id = getParam("id");

    setupEdit(id);

    const recipes = await fetchFromUrl(`recipes.php?id=${id}`);
    const [{ title, author, typeId, typeName, description }] = recipes;

    const titleElement = document.querySelector("h2");
    const subTitleElement = document.querySelector("h3");
    const descriptionElement = document.querySelector("p");

    titleElement.innerHTML = `Recipe: ${title}`;
    subTitleElement.innerHTML = `By <i>${author}</i>, from category <i>${typeName}</i>`;
    descriptionElement.innerHTML = description;

    const deleteButton = document.querySelector("button.delete-recipe");
    deleteButton.disabled = false;
    deleteButton.addEventListener("click", async () => {
        await fetchFromUrl(`recipes.php?id=${id}`, {
            method: "DELETE",
            withoutResponse: true
        });

        window.location.href = `${baseSiteUrl}/recipes.html?type=${typeId}`;
    });
}

onLoad();

