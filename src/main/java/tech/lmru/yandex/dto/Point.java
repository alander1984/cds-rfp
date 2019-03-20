package tech.lmru.yandex.dto;

import java.math.BigDecimal;

public class Point {
    private BigDecimal lat = new BigDecimal(0);
    private BigDecimal lon = new BigDecimal(0);
	/**
	 * @return the lat
	 */
	public BigDecimal getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}
	/**
	 * @return the lon
	 */
	public BigDecimal getLon() {
		return lon;
	}
	/**
	 * @param lon the lon to set
	 */
	public void setLon(BigDecimal lon) {
		this.lon = lon;
	}

    
}
