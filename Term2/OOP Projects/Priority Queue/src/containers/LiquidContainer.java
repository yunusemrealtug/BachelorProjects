
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;
/**
 * the class for liquid containers
 * @author Yunus Emre
 *
 */

public class LiquidContainer extends HeavyContainer {
	/**
	 * constructs a liquid container
	 * @param ID distinctive number of each liquid container
	 * @param weight weight of each liquid container
	 */

	public LiquidContainer(int ID, int weight) {
		super(ID, weight);
		// TODO Auto-generated constructor stub
	}
	public double consumption() {
		// TODO Auto-generated method stub
		return this.getWeight()*4.00;
	}
	@Override
	public boolean equals(Container other) {
		// TODO Auto-generated method stub
		return super.equals(other);
	}
	

}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

