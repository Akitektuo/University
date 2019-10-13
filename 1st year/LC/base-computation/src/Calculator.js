import React, { Component } from 'react';
import './Calculator.css';
import Conversions from './Conversions';
import Operations from './Operations';

class Calculator extends Component {

  render() {
    return (
      <div className="Calculator">
        <Conversions/>
        <Operations/>
      </div>
    );
  }
}

export default Calculator;
