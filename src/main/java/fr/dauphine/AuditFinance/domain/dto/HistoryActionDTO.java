package fr.dauphine.AuditFinance.domain.dto;

import java.io.Serializable;

public class HistoryActionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Double open;

	private Double high;

	private Double low;

	private Double close;

	private Double adjustedClose;

	private Long volume;

	private Double dividendAmount;

	private String modifiedDate;

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

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
