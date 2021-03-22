const main = async () => {
    const counties = await getAllCounties();

    const selectedOptions = document.querySelector(".selected-options");
    let selectedCounty = "None";
    let selectedCity = "None";

    const countyComboBox = ComboBox(".combo-box.counties", counties);
    const citiesComboBox = ComboBox(".combo-box.cities");

    const renderCitiesOptions = async (county) =>
        citiesComboBox.renderOptions(await getCititesByCounty(county));

    const renderSelectedOptions = () =>
        selectedOptions.innerHTML = `${selectedCounty} - ${selectedCity}`;

    countyComboBox.renderOptions();
    countyComboBox.setSelectionListener(county => {
        renderCitiesOptions(county);
        selectedCounty = county.nume;
        selectedCity = "None";
        renderSelectedOptions();
    });

    citiesComboBox.setSelectionListener(city => {
        selectedCity = city.nume;
        renderSelectedOptions();
    });
}

main();