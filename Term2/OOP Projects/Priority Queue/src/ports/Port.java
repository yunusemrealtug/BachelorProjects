
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ports;

import java.util.ArrayList;
import java.util.Collections;

import containers.Container;
import interfaces.IPort;
import ships.Ship;
/**
 * the class for ports
 * @author Yunus Emre
 *
 */
public class Port implements IPort {
	
	/** distinctive number of each port, it cannot be changed
	 */
	private final int ID;
	/** x coordinate of each port, it cannot be changed
	 */
	private final double X;
	/** y coordinate of each port, it cannot be changed
	 */
	private final double Y;
	/** the list of containers in that port
	 */
	private final ArrayList<Container> containers;
	/** the list of ships which were located in that port
	 */
	private final ArrayList<Ship> history;
	/** the list of ships which are located in that port
	 */
	private final ArrayList<Ship> current;
	
	/**
	 * constructs port
	 * @param ID ID distinctive number of each port
	 * @param X x coordinate of each port
	 * @param Y y coordinate of each port
	 */
	public Port (int ID, double X, double Y) {
		this.ID=ID;
		this.X=X;
		this.Y=Y;
		this.containers=new ArrayList<Container>();
		this.history=new ArrayList<Ship>();
		this.current=new ArrayList<Ship>();
	}
	/**
	 * calculates distance between two ports in kilometers
	 * @param other the other port
	 * @return distance between two ports in kilometers
	 */
	public double getDistance(Port other) {
		double differenceX=this.X-other.X;
		double differenceY=this.Y-other.Y;
		double distanceSquare= differenceX*differenceX+differenceY*differenceY;
		return Math.sqrt(distanceSquare);
	}
	/**
	 * adds ship into current if it comes from another port
	 * @param s ship which is coming
	 */

	@Override
	public void incomingShip(Ship s) {
		// TODO Auto-generated method stub
		if (current.contains(s)) {
			
		}
		else {	
			current.add(s);
		}
	}
	/**
	 * adds ship into history if ship leaves port firstly and removes ship from current 
	 * @param s ship which is leaving
	 */


	@Override
	public void outgoingShip(Ship s) {
		// TODO Auto-generated method stub
		current.remove(s);
		if (history.contains(s)) {
		}
		else {
		history.add(s);
		}
	}
	/**
	 * sorts the containers in that port based on their IDs
	 * @return containers in the port at that time
	 */

	public ArrayList<Container> getContainers() {
		Collections.sort(containers);
		return containers;
	}
	/**
	 * gets x coordinate of port
	 * @return x coordinate of port
	 */

	public double getX() {
		return X;
	}
	/**
	 * gets y coordinate of port
	 * @return y coordinate of port
	 */

	public double getY() {
		return Y;
	}
	/**
	 * checks presence of container type in port which is requested by user 
	 * @param a name of container type
	 * @return correctness of presence 
	 */
	public boolean containerCheck(String a) {
		for (Container j: this.containers) {
			if (j.getClass().getName()==a) {
				return true;
			}
		}
		return false;
	}
	/**
	 * sorts the ships in that port based on their IDs
	 * @return ships in the port at that time
	 */
	public ArrayList<Ship> getCurrent() {
		Collections.sort(current);
		return current;
	}
	
	
	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

