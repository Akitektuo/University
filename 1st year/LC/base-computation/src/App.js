import React, { Component } from 'react';
import './App.css';
import Calculator from './Calculator';
import Test from './Test';

class App extends Component {
  state = {
    mainScreen: true
  }

  render() {
    const { mainScreen } = this.state 
    return (
      <div className="App">
        <div className="container">
          {mainScreen ? <Calculator/> : <Test/> }
        </div>
        <div className="footer">
          <div className="tag">
            Alex Copindean @ Akitektuo
          </div>
          <div className="button-test ripple" onClick={() => {this.setState({ mainScreen: !mainScreen })}}>
            {mainScreen ? "Go to tests" : "Go back"}
          </div>
        </div>
      </div>
    );
  }
}

export default App;
