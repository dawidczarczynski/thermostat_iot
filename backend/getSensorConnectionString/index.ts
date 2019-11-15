import { AzureFunction, Context, HttpRequest } from "@azure/functions"
import { ConnectionStringParser } from "./ConnectionStringParser";
import { DeviceService } from "./DeviceService";

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {

    const deviceId = context.bindingData.deviceId;
    const connectionString = process.env.AzureIotHubRegistry;

    try {
        const deviceService = new DeviceService(connectionString);
        const device = await deviceService.getDeviceById(deviceId);
        if (!device) {
            context.res = {
                status: 404,
                body: `Could not find device with ID ${deviceId}`
            };
        } else {
            const deviceConnectionString = ConnectionStringParser.buildDeviceConnectionString(connectionString, device); 
            context.res = {
                body: { deviceConnectionString }
            };
        }
    } catch(ex) {
        context.log.error(ex.message);
        context.res = {
            status: 500,
            body: "Cannot get the device"
        };
    }

};

export default httpTrigger;
