package tech.lmru.yandex.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Location {
    
    private int id;
    private tech.lmru.yandex.dto.Point point = new Point();
    /**
     * Allowed time window to visit location, in [D.]HH[:MM[:SS]] - [D.]HH[:MM[:SS]] format or ISO 8601 2018-09-06T10:15:00+03:00/2018-09-06T12:45:00+03:00, 2018-09-06T10:15:00Z/2018-09-06T12:45:00Z
     */
    @JsonInclude(Include.NON_NULL)
    private String time_window;
    /**
     * Restricts time window relaxation during route planning.If location can not be visited within specified time window, it will be excluded from route and added to to dropped_orders field in response. This field is useful to model orders, that can not be completed when time window is failed due to conflicts with other orders.
     */
     @JsonInclude(Include.NON_NULL)
    private boolean hard_window = false;
    private ShipmentSize shipment_size = new ShipmentSize();
    /**
     * Duration of service at location. Service duration can include any operations at location, such as loading or unloading items from a vehicle, or any other time associated with a location (apart from waiting, which is time after arrival and before service).
     */
    private BigDecimal service_duration_s;
    /**
     * Time spent at a depot during handling of this location (goods loading, document collection, etc.).
     */
    private BigDecimal depot_duration_s;
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
	 * @return the shipment_size
	 */
	public ShipmentSize getShipment_size() {
		return shipment_size;
	}
	/**
	 * @param shipment_size the shipment_size to set
	 */
	public void setShipment_size(ShipmentSize shipment_size) {
		this.shipment_size = shipment_size;
	}
	/**
	 * @return the service_duration_s
	 */
	public BigDecimal getService_duration_s() {
		return service_duration_s;
	}
	/**
	 * @param service_duration_s the service_duration_s to set
	 */
	public void setService_duration_s(BigDecimal service_duration_s) {
		this.service_duration_s = service_duration_s;
	}
	/**
	 * @return the depot_duration_s
	 */
	public BigDecimal getDepot_duration_s() {
		return depot_duration_s;
	}
	/**
	 * @param depot_duration_s the depot_duration_s to set
	 */
	public void setDepot_duration_s(BigDecimal depot_duration_s) {
		this.depot_duration_s = depot_duration_s;
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
