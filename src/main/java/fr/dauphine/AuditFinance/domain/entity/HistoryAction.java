package fr.dauphine.AuditFinance.domain.entity;

import jakarta.persistence.*;

import java.io.Serializable;

import java.time.LocalDateTime;

@Entity
@Table(name = "history_action")
public class HistoryAction implements Serializable {

    private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
   @SequenceGenerator(name = "sequenceGenerator")
   private Long id;

   private Double open;

   private Double high;

   private Double low;

   private Double close;

   private Double adjustedClose;

   private Long volume;

   private Double dividendAmount;
   
   private String stockKey;

   private LocalDateTime modifiedDate = LocalDateTime.now();

   public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

	public String getStockKey() {
		return stockKey;
	}

	public void setStockKey(String stockKey) {
		this.stockKey = stockKey;
	}
    
}
