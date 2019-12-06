const Io = require('socket.io');
const Sensors = require('../core/Sensors');
const Heater = require('../core/Heater');

const CONNECTION = 'connection';
const DISCONNECT = 'disconnect';
const LISTEN_FOR_SENSOR = 'listen_for_sensor';
const TEMPERATURE_CHANGE = 'temperature_change';
const LISTEN_FOR_HEATER_STATUS = 'listen_for_heater_status';
const HEATER_STATUS_CHANGE = 'heater_status_change';
const DATA_RECEIVED = 'data';

class WebsocketController {

  constructor(server) {
    if (WebsocketController.instance) {
      return WebsocketController.instance;
    }

    this.io = Io(server);
    this.sensors = new Sensors();
    this.heater = new Heater();
    this.connections = new Set();

    WebsocketController.instance = this;
  }

  start() {
    this.io.on(CONNECTION, client => {
      const { id } = client;

      console.log(`Socket user connected with id ${id}`);

      this.connections.add(id);
      if (this.connections.size > 0)
        this.sensors.startGeneratingTemperatureValues();
      
      console.log(`${this.connections.size} users connected`);

      client.on(LISTEN_FOR_SENSOR, sensorId => {
        this.sensors
          .subscribeForTemperatureChanges(sensorId)
          .on(DATA_RECEIVED, sample => client.emit(TEMPERATURE_CHANGE, sample));
      });

      client.on(LISTEN_FOR_HEATER_STATUS, () => {
        this.heater
        .subscribeForHeaterStatus()
        .on(DATA_RECEIVED, sample => client.emit(HEATER_STATUS_CHANGE, sample));     
      });

      client.on(DISCONNECT, () => {
        this.connections.delete(id);

        console.log(`${this.connections.size} users connected`);
      
        if (this.connections.size === 0)
          this.sensors.stopGeneratingTemperatureValues();
      });
    });

  }

}

module.exports = WebsocketController;
