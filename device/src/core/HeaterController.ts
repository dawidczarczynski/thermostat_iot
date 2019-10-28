import { Logger } from "../interfaces/Logger";

export class HeaterController {
  
  private isHeaterOn: boolean = false;

  constructor(private logger: Logger) {}

  public changeState(isOn: boolean): void {
    isOn
      ? this.turnHeaterOn()
      : this.turnHeaterOff();
  }

  public getHeaterState(): boolean {
    return this.isHeaterOn;
  }

  private turnHeaterOn(): void {
    this.isHeaterOn = true;
    this.logger.logInfo('Heater has been turned on');
  }

  private turnHeaterOff(): void {
    this.isHeaterOn = false;
    this.logger.logInfo('Heater has been turned off');
  }

}