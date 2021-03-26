const renderOption = (option, index) => `<div key=${index} class="option">${option.nume}</div>`;

const DEFAULT_VALUE = "Select a value";

const ComboBox = (containerSelector, initialData) => {
    const container = document.querySelector(containerSelector);
    const selectedOption = container.querySelector(".selected-option");
    const optionList = container.querySelector(".option-list");
    const search = container.querySelector("input");
    const disabledOverlay = container.querySelector(".disabled");
    let data = initialData;
    let options;
    let disabled = true;

    let selectionListener;

    const setSelectionListener = (listener) => selectionListener = listener;

    const enable = () => {
        disabled = false;
        disabledOverlay.classList.remove("active");
    }

    const isFocused = () => container.classList.contains("focused");

    const setFocused = (focused) => {
        if (disabled) {
            return;
        }
        
        if (focused) {
            container.classList.add("focused");
            search.focus();
            return;
        }
        container.classList.remove("focused");
        clearSearch();
    }

    const setSelectionOptionText = (text) => {
        if (text) {
            selectedOption.classList.add("selected");
            selectedOption.innerHTML = text;
            return;
        }
        selectedOption.classList.remove("selected");
        selectedOption.innerHTML = DEFAULT_VALUE;
    }

    const setSelectionInOptions = (option) => {
        options.filter(option => option.classList.contains("selected"))
            .forEach(option => option.classList.remove("selected"));
        option.classList.add("selected");
    }

    const setOptionSelectionListener = (option) => option.addEventListener("click", e => {
        e.stopPropagation();

        setSelectionOptionText(option.innerHTML);
        setSelectionInOptions(option);

        setFocused(false);
        selectionListener?.(data[option.getAttribute("key")]);
    });

    const setOptionSelectionListeners = () => options?.forEach(setOptionSelectionListener);

    const filterOptions = ({ target: { value: searchText } }) =>
        options.forEach(option => option.style.display =
            option.innerHTML.containsIgnoringCase(searchText) ? "block" : "none");

    const setSearchListener = () => search.addEventListener("input", e => filterOptions(e, options));

    const setOptions = () => {
        [...options] = container.querySelectorAll(".option");
        setOptionSelectionListeners();
        setSearchListener();
    }

    const clearSearch = () => {
        search.value = "";
        options?.forEach(option => option.style.display = "block");
    }

    const setOutsideClick = () => window.addEventListener("click", e => {
        if (!container.contains(e.target)) {
            setFocused(false);
        }
    });

    const setFocusListener = () => container.addEventListener("click", () => {
        if (isFocused()) {
            return;
        }

        setFocused(true);
        setOutsideClick();
    });

    const renderOptions = (newData = undefined) => {
        newData && (data = newData);
        const options = data.map(renderOption).join("");
    
        optionList.innerHTML = options;
        setOptions();
        setSelectionOptionText("");
        enable();
    }

    setFocusListener();

    return {
        renderOptions,
        setSelectionListener
    };
}