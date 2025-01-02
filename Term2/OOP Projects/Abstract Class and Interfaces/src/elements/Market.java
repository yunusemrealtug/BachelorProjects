package elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Market {
	
	private final PriorityQueue<SellingOrder> sellingOrders;
	private final PriorityQueue<BuyingOrder> buyingOrders;
	private final ArrayList<Transaction> transactions;
	private final double fee;
	private int numberOfInvalidOperations;
	private int numberOfTransactions;
	
	public Market(int fee) {
		this.sellingOrders=new PriorityQueue<SellingOrder>();
		this.buyingOrders=new PriorityQueue<BuyingOrder>();
		this.transactions=new ArrayList<Transaction>();
		this.fee=fee;
		numberOfInvalidOperations=0;
		numberOfTransactions=0;
	}
	
	public void giveSellOrder(SellingOrder order) {
		sellingOrders.offer(order);
		
	}
	public void setNumberOfInvalidOperations() {
		this.numberOfInvalidOperations+= 1;
	}

	public void setNumberOfTransactions() {
		this.numberOfTransactions+=1;
	}

	public void giveBuyOrder(BuyingOrder order) {
		buyingOrders.offer(order);
		
	}
	public void makeOpenMarketOperation(double price) {
		if (buyingOrders.peek()!=null && price<=this.buyingOrders.peek().price) {
			SellingOrder o1=new SellingOrder(0, buyingOrders.peek().amount, buyingOrders.peek().price);
			this.giveSellOrder(o1);	
		}
		else if (sellingOrders.peek()!=null && price>=this.sellingOrders.peek().price) {
			BuyingOrder o1=new BuyingOrder(0, sellingOrders.peek().amount, sellingOrders.peek().price);
			this.giveBuyOrder(o1);
		}
	}
	public void checkTransactions(ArrayList<Trader> traders) {
		if (sellingOrders.peek()!=null && buyingOrders.peek()!=null) {
			while (sellingOrders.peek()!=null && buyingOrders.peek()!=null && sellingOrders.peek().getPrice()<=buyingOrders.peek().getPrice() ) {
				if (buyingOrders.peek().amount>sellingOrders.peek().amount) {
					traders.get(buyingOrders.peek().traderID).getWallet().setBlockedDollars(-1*sellingOrders.peek().amount*(buyingOrders.peek().price-sellingOrders.peek().price));
					traders.get(buyingOrders.peek().traderID).getWallet().setDollars(sellingOrders.peek().amount*(buyingOrders.peek().price-sellingOrders.peek().price));
					traders.get(buyingOrders.peek().traderID).buy(sellingOrders.peek().amount, sellingOrders.peek().getPrice(), this);
					traders.get(sellingOrders.peek().traderID).sell(sellingOrders.peek().amount, sellingOrders.peek().getPrice(), this);
				}
				else {
					traders.get(buyingOrders.peek().traderID).getWallet().setBlockedDollars(-1*buyingOrders.peek().amount*(buyingOrders.peek().price-sellingOrders.peek().price));
					traders.get(buyingOrders.peek().traderID).getWallet().setDollars(buyingOrders.peek().amount*(buyingOrders.peek().price-sellingOrders.peek().price));
					traders.get(buyingOrders.peek().traderID).buy(buyingOrders.peek().amount, sellingOrders.peek().getPrice(), this);
					traders.get(sellingOrders.peek().traderID).sell(buyingOrders.peek().amount, sellingOrders.peek().getPrice(), this);
				}
			}
		}
	}

	public PriorityQueue<SellingOrder> getSellingOrders() {
		return sellingOrders;
	}

	public PriorityQueue<BuyingOrder> getBuyingOrders() {
		return buyingOrders;
	}

	public int getNumberOfInvalidOperations() {
		return numberOfInvalidOperations;
	}

	public int getNumberOfTransactions() {
		return numberOfTransactions;
	}

	public double getFee() {
		return fee;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
}
