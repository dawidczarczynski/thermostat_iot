import { AzureFunction, Context, HttpRequest } from "@azure/functions"
import { Client } from 'azure-iothub';
import { Message } from 'azure-iot-common';
import { DeviceMessageType } from 'contract';

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {

    const connectionString = process.env['AzureIotHub'];
    const targetDevice = 'thermostat';
    const client = Client.fromConnectionString(connectionString);
    const requestedStatus = req.body.heaterStatus;
    try {
        await client.open();
        context.log.info('Connection established...');
        
        const message = new Message(JSON.stringify({ type: DeviceMessageType.CHANGE_HEATER_STATUS, payload: requestedStatus }));

        await client.send(targetDevice, message);
        context.log.info('Message has been sent to the device...');

        await client.close();
        context.log.info('Connection closed...');

        context.res = {
            status: 200,
            body: "Message has been send to the device"
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
