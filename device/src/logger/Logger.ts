export class Logger {

  public logInfo(message: string): void {
    console.log(`INFO: ${message}`);
  }

  public logError(error: string): void {
    console.error(`ERROR: ${error}`);
  } 

}