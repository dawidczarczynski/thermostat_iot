import { ErrorTypes } from "./ErrorTypes";

export class NotFoundError extends Error {
  
  public readonly type = ErrorTypes.NOT_FOUND;

  constructor(message: string) {
    super(message);
  }

}