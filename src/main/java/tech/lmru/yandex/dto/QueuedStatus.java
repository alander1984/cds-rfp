package tech.lmru.yandex.dto;

import java.math.BigDecimal;

public class QueuedStatus {
    /**
     * В одном из параметров будет 1, в остальных 0
     */
    private BigDecimal queued;
    private BigDecimal started;
    private BigDecimal cancelled;
    private BigDecimal completed;
	/**
	 * @return the queued
	 */
	public BigDecimal getQueued() {
		return queued;
	}
	/**
	 * @param queued the queued to set
	 */
	public void setQueued(BigDecimal queued) {
		this.queued = queued;
	}
	/**
	 * @return the started
	 */
	public BigDecimal getStarted() {
		return started;
	}
	/**
	 * @param started the started to set
	 */
	public void setStarted(BigDecimal started) {
		this.started = started;
	}
	/**
	 * @return the cancelled
	 */
	public BigDecimal getCancelled() {
		return cancelled;
	}
	/**
	 * @param cancelled the cancelled to set
	 */
	public void setCancelled(BigDecimal cancelled) {
		this.cancelled = cancelled;
	}
	/**
	 * @return the completed
	 */
	public BigDecimal getCompleted() {
		return completed;
	}
	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(BigDecimal completed) {
		this.completed = completed;
	}
    
    
}
