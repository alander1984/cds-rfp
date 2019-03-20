package tech.lmru.yandex.dto;

import java.math.BigDecimal;

public class Cost {
    /**
     * Cost per fact of vehicle use (cost occurred if vehicle is included in route).
     */
    private BigDecimal fixed = new BigDecimal(3000);
    /**
     * Cost per single vehicle run from depot to locations.
     */
    private BigDecimal run = new BigDecimal(0);
    /**
     * Cost of using vehicle per kilometer. It should not be zero or you may get routes which are not optimal in terms of distance.
     */
    private BigDecimal km = new BigDecimal(8);
    /**
     * Cost of using vehicle per hour. It should not be zero or you may get routes which are not optimal in terms of time.
     */
    private BigDecimal hour = new BigDecimal(100);
    /**
     * Cost of using vehicle per location (default 0).
     */
    private BigDecimal location = new BigDecimal(0);
	/**
	 * @return the fixed
	 */
	public BigDecimal getFixed() {
		return fixed;
	}
	/**
	 * @param fixed the fixed to set
	 */
	public void setFixed(BigDecimal fixed) {
		this.fixed = fixed;
	}
	/**
	 * @return the run
	 */
	public BigDecimal getRun() {
		return run;
	}
	/**
	 * @param run the run to set
	 */
	public void setRun(BigDecimal run) {
		this.run = run;
	}
	/**
	 * @return the km
	 */
	public BigDecimal getKm() {
		return km;
	}
	/**
	 * @param km the km to set
	 */
	public void setKm(BigDecimal km) {
		this.km = km;
	}
	/**
	 * @return the hour
	 */
	public BigDecimal getHour() {
		return hour;
	}
	/**
	 * @param hour the hour to set
	 */
	public void setHour(BigDecimal hour) {
		this.hour = hour;
	}
	/**
	 * @return the location
	 */
	public BigDecimal getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(BigDecimal location) {
		this.location = location;
	}
    
    
}
