package elements;

public class SellingOrder extends Order implements Comparable<Order> {

	public SellingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(Order o) {
		// TODO Auto-generated method stub
		if (this.getPrice()<o.getPrice()) {
			return -1;
		}
		else if (this.getPrice()>o.getPrice()) {
			return 1;
		}
		else {
			if (this.amount>o.amount) {
				return -1;
			}
			else if (this.amount<o.amount) {
				return 1;
			}
			else {
				if (this.traderID<o.traderID) {
					return -1;
				}
				else {
					return 1;
				}
			}
		}
	}

}
