import { Message } from 'azure-iot-device';
import { MessageEnqueued } from 'azure-iot-common/lib/results';

export interface IotClient {
  openConnection(): Promise<void>;
  send(message: Message): Promise<MessageEnqueued>;
  onMessage(callback: (message: Message) => void): void
}