import { Registry, Device } from "azure-iothub";
import { NotFoundError } from "../commons/errors";

export class DeviceService {
  
  private registry: Registry;

  constructor(connectionString: string) {
    this.registry = Registry.fromConnectionString(connectionString);
  }

  public async getDeviceById(deviceId: string): Promise<Device> {
    const registryResponse = await this.registry.get(deviceId);

    if (!registryResponse) {
        throw new NotFoundError('Device not found');
    }

    return registryResponse.responseBody;
  }

}