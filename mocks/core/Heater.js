const { Readable } = require('stream');

const ON = true;
const OFF = false;

class Heater {

  constructor() {
    if (Heater.instance) {
      return Heater.instance;
    }

    this.status = OFF;
    this.statusStream = new Readable({ 
      objectMode: true,
      read: () => true
    });

    Heater.instance = this;
  }

  changeHeaterStatus(heaterStatus) {
    this.status = heaterStatus;
    this.statusStream.push({ heaterStatus })
  }

  subscribeForHeaterStatus() {
    return this.statusStream;
  }

}

module.exports = Heater;
