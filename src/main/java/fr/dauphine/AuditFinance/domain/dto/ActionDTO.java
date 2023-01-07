package fr.dauphine.AuditFinance.domain.dto;



public class ActionDTO {

   // @JsonProperty("1. open")
    private Double open;

   // @JsonProperty("2. high")
    private Double high;

   // @JsonProperty("3. low")
    private Double low;

 //   @JsonProperty("4. close")
    private Double close;

    //@JsonProperty("5. adjusted close")
    private Double adjustedClose;

   // @JsonProperty("6. volume")
    private Long volume;

   // @JsonProperty("7. dividend amount")
    private Double dividendAmount;

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getAdjustedClose() {
        return adjustedClose;
    }

    public void setAdjustedClose(Double adjustedClose) {
        this.adjustedClose = adjustedClose;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Double getDividendAmount() {
        return dividendAmount;
    }

    public void setDividendAmount(Double dividendAmount) {
        this.dividendAmount = dividendAmount;
    }
}
