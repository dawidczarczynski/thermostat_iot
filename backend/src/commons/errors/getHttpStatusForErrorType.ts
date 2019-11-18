import { ErrorTypes } from "./ErrorTypes";

export function getHttpStatusForErrorType(errorType: ErrorTypes): number {
  switch (errorType) {
    case ErrorTypes.NOT_FOUND:
      return 404;
    default:
      return 500;
  }
}