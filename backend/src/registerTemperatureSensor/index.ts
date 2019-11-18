import { AzureFunction, Context, HttpRequest, Logger } from "@azure/functions"
import { Registry } from 'azure-iothub';
import { functionWrapper } from "../commons/functionWrapper";

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {
    const deviceId = req.body.deviceId;
    const connectionString = process.env.AzureIotHubRegistry;

    await functionWrapper(context, req, async () => {
        const logger: Logger = context.log;
        const registry = Registry.fromConnectionString(connectionString);
        const newDevice: Registry.DeviceDescription = { deviceId };

        const device = await registry.create(newDevice);

        logger.info('Device has been created', device);

        return `Device has been created with ID ${device.responseBody.deviceId}`;
    });
};

export default httpTrigger;
