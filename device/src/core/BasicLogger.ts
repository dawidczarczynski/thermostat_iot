import { Logger } from "../interfaces/Logger";

export class BasicLogger implements Logger {

  public logInfo(message: string): void {
    console.log(`INFO: ${message}`);
  }

  public logError(error: string): void {
    console.error(`ERROR: ${error}`);
  } 

}