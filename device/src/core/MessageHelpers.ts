import { Message } from "azure-iot-device";

export enum MessageType {
  GET_HEATER_STATUS = 'GET_HEATER_STATUS',
  SET_INITIAL_HEATER_STATUS = 'SET_INITIAL_HEATER_STATUS',
  CHANGE_HEATER_STATUS = 'CHANGE_HEATER_STATUS',
  HEATER_STATUS_CHANGED = 'HEADER_STATUS_CHANGED'
}

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
