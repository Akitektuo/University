const baseSiteUrl = getSiteBaseUrl();

const onLoad = async () => {
    const typeId = getParam("type");
    const recipes = await fetchFromUrl(`recipes.php?type=${typeId}`);

    document.querySelector("h3").innerHTML = `Recipes - <i>${recipes[0]?.typeName}</i>`;

    const recipesContainer = document.querySelector(".recipes-container");
    recipesContainer.innerHTML = recipes.map(({ id, title, author, description }) => `
        <div key=${id} class="recipe-item">
            <h3>${title}</h3>
            <sub>By <i>${author}</i></sub>
            <p>${description}</p>
        </div>
    `).join("");

    document.querySelectorAll(".recipe-item").forEach(element =>
        element.addEventListener("click", () =>
            window.location.href = `${baseSiteUrl}/recipe.html?id=${element.getAttribute("key")}`));
}

onLoad();