import React, { Component } from 'react';
import './NumberResult.css';

class NumberResult extends Component {

    render() {
        const { base, value } = this.props;
        return (
            <div className="NumberResult">
                <div className="base">{ `Base ${base}` }</div>
                <div className="number">{value}</div>
            </div>
        );
    }

}

export default NumberResult;