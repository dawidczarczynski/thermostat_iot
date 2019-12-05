const helpers = require('./helpers');

class Sensors {

  constructor() {
    this.sensors = [
      { id: 1, name: "First sensor" },
      { id: 2, name: "Second sensor" },
      { id: 3, name: "Third sensor" }
    ];
    this.currentSensor = 1;
    this.interval = null;
  }

  getSensorsList() { 
    return this.sensors;
  } 

  subscribeForTemperatureChanges(id, callback) {
    this.currentSensor = id;
    clearInterval(this.interval);

    this.interval = setInterval(() => { 
      const temperature = helpers.getRandomIntInclusive(17, 24);
      callback({ id, temperature });
    }, 2000);
  }

}

module.exports = Sensors;