import React, { Component } from 'react';
import './Operations.css';
import BaseInput from './BaseInput';
import NumberResult from './NumberResult';
import { addNumbers, substractNumbers, mulyiplyNumbers, divideNumbers } from './Computation';

class Operations extends Component {

  state = {
    base: 2,
    result: 0,
    operator: '+'
  }

  number1 = 0
  number2 = 0

  render() {
    const { base, operator, result } = this.state

    return (
      <div className="Operations">
        <div className="title">Operations</div>
        <BaseInput
          onNumberChange={ (base, value) => this.onNumberChange(base, value, 1) } />
        <div className="operation">
          <div className="operator">Operator:</div>
          <input
            className="input-operator"
            name="inputOperator"
            type="text"
            value={ operator }
            onPaste={ this.preventPaste }
            onChange={ (e) => { this.filterOperator(e.target.value) } } />
        </div>
        <BaseInput
          base={ base }
          onNumberChange={ (base, value) => this.onNumberChange(base, value, 2) } />
        <NumberResult
          base={ base }
          value={ result } />
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

  filterOperator = (value) => {
    const lastChar = this.getLastChar(value);
    if (lastChar === '+' || lastChar === '-' || lastChar === '*' || lastChar === '/') {
      this.setState({
        operator: lastChar
      });
      this.doOperations();
    }
  }

  onNumberChange = (base, value, which) => {
    if (value === '') {
      value = 0
    }
    switch (which) {
      case 1:
        this.number1 = value;
        break;
      case 2:
        this.number2 = value;
        break;
    }
    if (base !== this.state.base) {
      this.setState({
        base,
        result: 0
      })
    }
    this.doOperations();
  }

  doOperations() {
    setTimeout(() => {
      if (this.number1 === 0 || this.number2 === 0) {
        return;
      }
      const { base, operator } = this.state;
      let result = 0;
      switch (operator) {
        case '+':
          result = addNumbers(this.number1, this.number2, base)
          break;
        case '-':
          result = substractNumbers(this.number1, this.number2, base)
          break;
        case '*':
          result = mulyiplyNumbers(this.number1, this.number2, base)
          break;
        case "/":
          result = divideNumbers(this.number1, this.number2, base)
          break;
      }
      this.setState({
        result
      })
    }, 10)
  }
}

export default Operations;