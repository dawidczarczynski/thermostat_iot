const Sensors = require('../sensors/Sensors');

class RestController {

  constructor() {
    this.sensors = new Sensors();
  }

  getSensorsList(_, res) {
    const sensorsList = this.sensors.getSensorsList();
    res.send(sensorsList);
  }

}

module.exports = RestController;
