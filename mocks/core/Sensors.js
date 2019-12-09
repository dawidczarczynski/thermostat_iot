const { Readable } = require('stream');
const SensorsFilter = require('./SensorFilter');
const helpers = require('../helpers');
const sensorsList = require('../data/sensors.json');

const MIN_TEMPERATURE = 15;
const MAX_TEMPERATURE = 25;
const EMIT_INTERVAL_TIME = 2000;

class Sensors {

  constructor() {
    if (Sensors.instance) {
      return Sensors.instance;
    }
    this.sensors = sensorsList;
    this.subscribedSensors = new Set();
    this.temperatureInterval = null;
    this.temperatureStream = new Readable({ 
      objectMode: true,
      read: () => true
    });

    Sensors.instance = this;
  }

  getSensorsList() { 
    return this.sensors;
  } 

  addSensor(sensor) {
    this.sensors.push(sensor);
  }

  startGeneratingTemperatureValues() {
    if (this.temperatureInterval) {
      return;
    }

    this.temperatureInterval = setInterval(() => {
      this.sensors
        .map(sensor => sensor.id)
        .filter(id => this.subscribedSensors.has(id))
        .forEach(id => {
          const temperature = helpers.getRandomIntInclusive(MIN_TEMPERATURE, MAX_TEMPERATURE);
          this.temperatureStream.push({ id, temperature });
        });
    }, EMIT_INTERVAL_TIME);

    console.log('Temperature generating started');
  }

  stopGeneratingTemperatureValues() {
    clearInterval(this.temperatureInterval);
    this.temperatureInterval = null;
    
    console.log('Temperature generating stopped');
  }

  subscribeForTemperatureChanges(sensorId) {
    this.subscribedSensors.add(sensorId);

    return this.temperatureStream
      .pipe(new SensorsFilter(sensorId));
  }

}

module.exports = Sensors;