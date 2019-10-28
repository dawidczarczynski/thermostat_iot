export class MessageBuilder {

  private messageObject: { type: string; payload?: any } = { type: '' };

  public setType(type: string): MessageBuilder {
    this.messageObject.type = type;
    return this;
  }

  public setPayload(payload: Object): MessageBuilder {
    this.messageObject.payload = payload;
    return this;
  }

  public buildStringified(): string {
    return JSON.stringify(this.messageObject);
  } 

}