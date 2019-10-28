import { Message } from "azure-iot-device";
import { IotClient } from "./client/IotClient";
import { MqttClient } from "./client/MqttClient";

(async () => {

  try {

    // Open client connection
    const client: IotClient = new MqttClient();
    await client.openConnection();

    // Receive message
    client.onMessage((message: Message) => {
      const messageText = message.getData().toString();
      console.log(messageText);
    });

    // Send message
    await client.send(new Message('Some message'));

  } catch (ex) { 
    console.error(ex.mesage);
  }

})();
