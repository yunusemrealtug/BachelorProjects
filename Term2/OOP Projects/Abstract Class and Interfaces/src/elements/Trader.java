package elements;

import java.util.Collections;

public class Trader {
	
	private final int id;
	private final Wallet wallet;
	public static int numberOfUsers = 0;
	
	public Trader(double dollars, double coins) {
		
		wallet= new Wallet(dollars, coins);
		id=numberOfUsers;
		numberOfUsers+=1;
		
		
	}
	public int sell(double amount, double price, Market market) {
		this.wallet.setDollars(price*amount*((1000-market.getFee())/1000));
		this.wallet.setBlockedCoins(amount*-1);
		market.setNumberOfTransactions();
		Transaction t1=new Transaction(market.getSellingOrders().peek(), market.getBuyingOrders().peek());
		market.getTransactions().add(t1);
		if (market.getSellingOrders().peek().amount>amount) {
			market.getSellingOrders().peek().amount-=amount;
			market.getBuyingOrders().poll();
			
		}
		else if (market.getBuyingOrders().peek().amount>amount) {
			market.getBuyingOrders().peek().amount-=amount;
			market.getSellingOrders().poll();
		}
		else {
			market.getSellingOrders().poll();
			market.getBuyingOrders().poll();
		}
		return 0;
		
	}
	public int buy(double amount, double price, Market market) {
		this.wallet.setCoins(amount);
		this.wallet.setBlockedDollars(price*amount*-1);
		return 0;
		
	}
	
	public int getId() {
		return id;
	}
	public Wallet getWallet() {
		return wallet;
	}
	

}
