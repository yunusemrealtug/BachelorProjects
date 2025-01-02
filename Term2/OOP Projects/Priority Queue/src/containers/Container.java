
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

/**
 * the class for containers
 * @author Yunus Emre
 *
 */
public abstract class Container implements Comparable<Container> {
	/** distinctive number of each container, it cannot be changed
	 */
	private final int ID;
	/** weight of each container, it cannot be changed
	 */
	private final int weight;
	
	/**
	 * constructs a container
	 * @param ID distinctive number of each container
	 * @param weight weight of each container
	 */
	public Container (int ID, int weight){
		this.ID=ID;
		this.weight=weight;
	}
	/**
	 * calculates consumption of fuel which container causes in a ship
	 * @return fuel consumption in liters
	 */
	public abstract double consumption(); 
	
	/**
	 * checks two containers' fields
	 * @param other the other container
	 * @return true if containers are same else false
	 */
	public boolean equals(Container other){
		if (this.ID==other.ID && this.weight==other.weight && this.getClass()==other.getClass()) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * gets weight of container
	 * @return weight of container
	 */

	public int getWeight() {
		return weight;
	}
	/**
	 * gets ID of container
	 * @return ID of container
	 */
	public int getID() {
		return ID;
	}
	public int compareTo(Container o) {
		// TODO Auto-generated method stub
		return this.ID - o.ID;
	}
	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

