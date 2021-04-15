const baseSiteUrl = getSiteBaseUrl();

const onLoad = async () => {
    const categoriesContainer = document.querySelector(".categories-container");
    const types = await fetchFromUrl("types.php");

    categoriesContainer.innerHTML = types.map(({ id, name, image }) => `
        <div key=${id} class="category-element">
            <img src="${image}">
            <span>${name}<span>
        </div>
    `).join("");

    document.querySelectorAll(".category-element").forEach(element =>
        element.addEventListener("click", () =>
            window.location.href = `${baseSiteUrl}/recipes.html?type=${element.getAttribute("key")}`));
}

onLoad();

document.querySelector("button.add-recipe").addEventListener("click", () => 
    window.location.href = `${baseSiteUrl}/addRecipe.html`);

