const Express = require('express');
const Http = require('http');
const Io = require('socket.io');
const Sensors = require('./Sensors');

const PORT = 3000;
const PREFIX = '/api';

const app = Express();
const server = Http.createServer(app);
const io = Io(server);
const sensors = new Sensors();

const router = Express.Router();

router.get('/sensors', (_, res) => {
  const sensorsList = sensors.getSensorsList();
  res.send(sensorsList);
});

app.use(PREFIX, router);

io.on('connection', socket => {
  console.log('Socket user connected');

  socket.on('listenForSensor', sensorId => {
    sensors.subscribeForTemperatureChanges(
        sensorId,
        response => io.emit('temperatureChange', response)
    );
  });
});

server.listen(PORT, () => {
  console.log(`Server is listening on port ${PORT}`);
})
