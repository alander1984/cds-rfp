package tech.lmru.yandex.dto;

import java.util.HashSet;
import java.util.Set;

public class OptimizationTask {
    private Depot depot = new Depot();
    private Options options = new Options();
    private Set<Location> locations = new HashSet<>();
    private Set<Vehicle> vehicles = new HashSet<>();
	/**
	 * @return the depot
	 */
	public Depot getDepot() {
		return depot;
	}
	/**
	 * @param depot the depot to set
	 */
	public void setDepot(Depot depot) {
		this.depot = depot;
	}
	/**
	 * @return the options
	 */
	public Options getOptions() {
		return options;
	}
	/**
	 * @param options the options to set
	 */
	public void setOptions(Options options) {
		this.options = options;
	}
	/**
	 * @return the locations
	 */
	public Set<Location> getLocations() {
		return locations;
	}
	/**
	 * @param locations the locations to set
	 */
	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}
	/**
	 * @return the vehicles
	 */
	public Set<Vehicle> getVehicles() {
		return vehicles;
	}
	/**
	 * @param vehicles the vehicles to set
	 */
	public void setVehicles(Set<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
    
    
}
