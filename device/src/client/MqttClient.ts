import { Client, Message } from 'azure-iot-device';
import { MessageEnqueued } from 'azure-iot-common/lib/results';
import { Mqtt } from 'azure-iot-device-mqtt';

import { CONNECTION_STRING } from '../config';
import { IotClient } from './IotClient';

enum Event {
  ERROR = 'error',
  MESSAGE = 'message'
}

export class MqttClient implements IotClient {

  private client: Client;

  constructor() {
    this.client = Client.fromConnectionString(CONNECTION_STRING, Mqtt);
    this.client.on(Event.ERROR, error => { throw new Error(`Client error. Reason: ${error.message}`) });
  }

  public async openConnection(): Promise<void> {
    try {
      await this.client.open();
    } catch (ex) {
      throw new Error(`Could not connect to the IoT Hub. Reason: ${ex.message}`);
    }
  }

  public async send(message: Message): Promise<MessageEnqueued> {
    try {
      return await this.client.sendEvent(message)
    } catch (ex) {
      throw new Error(`Could not send the message. Reason: ${ex.message}`);
    }
  }

  public onMessage(callback: (message: Message) => void): void {
    this.client.on(Event.MESSAGE, callback)
  }
  
}
