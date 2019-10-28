import { Message } from "azure-iot-device";
import { IotClient } from "./client/IotClient";
import { MqttClient } from "./client/MqttClient";
import { Logger } from "./logger/Logger";

(async () => {
  const logger = new Logger();
  try {

    // Open client connection
    const client: IotClient = new MqttClient(logger);
    await client.openConnection();

    // Receive message
    client.onMessage((message: Message) => {
      const messageText = message.getData().toString();
      logger.logInfo(`Message received: ${messageText}`);
    });

    // Send message
    await client.send(new Message('Some message'));

  } catch (ex) { 
    logger.logError(ex.mesage);
  }

})();
