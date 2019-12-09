const Express = require('express');
const RestController = require('./RestController');

const ALL_SENSORS = '/sensors';
const HEATER_STATUS = '/heater';

const router = Express.Router();
const controller = new RestController();

router.get(ALL_SENSORS, (req, res) => controller.getSensorsList(req, res));
router.post(ALL_SENSORS, (req, res) => controller.addSensor(req, res));
router.post(HEATER_STATUS, (req, res) => controller.changeHeaterStatus(req, res));

module.exports = router;