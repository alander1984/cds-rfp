package tech.lmru.yandex.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Options {
    private BigDecimal time_zone = new BigDecimal(3);
    @JsonInclude(Include.NON_NULL)
    private BigDecimal default_speed_km_h;
    /**
     * Starting date for all routes (YYYY-MM-DD format), defaults to current date.
     */
    @JsonInclude(Include.NON_NULL)
    private String date;
    @JsonInclude(Include.NON_NULL)
    private Minimize minimize;
	/**
	 * @return the time_zone
	 */
	public BigDecimal getTime_zone() {
		return time_zone;
	}
	/**
	 * @param time_zone the time_zone to set
	 */
	public void setTime_zone(BigDecimal time_zone) {
		this.time_zone = time_zone;
	}
	/**
	 * @return the default_speed_km_h
	 */
	public BigDecimal getDefault_speed_km_h() {
		return default_speed_km_h;
	}
	/**
	 * @param default_speed_km_h the default_speed_km_h to set
	 */
	public void setDefault_speed_km_h(BigDecimal default_speed_km_h) {
		this.default_speed_km_h = default_speed_km_h;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the minimize
	 */
	public Minimize getMinimize() {
		return minimize;
	}
	/**
	 * @param minimize the minimize to set
	 */
	public void setMinimize(Minimize minimize) {
		this.minimize = minimize;
	}
    
    
    
    
}
