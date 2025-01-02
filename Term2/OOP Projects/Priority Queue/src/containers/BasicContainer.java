
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;
/**
 * the class for basic containers
 * @author Yunus Emre
 *
 */

public class BasicContainer extends Container {
	/**
	 * constructs a basic container
	 * @param ID distinctive number of each basic container
	 * @param weight weight of each basic container
	 */
	public BasicContainer(int ID, int weight) {
		super(ID, weight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double consumption() {
		// TODO Auto-generated method stub
		return 2.50*this.getWeight();
	}

	@Override
	public boolean equals(Container other) {
		// TODO Auto-generated method stub
		return super.equals(other);
	}
	
	

}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

