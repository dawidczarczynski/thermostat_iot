import { AzureFunction, Context, HttpRequest } from "@azure/functions"
import { Registry } from 'azure-iothub';

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {
    const deviceId = req.body.deviceId;
    const connectionString = process.env.AzureIotHubRegistry;

    const registry = Registry.fromConnectionString(connectionString);
    const newDevice: Registry.DeviceDescription = { deviceId };

    try {
        const device = await registry.create(newDevice);
        context.log.info('Device has been created', device);
        context.res = {
            status: 200,
            body: `Device has been created with ID ${device.responseBody.deviceId}`
        };
    } catch(ex) {
        context.log.error(ex.message);
        context.res = {
            status: 500,
            body: 'Device cannot be registered'
        };
    }
};

export default httpTrigger;
