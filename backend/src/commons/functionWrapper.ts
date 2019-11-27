import { Logger, Context, HttpRequest } from "@azure/functions";
import { getHttpStatusForErrorType } from "./errors/getHttpStatusForErrorType";

export async function functionWrapper(context: Context, req: HttpRequest, action: Function) {
  
  const logger: Logger = context.log;

  try {
    logger.info('Action started.');

    const actionResult = await action();

    logger.info('Action finished successfuly');

    context.res = {
      status: 200,
      body: actionResult
    };
  } catch (ex) {
    const error = ex.message;
    const status = getHttpStatusForErrorType(ex.type);

    logger.error('Action execution failed.', error);
    
    context.res = {
      status,
      body: { error }
    };
  }

}
