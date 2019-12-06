const Http = require('http');
const WebsocketController = require('./websocket/WebsocketController');

const app = require('./app');

const PORT = 3000;

const server = Http.createServer(app);
const websocket = new WebsocketController(server);

websocket.start();

server.listen(PORT, () => {
  console.log(`Server is listening on port ${PORT}`);
});
