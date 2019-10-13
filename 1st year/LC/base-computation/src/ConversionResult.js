import React, { Component } from 'react';
import './ConversionResult.css';

class ConversionResult extends Component {

    render() {
        const { base, value } = this.props;
        return (
            <div className="ConversionResult">
                <div className="base">{ `Base ${base}` }</div>
                <div className="number">{value}</div>
            </div>
        );
    }

}

export default ConversionResult;