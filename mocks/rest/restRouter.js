const Express = require('express');
const RestController = require('./RestController');

const ALL_SENSORS = '/sensors';

const router = Express.Router();
const controller = new RestController();

router.get(ALL_SENSORS, (req, res) => controller.getSensorsList(req, res));

module.exports = router;