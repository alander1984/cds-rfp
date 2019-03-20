package tech.lmru.yandex.dto;

public class Shift {
    private String id = java.util.UUID.randomUUID().toString();
    private String time_window = "07:00:00-23:59:59";
    private boolean hard_window = false;
    private Penalty penalty = new Penalty();
    /**
     * Maximal duration of a shift in seconds. If real shift duration is more than specified, the shift out of time penalties are applied.
     */
    private float max_duration_s = 172800f;
    /**
     * Duration of service in between shifts (in seconds). It can include any operations needed to finish the shift, such as time to change a driver, exchange papers etc (default: 0).
     */
    private float service_duration_s = 0;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the time_window
	 */
	public String getTime_window() {
		return time_window;
	}
	/**
	 * @param time_window the time_window to set
	 */
	public void setTime_window(String time_window) {
		this.time_window = time_window;
	}
	/**
	 * @return the hard_window
	 */
	public boolean isHard_window() {
		return hard_window;
	}
	/**
	 * @param hard_window the hard_window to set
	 */
	public void setHard_window(boolean hard_window) {
		this.hard_window = hard_window;
	}
	/**
	 * @return the penalty
	 */
	public Penalty getPenalty() {
		return penalty;
	}
	/**
	 * @param penalty the penalty to set
	 */
	public void setPenalty(Penalty penalty) {
		this.penalty = penalty;
	}
	/**
	 * @return the max_duration_s
	 */
	public float getMax_duration_s() {
		return max_duration_s;
	}
	/**
	 * @param max_duration_s the max_duration_s to set
	 */
	public void setMax_duration_s(float max_duration_s) {
		this.max_duration_s = max_duration_s;
	}
	/**
	 * @return the service_duration_s
	 */
	public float getService_duration_s() {
		return service_duration_s;
	}
	/**
	 * @param service_duration_s the service_duration_s to set
	 */
	public void setService_duration_s(float service_duration_s) {
		this.service_duration_s = service_duration_s;
	}
    
    
    
}
