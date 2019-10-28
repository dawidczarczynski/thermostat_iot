import { Client, Message } from 'azure-iot-device';
import { MessageEnqueued } from 'azure-iot-common/lib/results';
import { Mqtt } from 'azure-iot-device-mqtt';

import { CONNECTION_STRING } from '../config';
import { IotClient } from '../interfaces/IotClient';
import { Logger } from '../interfaces/Logger';

enum Events {
  ERROR = 'error',
  MESSAGE = 'message'
}
export class MqttClient implements IotClient {

  private client: Client;

  constructor(private logger: Logger) {
    this.client = Client.fromConnectionString(CONNECTION_STRING, Mqtt);
    this.client.on(Events.ERROR, error => { throw new Error(`Client error. Reason: ${error.message}`) });
  }

  public async openConnection(): Promise<void> {
    try {
      await this.client.open();
      this.logger.logInfo('Connection with IoT Hub established');
    } catch (ex) {
      throw new Error(`Could not connect to the IoT Hub. Reason: ${ex.message}`);
    }
  }

  public async send(message: Message): Promise<MessageEnqueued> {
    try {
      this.logger.logInfo(`Sending message: ${JSON.stringify(message)}`);
      return await this.client.sendEvent(message)
    } catch (ex) {
      throw new Error(`Could not send the message. Reason: ${ex.message}`);
    }
  }

  public onMessage(callback: (message: Message) => void): void {
    this.client.on(Events.MESSAGE, callback)
  }
  
}
