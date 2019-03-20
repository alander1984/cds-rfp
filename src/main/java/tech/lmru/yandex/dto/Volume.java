package tech.lmru.yandex.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Volume {
    
    /**
     * Width, meters.
     */
    @JsonInclude(Include.NON_NULL)
    private BigDecimal width_m;
    /**
     * Depth, meters.
     */
    @JsonInclude(Include.NON_NULL)
    private BigDecimal depth_m;
    /**
     * Height, meters.
     */
    @JsonInclude(Include.NON_NULL)
    private BigDecimal height_m;
	/**
	 * @return the width_m
	 */
	public BigDecimal getWidth_m() {
		return width_m;
	}
	/**
	 * @param width_m the width_m to set
	 */
	public void setWidth_m(BigDecimal width_m) {
		this.width_m = width_m;
	}
	/**
	 * @return the depth_m
	 */
	public BigDecimal getDepth_m() {
		return depth_m;
	}
	/**
	 * @param depth_m the depth_m to set
	 */
	public void setDepth_m(BigDecimal depth_m) {
		this.depth_m = depth_m;
	}
	/**
	 * @return the height_m
	 */
	public BigDecimal getHeight_m() {
		return height_m;
	}
	/**
	 * @param height_m the height_m to set
	 */
	public void setHeight_m(BigDecimal height_m) {
		this.height_m = height_m;
	}

    
}
