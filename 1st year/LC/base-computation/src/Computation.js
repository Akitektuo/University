function convertIntoBase10(base, value) {
    
    let numberInBase10 = 0;
    if (base === 16) {
        if (value.includes('.')) {
            const parts = value.split(".");
            for (let i in parts[0]) {
                const numberAsString = parts[0][parts[0].length - i - 1];
                const number = numberAsString.toUpperCase() >= "A" ? getDecimalEquivalent(numberAsString) : Number(numberAsString);
                numberInBase10 += Math.pow(base, i) * number;
            }
            for (let i in parts[1]) {
                const numberAsString = parts[1][i];
                const number = numberAsString.toUpperCase() >= "A" ? getDecimalEquivalent(numberAsString) : Number(numberAsString);
                numberInBase10 += Math.pow(base, -(parseInt(i, 10) + 1)) * number;
            }
            return Math.round(numberInBase10 * 100) / 100;
        }
        for (let i in value) {
            const numberAsString = value[value.length - i - 1];
            const number = numberAsString.toUpperCase() >= "A" ? getDecimalEquivalent(numberAsString) : Number(numberAsString);
            numberInBase10 += Math.pow(base, i) * number;
        }
        return numberInBase10;
    }
    if (value.includes('.')) {
        const parts = value.split(".");
        for (let i in parts[0]) {
            const number = Number(value[value.length - i - 1]);
            numberInBase10 += Math.pow(base, i) * number;
        }
        for (let i in parts[1]) {
            const number = Number(parts[1][i]);
            numberInBase10 += Math.pow(base, -(parseInt(i, 10) + 1)) * number;
        }
        return Math.round(numberInBase10 * 100) / 100;
    }
    for (let i in value) {
        const number = Number(value[value.length - i - 1]);
        numberInBase10 += Math.pow(base, i) * number;
    }
    return numberInBase10;
}

function getDecimalEquivalent(hexaValue) {
    switch (hexaValue.toUpperCase()) {
        case 'A':
            return 10;
        case 'B':
            return 11;
        case 'C':
            return 12;
        case 'D':
            return 13;
        case 'E': 
            return 14;
        case 'F':
            return 15;
        default:
            return Number(hexaValue);
    }
}

function getHexaEquivalent(decimalValue) {
    switch (decimalValue) {
        case 10:
            return 'A';
        case 11:
            return 'B';
        case 12:
            return 'C';
        case 13:
            return 'D';
        case 14: 
            return 'E';
        case 15:
            return 'F';
        default:
            return decimalValue;
    }
}

function convertFromBase10(numberInBase10, intoBase) {
    if (intoBase === 10) {
        return numberInBase10;
    }
    if (intoBase === 16) {
        return convertBase10toBase16(numberInBase10);
    }
    
    let digits = [];
    while (numberInBase10 > 0) {
        digits.push(numberInBase10 % intoBase);
        numberInBase10 = Math.floor(numberInBase10 / intoBase);
    }
    let numberInBase = ""
    for (let i in digits) {
        numberInBase += digits[digits.length - i - 1];
    }
    return numberInBase;
}

function convertBase10toBase16(numberInBase10) {
    //repair this
    let digits = [];
    while (numberInBase10 > 0) {
        digits.push(getHexaEquivalent(numberInBase10 % 16));
        numberInBase10 = Math.floor(numberInBase10 / 16);
    }
    let numberInBase16 = ""
    for (let i in digits) {
        numberInBase16 += digits[digits.length - i - 1];
    }
    return numberInBase16;
}

export { convertIntoBase10, convertFromBase10 }