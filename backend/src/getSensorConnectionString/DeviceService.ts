import { Registry, Device } from "azure-iothub";
import { CustomError } from "../commons/errors/CustomError";
import { ErrorTypes } from "../commons/errors/ErrorTypes";

export class DeviceService {
  
  private registry: Registry;

  constructor(connectionString: string) {
    this.registry = Registry.fromConnectionString(connectionString);
  }

  public async getDeviceById(deviceId: string): Promise<Device> {
    const registryResponse = await this.registry.get(deviceId);

    if (!registryResponse) {
        throw new CustomError('Device not found', ErrorTypes.NOT_FOUND);
    }

    return registryResponse.responseBody;
  }

}