const getAllCounties = async () => {
    const response = await fetch("https://roloca.coldfuse.io/judete");

    return await response.json();
}

const getCititesByCounty = async (county) => {
    const response = await fetch(`https://roloca.coldfuse.io/orase/${county.auto}`);

    return await response.json();
}

const setFocusListener = (comboBox) => {
    const search = comboBox.querySelector("input");
    const [...options] = comboBox.querySelectorAll(".option");

    comboBox.addEventListener("click", e => {
        e.stopPropagation();

        const { classList } = comboBox;
        if (classList.contains("focused")) {
            return;
        }

        classList.add("focused");
        search.focus();

        window.addEventListener("click", () => {
            search.value = "";
            options.forEach(option => option.style.display = "block");
            classList.remove("focused");
        });
    });
}

const setComboBoxListeners = () => {
    document.querySelectorAll(".combo-box")
        .forEach(setFocusListener);
}

const populateCounties = (counties, optionList) => {
    const countiesOptionList = optionList ?? document.querySelector(".combo-box.counties .option-list");
    const countiesOptions = counties.map((county, index) => `<div key=${index} class="option">${county.nume}</div>`)
        .join("");

    countiesOptionList.innerHTML = countiesOptions;
}

const setSelectionListener = (container, options, option, selectedOption, search) =>
    option.addEventListener("click", e => {
        e.stopPropagation();

        selectedOption.classList.add("selected");
        selectedOption.innerHTML = option.innerHTML;
        options.filter(option => option.classList.contains("selected"))
            .forEach(option => option.classList.remove("selected"));
        option.classList.add("selected");
        container.classList.remove("focused");
        search.value = "";
        options.forEach(option => option.style.display = "block");
    });

const stripText = (text) => text.trim()
    .toLowerCase()
    .normalize("NFD")
    .replace(/[\u0300-\u036f]/g, "");

const setCountiesListeners = () => {
    const container = document.querySelector(".combo-box.counties");
    const selectedOption = container.querySelector(".selected-option");
    const [...options] = container.querySelectorAll(".option");
    const search = container.querySelector("input");

    options.forEach(option => setSelectionListener(container, options, option, selectedOption, search));
    search.addEventListener("input", e => {
        const searchText = stripText(e.target.value);

        options.forEach(option => option.style.display = stripText(option.innerHTML).includes(searchText) ? "block" : "none");
    });
}

const main = async () => {
    const counties = await getAllCounties();
    // const cititesByCounty = await getCititesByCounty(counties.find(county => county.auto === "CJ"));
    
    // console.log(counties);
    // console.log(cititesByCounty);
    populateCounties(counties);
    setComboBoxListeners();
    setCountiesListeners(counties);
}

main();