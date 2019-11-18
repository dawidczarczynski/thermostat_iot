import { Device } from "azure-iothub";

export function buildDeviceConnectionString(hubConnectionString: string, device: Device): string {
    const host = hubConnectionString.split(';')[0].replace('HostName=', '');
    const deviceConnectionString = `HostName=${host};DeviceId=${device.deviceId};SharedAccessKey=${device.authentication.symmetricKey.primaryKey}`;
  
    return deviceConnectionString;
  }

