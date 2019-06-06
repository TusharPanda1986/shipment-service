package com.ws.shipment.models;

public class ZipCodeRange {

	private Integer startingZip;
	private Integer endingZip;
	
	public ZipCodeRange(String startingZip, String endingZip) {
		this.startingZip = Integer.valueOf(startingZip);
		this.endingZip = Integer.valueOf(endingZip);
	}
	
	public Integer getStartingZip() {
		return startingZip;
	}
	public Integer getEndingZip() {
		return endingZip;
	}
	public void setStartingZip(Integer startingZip) {
		this.startingZip = startingZip;
	}
	public void setEndingZip(Integer endingZip) {
		this.endingZip = endingZip;
	}

	@Override
	public String toString() {
		return "[" + startingZip + "," + endingZip + "]";
	}
	
}
