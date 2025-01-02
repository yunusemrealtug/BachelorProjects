
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ships;

import java.util.ArrayList;
import java.util.Collections;

import containers.Container;
import interfaces.IShip;
import ports.Port;

/**
 * the class for ships
 * @author Yunus Emre
 *
 */
public class Ship implements IShip, Comparable<Ship> {
	
	/** distinctive number of each ship, it cannot be changed
	 */
	private final int ID;
	
	/** fuel in the ship
	 */
	private double fuel;
	/** port where ship is located
	 */
	private Port currentPort;
	/** max masses of containers can be added to ship
	 */
	private int totalWeightCapacity;
	/** max quantities of containers can be added to ship
	 */
	private int maxNumberOfAllContainers;
	/** max quantities of heavy (refrigerateds and liquids being included) containers can be added to ship
	 */
	private int maxNumberOfHeavyContainers;
	/** max quantities of refrigerated containers can be added to ship
	 */
	private int maxNumberOfRefrigeratedContainers;
	/** max quantities of liquid containers can be added to ship
	 */
	private int maxNumberOfLiquidContainers;
	/** fuel that ship consumes per 1 km 
	 */
	private double fuelConsumptionPerKM;
	/** the list of containers in that ship
	 */
	private final ArrayList<Container> currentContainers;
	
	/** constructs ship and adds ship to its port's ship list
	 * @param ID distinctive number of each ship
	 * @param p port where ship is located
	 * @param totalWeightCapacity max masses of containers can be added to ship
	 * @param maxNumberOfAllContainers max quantities of containers can be added to ship
	 * @param maxNumberOfHeavyContainers max quantities of heavy (refrigerateds and liquids being included) containers can be added to ship
	 * @param maxNumberOfRefrigeratedContainers max quantities of refrigerated containers can be added to ship
	 * @param maxNumberOfLiquidContainers max quantities of liquid containers can be added to ship
	 * @param fuelConsumptionPerKM fuel that ship consumes per 1 km 
	 */
	
	
	public Ship(int ID, Port p, int totalWeightCapacity, int
			maxNumberOfAllContainers, int maxNumberOfHeavyContainers, int
			maxNumberOfRefrigeratedContainers, int
			maxNumberOfLiquidContainers, double fuelConsumptionPerKM) {
		this.currentPort=p;
		this.ID=ID;
		this.fuel=0;
		this.fuelConsumptionPerKM=fuelConsumptionPerKM;
		this.maxNumberOfAllContainers=maxNumberOfAllContainers;
		this.maxNumberOfHeavyContainers=maxNumberOfHeavyContainers;
		this.maxNumberOfLiquidContainers=maxNumberOfLiquidContainers;
		this.maxNumberOfRefrigeratedContainers=maxNumberOfRefrigeratedContainers;
		this.currentContainers=new ArrayList <Container>();
		this.totalWeightCapacity=totalWeightCapacity;
		this.getCurrentPort().incomingShip(this);
	}
	
