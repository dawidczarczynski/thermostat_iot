const Sensors = require('../core/Sensors');
const Heater = require('../core/Heater');

class RestController {

  constructor() {
    this.sensors = new Sensors();
    this.heater = new Heater();
  }

  getSensorsList(_, res) {
    console.log('Get sensors list endpoint called');
    const sensors = this.sensors.getSensorsList();
    
    res.send({ sensors });
  }

  addSensor(req, res) {
    const sensor = req.body.sensor;
    console.log(`Add sensor endpoint called with sensor: ${JSON.stringify(sensor)}`);
    this.sensors.addSensor(sensor);

    res.status(201).end();
  }

  changeHeaterStatus(req, res) {
    const status = req.body.heaterStatus;
    console.log(`Change heater status endpoint called with status: ${status}`);
    this.heater.changeHeaterStatus(status);

    res.status(200).send({
      status: "Heater status change request registered"
    });
  }

}

module.exports = RestController;
