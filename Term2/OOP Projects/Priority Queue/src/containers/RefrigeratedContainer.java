
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;
/**
 * the class for refrigerated containers
 * @author Yunus Emre
 *
 */

public class RefrigeratedContainer extends HeavyContainer {
	/**
	 * constructs a refrigerated container
	 * @param ID distinctive number of each refrigerated container
	 * @param weight weight of each refrigerated container
	 */
	public RefrigeratedContainer(int ID, int weight) {
		super(ID, weight);
		// TODO Auto-generated constructor stub
	}
	
	public double consumption() {
		// TODO Auto-generated method stub
		return this.getWeight()*5.00;
	}

	@Override
	public boolean equals(Container other) {
		// TODO Auto-generated method stub
		return super.equals(other);
	}
	
	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

