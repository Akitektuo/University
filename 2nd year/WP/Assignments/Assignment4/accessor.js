const fetchFromUrl = async (url) => {
    const response = await fetch(url);

    return await response.json();
}

const getAllCounties = async () => await fetchFromUrl("https://roloca.coldfuse.io/judete");

const getCititesByCounty = async (county) =>
    await fetchFromUrl(`https://roloca.coldfuse.io/orase/${county.auto}`);