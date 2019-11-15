import { Registry, Device } from "azure-iothub";

export class DeviceService {
  
  private registry: Registry;

  constructor(connectionString: string) {
    this.registry = Registry.fromConnectionString(connectionString);
  }

  public async getDeviceById(deviceId: string): Promise<Device> {
    const registryResponse = await this.registry.get(deviceId);

    return registryResponse.responseBody;
  }

}