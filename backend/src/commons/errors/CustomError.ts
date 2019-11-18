import { ErrorTypes } from "./ErrorTypes";

export class CustomError extends Error {

  constructor(message: string, public type?: ErrorTypes) {
    super(message);
  }

}