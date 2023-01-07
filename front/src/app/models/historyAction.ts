export class HistoryAction {
  constructor(
    public id?: number,
    public open?: number,
    public high?: number,
    public low?: number,
    public adjustedClose?: number,
    public volume?: number,
    public dividendAmount?: number,
    public modifiedDate?: number
  ) {}
}
