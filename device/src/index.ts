import { Message } from "azure-iot-device";
import { DeviceMessageType } from "contract";

import { IotClient } from "./interfaces/IotClient";
import { Logger } from "./interfaces/Logger";

import { MqttClient } from "./core/MqttClient";
import { BasicLogger } from "./core/BasicLogger";
import { HeaterController } from "./core/HeaterController";
import { MessageHelpers } from "./core/MessageHelpers";
import { MessageBuilder } from "./core/MessageBuilder";

(async () => {
  const logger: Logger = new BasicLogger();

  try {
    // Open client connection
    const client: IotClient = new MqttClient(logger);
    const heater = new HeaterController(logger);

    await client.openConnection();

    // Receive message
    client.onMessage(async (message: Message) => {

      try {
        const { type, payload } = MessageHelpers.getMessageData(message);
        switch (type) {
          case DeviceMessageType.SET_INITIAL_HEATER_STATUS:
            heater.changeState(payload);
            break;
          case DeviceMessageType.CHANGE_HEATER_STATUS: {
            heater.changeState(payload);
  
            const newStatusMessage = new MessageBuilder()
              .setType(DeviceMessageType.HEATER_STATUS_CHANGED)
              .setPayload(heater.getHeaterState())
              .buildStringified();
            await client.send(new Message(newStatusMessage));
            break;
          }
        }
      } catch (ex) {
        logger.logError(`Mesage received, but error occured while executing: ${ex.message}`);
      }
    });

    // Send message to get current heater status
    const getHeaterStatus = new MessageBuilder()
      .setType(DeviceMessageType.GET_HEATER_STATUS)
      .buildStringified();
    await client.send(new Message(getHeaterStatus));

  } catch (ex) { 
    logger.logError(ex.mesage);
  }

})();
