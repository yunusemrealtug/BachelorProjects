package elements;

public class Transaction {
	
	private final SellingOrder sellingOrder;
	private final BuyingOrder buyingOrder;
	
	
	public Transaction (SellingOrder sellingOrder, BuyingOrder buyingOrder) {
		this.sellingOrder=sellingOrder;
		this.buyingOrder=buyingOrder;
	}
}
