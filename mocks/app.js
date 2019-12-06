const Express = require('express');
const restRouter = require('./rest/restRouter');

const PREFIX = '/api';

const app = Express();

app.use(Express.json());
app.use(PREFIX, restRouter);

module.exports = app;
