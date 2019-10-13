import React, { Component } from 'react';
import './Conversions.css';
import ConversionField from './ConversionField';
import { convertIntoBase10, convertFromBase10 } from './Computation';
import ConversionResult from './ConversionResult';

class Conversions extends Component {

    constructor(props) {
        super(props)

        this.state = {
            base2: "",
            base3: "",
            base4: "",
            base5: "",
            base6: "",
            base7: "",
            base8: "",
            base9: "",
            base10: "",
            base16: ""
        }
    }
    
    render() {
        const { 
            base2,
            base3,
            base4,
            base5,
            base6,
            base7,
            base8,
            base9,
            base10,
            base16
        } = this.state;

        return (
            <div className="Conversions">
                <div className="title">Conversions</div>
                <ConversionField
                    onConversionRequest={ this.computeConversion } />
                <ConversionResult
                    base={ 2 }
                    value={ base2 } />
                <ConversionResult
                    base={ 3 }
                    value={ base3 } />
                <ConversionResult
                    base={ 4 }
                    value={ base4 } />
                <ConversionResult
                    base={ 5 }
                    value={ base5 } />
                <ConversionResult
                    base={ 6 }
                    value={ base6 } />
                <ConversionResult
                    base={ 7 }
                    value={ base7 } />
                <ConversionResult
                    base={ 8 }
                    value={ base8 } />
                <ConversionResult
                    base={ 9 }
                    value={ base9 } />
                <ConversionResult
                    base={ 10 }
                    value={ base10 } />
                <ConversionResult
                    base={ 16 }
                    value={ base16 } />
            </div>
        );
    }

    computeConversion = (base, value) => {
        if (value === "") {
            this.setState({
                base2: "",
                base3: "",
                base4: "",
                base5: "",
                base6: "",
                base7: "",
                base8: "",
                base9: "",
                base10: "",
                base16: ""
            });
            return;
        }
        let numberInBase10 = base === 10 ? value : convertIntoBase10(base, value);
        this.setState({
            base2: base === 2 ? value : convertFromBase10(numberInBase10, 2),
            base3: base === 3 ? value : convertFromBase10(numberInBase10, 3),
            base4: base === 4 ? value : convertFromBase10(numberInBase10, 4),
            base5: base === 5 ? value : convertFromBase10(numberInBase10, 5),
            base6: base === 6 ? value : convertFromBase10(numberInBase10, 6),
            base7: base === 7 ? value : convertFromBase10(numberInBase10, 7),
            base8: base === 8 ? value : convertFromBase10(numberInBase10, 8),
            base9: base === 9 ? value : convertFromBase10(numberInBase10, 9),
            base10: base === 10 ? value : numberInBase10,
            base16: base === 16 ? value : convertFromBase10(numberInBase10, 16)
        });
    }

}

export default Conversions;