	/**
	 * sorts the containers in the ship based on their IDs
	 * @return containers in the ship at that time
	 */
	public ArrayList<Container> getCurrentContainers(){
		Collections.sort(currentContainers);
		return currentContainers;
	}
	
	
	/**
	 * calculates potential fuel consumption, 
	 * if fuel is enough:
	 * 1. consumes fuel as that as ship needs,
	 * 2. removes ship from current ship list of port that ship leaves from
	 * 3. adds ship to history list of port that ship leaves from if it does not contain
	 * 4. changes its port
	 * 5. adds ship to current list of port that ship goes to, else no action
	 * @param p port where ship is planning to go
	 */
	
	
	@Override
	public boolean sailTo(Port p) {
		// TODO Auto-generated method stub
		if (this.fuelConsumptionPerKM*this.currentPort.getDistance(p)<=this.fuel) {
			this.fuel-=this.fuelConsumptionPerKM*this.currentPort.getDistance(p);
			this.getCurrentPort().outgoingShip(this);
			this.setCurrentPort(p);
			p.incomingShip(this);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * adds fuel to ship
	 * @param newFuel amount of fuel to be added
	 */
	

	@Override
	public void reFuel(double newFuel) {
		// TODO Auto-generated method stub
		this.fuel+=newFuel;
	}
	
	/**
	 * checks container capacity of ship and presence of container in port,
	 * if capacity is enough and container is present, loads container in ship from port and capacity of ship decreases
	 * @param cont the container which is requested to be loaded
	 */

	@Override
	public boolean load(Container cont) {
		// TODO Auto-generated method stub
		if (this.currentPort.getContainers().contains(cont)) {
			if (cont.getClass().getName()=="containers.BasicContainer") {
				if (this.maxNumberOfAllContainers>0 && this.totalWeightCapacity-cont.getWeight()>=0) {
					currentContainers.add(cont);
					this.currentPort.getContainers().remove(cont);
					this.fuelConsumptionPerKM+=cont.consumption();
					this.maxNumberOfAllContainers-=1;
					this.totalWeightCapacity-=cont.getWeight();
					return true;
				}
				else {
					return false;
				}
			}
			else if (cont.getClass().getName()=="containers.HeavyContainer") {
				if (this.maxNumberOfAllContainers>0 && this.totalWeightCapacity-cont.getWeight()>=0
						&& this.maxNumberOfHeavyContainers>0) {
					currentContainers.add(cont);
					this.fuelConsumptionPerKM+=cont.consumption();
					this.currentPort.getContainers().remove(cont);
					this.maxNumberOfAllContainers-=1;
					this.totalWeightCapacity-=cont.getWeight();
					this.maxNumberOfHeavyContainers-=1;
					return true;
				}
				else {
					return false;
				}
			}
			else if (cont.getClass().getName()=="containers.RefrigeratedContainer") {
				if (this.maxNumberOfAllContainers>0 && this.totalWeightCapacity-cont.getWeight()>=0
						&& this.maxNumberOfHeavyContainers>0 && this.maxNumberOfRefrigeratedContainers>0) {
					currentContainers.add(cont);
					this.currentPort.getContainers().remove(cont);
					this.fuelConsumptionPerKM+=cont.consumption();
					this.maxNumberOfAllContainers-=1;
					this.totalWeightCapacity-=cont.getWeight();
					this.maxNumberOfHeavyContainers-=1;
					this.maxNumberOfRefrigeratedContainers-=1;
					return true;
				}
				else {
					return false;
				}
			}
			else {
				if (this.maxNumberOfAllContainers>0 && this.totalWeightCapacity-cont.getWeight()>=0
						&& this.maxNumberOfHeavyContainers>0 && this.maxNumberOfLiquidContainers>0) {
					currentContainers.add(cont);
					this.currentPort.getContainers().remove(cont);
					this.fuelConsumptionPerKM+=cont.consumption();
					this.maxNumberOfAllContainers-=1;
					this.totalWeightCapacity-=cont.getWeight();
					this.maxNumberOfHeavyContainers-=1;
					this.maxNumberOfLiquidContainers-=1;
					return true;
				}
				else {
					return false;
				}
			}
		}
		else {
			return false;
		}
	}	
	
	/**
	 * checks presence of container in ship
	 * if container is present unloads container from ship to port and capacity of ship increases
	 * @param cont the container which is requested to be unloaded
	 */

	@Override
	public boolean unLoad(Container cont) {
		// TODO Auto-generated method stub
		if (this.currentContainers.contains(cont)) {
			currentContainers.remove(cont);
			this.currentPort.getContainers().add(cont);
			if (cont.getClass().getName()=="containers.BasicContainer") {
				this.fuelConsumptionPerKM-=cont.consumption();
				this.maxNumberOfAllContainers+=1;
				this.totalWeightCapacity+=cont.getWeight();
			}
			else if (cont.getClass().getName()=="containers.HeavyContainer") {
				this.fuelConsumptionPerKM-=cont.consumption();
				this.maxNumberOfAllContainers+=1;
				this.totalWeightCapacity+=cont.getWeight();
				this.maxNumberOfHeavyContainers+=1;
			}
			else if (cont.getClass().getName()=="containers.RefrigeratedContainer") {
				this.maxNumberOfAllContainers+=1;
				this.totalWeightCapacity+=cont.getWeight();
				this.maxNumberOfHeavyContainers+=1;
				this.maxNumberOfRefrigeratedContainers+=1;
				this.fuelConsumptionPerKM-=cont.consumption();
			}
			else {
				this.maxNumberOfAllContainers+=1;
				this.totalWeightCapacity+=cont.getWeight();
				this.maxNumberOfHeavyContainers+=1;
				this.maxNumberOfLiquidContainers+=1;
				this.fuelConsumptionPerKM-=cont.consumption();
			}
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * gets port where ship is located
	 * @return port where ship is located
	 */
	public Port getCurrentPort() {
		return currentPort;
	}
	/**
	 * sets new port where ship is located
	 * @param currentPort port where ship is located
	 */
	public void setCurrentPort(Port currentPort) {
		this.currentPort = currentPort;
	}
	/**
	 * gets distinctive number of each ship
	 * @return distinctive number of each ship
	 */
	public int getID() {
		return ID;
	}
	/**
	 * gets how many liters of fuel have left in the ship
	 * @return how many liters of fuel have left in the ship
	 */
	public double getFuel() {
		return fuel;
	}
	/**
	 * checks presence of container type in ship which is requested by user 
	 * @param a name of container type
	 * @return correctness of presence 
	 */
	public boolean containerCheck(String a) {
		for (Container j: this.currentContainers) {
			if (j.getClass().getName()==a) {
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public int compareTo(Ship o) {
		// TODO Auto-generated method stub
		return this.ID - o.ID;
	}
	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

