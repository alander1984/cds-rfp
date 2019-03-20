package tech.lmru.yandex.dto;

import java.util.HashSet;
import java.util.Set;


public class Vehicle {
    private int id;
    private Capacity capacity = new Capacity();
    /**
     * Priority of vehicle (the higher the priority, the more likely it is used).
     */
    private int priority;
    private Set<Shift> shifts = new HashSet<>();
    private Cost cost = new Cost();
    private String company_name;
    /**
     * List of locations to be visited by a vehicle in exact order as fixed part of the route without optimizations, with optional time of arrival for already visited locations.
     */
    private Set<VisitedLocation> visited_locations = new HashSet<>();
    /**
     * Vehicle reference number.
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
	 * @return the capacity
	 */
	public Capacity getCapacity() {
		return capacity;
	}
	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(Capacity capacity) {
		this.capacity = capacity;
	}
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	/**
	 * @return the shifts
	 */
	public Set<Shift> getShifts() {
		return shifts;
	}
	/**
	 * @param shifts the shifts to set
	 */
	public void setShifts(Set<Shift> shifts) {
		this.shifts = shifts;
	}
	/**
	 * @return the cost
	 */
	public Cost getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(Cost cost) {
		this.cost = cost;
	}
	/**
	 * @return the company_name
	 */
	public String getCompany_name() {
		return company_name;
	}
	/**
	 * @param company_name the company_name to set
	 */
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	/**
	 * @return the visited_locations
	 */
	public Set<VisitedLocation> getVisited_locations() {
		return visited_locations;
	}
	/**
	 * @param visited_locations the visited_locations to set
	 */
	public void setVisited_locations(Set<VisitedLocation> visited_locations) {
		this.visited_locations = visited_locations;
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
