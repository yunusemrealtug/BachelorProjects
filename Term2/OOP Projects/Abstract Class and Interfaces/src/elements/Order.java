package elements;

public class Order {
	
	double amount;
	final double price;
	final int traderID;
	
	public Order(int traderID, double amount, double price) {
		
		this.amount=amount;
		this.price=price;
		this.traderID=traderID;
		
	}

	public double getPrice() {
	
		return price;
	}

	public double getAmount() {
		return amount;
	}

}


