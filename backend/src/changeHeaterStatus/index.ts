import { AzureFunction, Context, HttpRequest, Logger } from "@azure/functions"
import { Client } from 'azure-iothub';
import { DeviceMessageType } from 'contract';
import { MessageBuilder } from 'shared';
import { functionWrapper } from "../commons/functionWrapper";

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {

    const connectionString = process.env['AzureIotHub'];
    const targetDevice = 'thermostat';
    const requestedStatus = req.body.heaterStatus;
    
    await functionWrapper(context, req, async () => {
        const logger: Logger = context.log;
        const client = Client.fromConnectionString(connectionString);

        await client.open();
        logger.info('Connection established...');
        
        const message = new MessageBuilder()
            .setType(DeviceMessageType.CHANGE_HEATER_STATUS)
            .setPayload(requestedStatus)
            .buildStringified();
        
        await client.send(targetDevice, message);
        logger.info('Message has been sent to the device...');

        await client.close();
        logger.info('Connection closed...');
    });

};

export default httpTrigger;
