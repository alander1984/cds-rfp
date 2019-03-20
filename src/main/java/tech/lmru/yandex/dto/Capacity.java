package tech.lmru.yandex.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Capacity {
     @JsonInclude(Include.NON_NULL)
    private Volume volume = new Volume();
    /**
     * Amount of custom units a vehicle is able to carry.
     */
     @JsonInclude(Include.NON_NULL)
    private BigDecimal units = new BigDecimal(0);
     @JsonInclude(Include.NON_NULL)
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
