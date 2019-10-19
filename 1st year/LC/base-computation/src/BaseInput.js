import React, { Component } from 'react';
import './BaseInput.css';

class BaseInput extends Component {

    state = {
        inputBase: 2,
        inputNumber: ""
    }

    componentDidUpdate() {
        const { base } = this.props

        const { inputBase } = this.state;

        if (base && inputBase !== base) {
            this.setState({
                inputBase: base,
                inputNumber: ""
            })
        }
    }

    render() {
        const { base } = this.props

        const { inputBase, inputNumber } = this.state;

        return (
            <div className="BaseInput">
                <div className="base">
                    Base
                    <input
                        className="input-base"
                        inputMode="numeric"
                        name="inputBase"
                        type="text"
                        pattern="[0-9]*"
                        value={ base ? base : inputBase }
                        onPaste={ this.preventPaste }
                        onChange={ (e) => { this.filterBase(e.target.value) } } />
                </div>
                <input
                    className="input-number" 
                    inputMode="numeric" 
                    name="inputNumber"
                    type="text" 
                    pattern="[0-9]*" 
                    value={ inputNumber }
                    onChange={ (e) => { this.filterNumber(e.target.value) } }
                    placeholder={ `Number in base ${base ? base : inputBase}` } 
                    onPaste={ this.filterNumber } />
            </div>
        );
    }

    preventPaste = (event) => {
        event.preventDefault();
    }

    getLastChar = (value) => {
        if (value.length < 1) {
            return "";
        }
        return value[value.length - 1];
    }

    filterBase = (value) => {
        const { base } = this.props
        if (base != undefined) {
            return;
        }

        const lastChar = this.getLastChar(value);
        if (lastChar < '0' || lastChar > '9') {
            return;
        }
        if ((lastChar === '0' || lastChar === '6') && value[0] === '1' && value.length === 2) {
            this.setState({
                inputBase: Number(value),
                inputNumber: ""
            });
            this.notifyConversion();
            return;
        }
        if (value.length > 1 && lastChar !== '0') {
            this.setState({
                inputBase: Number(lastChar),
                inputNumber: ""
            });
            this.notifyConversion();
            return;
        }
        return;
    }

    filterNumber = (value) => {
        const { inputBase } = this.state;
        if (value === "") {
            this.setState({
                inputNumber: ""
            });
            this.notifyConversion();
            return;
        }
        let usedDot = false;
        let dotIndex = 0;
        for (let i = 0; i < value.length; i++) {
            if ((inputBase === 16 && (value[i].toUpperCase() >= 'A' && value[i].toUpperCase() <= 'F'))) {
                if (usedDot && i > dotIndex + 2) {
                    value = value.substring(0, i);
                }
                continue;
            }
            if ((value[i] > `${inputBase === 16 ? 9 : inputBase - 1}` || value[i] < '0') && value[i] !== '.') {
                value = value.replace(value[i], '');
                i--;
            }
            if (value[i] === '.') {
                if (usedDot || i === 0) {
                    value = value.substring(0, i) + value.substring(i + 1, value.length);
                    i--;
                } else {
                    usedDot = true;
                    dotIndex = i;
                }
            }
            if (usedDot && i > dotIndex + 2) {
                value = value.substring(0, i);
            }
        }
        this.setState({
            inputNumber: value
        });
        this.notifyConversion();
    }

    notifyConversion = () => {
        setTimeout(() => {
            const { onNumberChange } = this.props;
            const { inputBase, inputNumber } = this.state;
            onNumberChange(inputBase, inputBase === 1 ? "" : inputNumber);
        }, 10)
    }
}

export default BaseInput;