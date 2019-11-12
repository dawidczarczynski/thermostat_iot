import { AzureFunction, Context, HttpRequest } from "@azure/functions"
import { Registry } from 'azure-iothub';

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {

    const deviceId = context.bindingData.deviceId;
    const connectionString = process.env.AzureIotHubRegistry;

    try {
        const registry = Registry.fromConnectionString(connectionString);
        const device = (await registry.get(deviceId)).responseBody;
        if (!device) {
            context.res = {
                status: 404,
                body: `Could not find device with ID ${deviceId}`
            };
        } else {
            const host = connectionString.split(';')[0].replace('HostName=', '');
            const deviceConnectionString = `HostName=${host};DeviceId=${device.deviceId};SharedAccessKey=${device.authentication.symmetricKey.primaryKey}`;
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
