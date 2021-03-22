const stripText = (text) => text.trim()
    .toLowerCase()
    .normalize("NFD")
    .replace(/[\u0300-\u036f]/g, "");

String.prototype.containsIgnoringCase = function (other) {
    return stripText(this).includes(stripText(other));
}