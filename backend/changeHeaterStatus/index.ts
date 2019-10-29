import { AzureFunction, Context, HttpRequest } from "@azure/functions"
import { Client } from 'azure-iothub';
import { Message } from 'azure-iot-common';

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {

    const connectionString = 'HostName=hostname;SharedAccessKeyName=device;SharedAccessKey=sharedaccesskey';
    const targetDevice = 'thermostat';

    const client = Client.fromConnectionString(connectionString);

    try {
        await client.open();
        context.log.info('Connection established...');
        
        const message = new Message('test');

        await client.send(targetDevice, message);
        context.log.info('Message has been sent to the device...');

        await client.close();
        context.log.info('Connection closed...');

        context.res = {
            status: 200,
            body: "Message sended to the device"
        };
    } catch (ex) {
        context.log.error('ERROR: ', ex.message);
        context.res = {
            status: 500,
            body: ex.message
        };
    }
};

export default httpTrigger;
