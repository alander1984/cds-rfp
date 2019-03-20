package tech.lmru.yandex.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Depot {
    
    private int id = 0;
    
    @JsonInclude(Include.NON_NULL)
    private tech.lmru.yandex.dto.Point point = new Point();
    /**
     * Allowed time window to visit location, in [D.]HH[:MM[:SS]] - [D.]HH[:MM[:SS]] format or ISO 8601 2018-09-06T10:15:00+03:00/2018-09-06T12:45:00+03:00, 2018-09-06T10:15:00Z/2018-09-06T12:45:00Z
     */
     @JsonInclude(Include.NON_NULL)
    private String time_window = "09:00:00-17:59:59";
    /**
     * Location reference id.
     */
     @JsonInclude(Include.NON_NULL)
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
