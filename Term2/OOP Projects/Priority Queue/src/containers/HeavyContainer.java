
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;
/**
 * the class for heavy containers
 * @author Yunus Emre
 *
 */

public class HeavyContainer extends Container {
	/**
	 * constructs a heavy container
	 * @param ID distinctive number of each heavy container
	 * @param weight weight of each heavy container
	 */

	public HeavyContainer(int ID, int weight) {
		super(ID, weight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double consumption() {
		// TODO Auto-generated method stub
		return 3.00*this.getWeight();
	}

	@Override
	public boolean equals(Container other) {
		// TODO Auto-generated method stub
		return super.equals(other);
	}
	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

