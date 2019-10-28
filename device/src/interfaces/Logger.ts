export interface Logger {
  logInfo(message: string): void;
  logError(error: string): void;
}