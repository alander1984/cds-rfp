package tech.lmru.yandex.dto;

public class Depot {
    
    private int id;
    private tech.lmru.yandex.dto.Point point = new Point();
    /**
     * Allowed time window to visit location, in [D.]HH[:MM[:SS]] - [D.]HH[:MM[:SS]] format or ISO 8601 2018-09-06T10:15:00+03:00/2018-09-06T12:45:00+03:00, 2018-09-06T10:15:00Z/2018-09-06T12:45:00Z
     */
    private String time_window;
    /**
     * Location reference id.
     */
    private String ref;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the point
	 */
	public tech.lmru.yandex.dto.Point getPoint() {
		return point;
	}
	/**
	 * @param point the point to set
	 */
	public void setPoint(tech.lmru.yandex.dto.Point point) {
		this.point = point;
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
	 * @return the ref
	 */
	public String getRef() {
		return ref;
	}
	/**
	 * @param ref the ref to set
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}
    
    
    
}
