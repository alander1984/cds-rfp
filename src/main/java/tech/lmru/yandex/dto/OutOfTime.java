package tech.lmru.yandex.dto;

import java.math.BigDecimal;

public class OutOfTime {
    private BigDecimal fixed = new BigDecimal(1000);
    private BigDecimal minute = new BigDecimal(17);
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
	 * @return the minute
	 */
	public BigDecimal getMinute() {
		return minute;
	}
	/**
	 * @param minute the minute to set
	 */
	public void setMinute(BigDecimal minute) {
		this.minute = minute;
	}

    
    
}
