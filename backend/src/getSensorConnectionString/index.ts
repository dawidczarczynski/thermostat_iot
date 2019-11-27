import { AzureFunction, Context, HttpRequest } from "@azure/functions"

import { buildDeviceConnectionString } from "./buildDeviceConnectionString";
import { DeviceService } from "./DeviceService";
import { functionWrapper } from "../commons/functionWrapper";

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {

    const deviceId = context.bindingData.deviceId;
    const connectionString = process.env.AzureIotHubRegistry;

    await functionWrapper(context, req, async () => {
        const deviceService = new DeviceService(connectionString);
        const device = await deviceService.getDeviceById(deviceId);
        const deviceConnectionString = buildDeviceConnectionString(connectionString, device); 

        return { deviceConnectionString };
    });

};

export default httpTrigger;
