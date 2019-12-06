const { Transform } = require('stream')

class SensorFilter extends Transform {

  constructor(sensorId) {
    super({
      readableObjectMode: true,
      writableObjectMode: true
    });
    this.sensorId = sensorId;
  }

  _transform(chunk, encoding, next) {
    if (this.checkSensorId(chunk.id)) {
      return next(null, chunk)
    }

    next()
  }

  checkSensorId(id) {
    return id === this.sensorId;
  }
}

module.exports = SensorFilter  