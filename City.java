/**
 *	City data - the city name, state name, location designation,
 *				and population est. 2017
 *
 *	@author	Rishabh Goel
 *	@since	1/10/23
 */
public class City implements Comparable<City> {
	
	// fields
	private String name;
	private String state;
	private String designation;
	private int population;
	
	// constructor
	public City(String stateIn, String nameIn, String type, int popIn){
		name = nameIn;
		state = stateIn;
		designation = type;
		population = popIn;
	}
	
	/**	Compare two cities populations
	 *	@param other		the other City to compare
	 *	@return				the following value:
	 *		If populations are different, then returns (this.population - other.population)
	 *		else if states are different, then returns (this.state - other.state)
	 *		else returns (this.name - other.name)
	 */
	 public int compareTo(City other){
		if (this.population != other.population)
			return this.population - other.population;
		else if (this.state.compareTo(other.state) != 0)
			return this.state.compareTo(other.state);
		else
			return this.name.compareTo(other.name);
	 }
	
	/**	Compare two cities names
	 * 
	 *	@param other		the other City to compare
	 *	@return				the following value:
	 */
	public int compareNames(City other){
		int comparison = this.name.compareTo(other.name);
		if (comparison != 0){
			return comparison;
		}else if (this.population != other.population){
			return other.population - this.population;
		}else {
			return this.state.compareTo(other.state);
		}
	}
	
	/**	Equal city name and state name
	 *	@param other		the other City to compare
	 *	@return				true if city name and state name equal; false otherwise
	 */
	
	public boolean equals(Object other){
		return other instanceof City && this.compareTo((City)other) == 0;
	}
	
	/**	Accessor methods */
	public String getName(){ return name; }
	public String getState(){ return state; }
	public String getDesignation() { return designation; } 
	public int getPopulation() { return population; } 
	
	/**	toString */
	@Override
	public String toString() {
		return String.format("%-22s %-22s %-12s %,12d", state, name, designation,
						population);
	}
}
