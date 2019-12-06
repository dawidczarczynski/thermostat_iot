const Sensors = require('../core/Sensors');
const Heater = require('../core/Heater');

class RestController {

  constructor() {
    this.sensors = new Sensors();
    this.heater = new Heater();
  }

  getSensorsList(_, res) {
    console.log('Get sensors list endpoint called');
    const sensorsList = this.sensors.getSensorsList();
    
    res.send(sensorsList);
  }

  changeHeaterStatus(req, res) {
    const status = req.body.heaterStatus;
    console.log(`Change heater status endpoint called with status: ${status}`);
    this.heater.changeHeaterStatus(status);

    res.status(200).end();
  }

}

module.exports = RestController;
