package tech.lmru.yandex.dto;

import java.math.BigDecimal;

public class Capacity {
    private Volume volume = new Volume();
    /**
     * Amount of custom units a vehicle is able to carry.
     */
    private BigDecimal units = new BigDecimal(0);
    private BigDecimal weight_kg;
	/**
	 * @return the volume
	 */
	public Volume getVolume() {
		return volume;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(Volume volume) {
		this.volume = volume;
	}
	/**
	 * @return the units
	 */
	public BigDecimal getUnits() {
		return units;
	}
	/**
	 * @param units the units to set
	 */
	public void setUnits(BigDecimal units) {
		this.units = units;
	}
	/**
	 * @return the weight_kg
	 */
	public BigDecimal getWeight_kg() {
		return weight_kg;
	}
	/**
	 * @param weight_kg the weight_kg to set
	 */
	public void setWeight_kg(BigDecimal weight_kg) {
		this.weight_kg = weight_kg;
	}

    
    
}
