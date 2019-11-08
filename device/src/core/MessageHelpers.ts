import { Message } from "azure-iot-device";
export class MessageHelpers {
  
  public static getMessageData(message: Message): { type: string; payload: any } {
    const data = message.getData().toString();

    try {
      return JSON.parse(data);
    } catch (ex) {
      throw new Error('Mesage data is not valid JSON'); 
    }
  }

}
