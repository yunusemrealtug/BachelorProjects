package executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import elements.Trader;
import elements.BuyingOrder;
import elements.Market;
import elements.Order;
import elements.SellingOrder;

public class Main {
	public static Random myRandom;
	public static void main (String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		int seed=in.nextInt();
		myRandom=new Random(seed);
		int transactionFee=in.nextInt();
		Market m1=new Market(transactionFee);
		int numberOfPCoinUsers=in.nextInt();
		int numberOfQueries=in.nextInt();
		ArrayList<Trader> traders=new ArrayList();
		
		for (int i = 0; i < numberOfPCoinUsers ; i++) {
			Trader t1=new Trader (in.nextInt(), in.nextInt());
			traders.add(t1);
		}
		
		for (int i = 0; i < numberOfQueries ; i++) {
			int number=in.nextInt();
			if (number==10) {
				int traderId=in.nextInt();
				int price=in.nextInt();
				int amount=in.nextInt();
				if (traders.get(traderId).getWallet().getDollars()>=price*amount) {
					BuyingOrder o1=new BuyingOrder(traderId, amount, price);
					m1.giveBuyOrder(o1);
					traders.get(traderId).getWallet().setBlockedDollars(price*amount);
					traders.get(traderId).getWallet().setDollars(price*amount*-1);
					m1.checkTransactions(traders);
				}
				else {
					m1.setNumberOfInvalidOperations();
				}
			}
			else if (number==11) {
				int traderId=in.nextInt();
				int amount=in.nextInt();
				if (m1.getSellingOrders().peek()!=null) {
					if (traders.get(traderId).getWallet().getDollars()>m1.getSellingOrders().peek().getPrice()*amount) {
						BuyingOrder o1=new BuyingOrder(traderId, amount, m1.getSellingOrders().peek().getPrice());
						m1.giveBuyOrder(o1);
						traders.get(traderId).getWallet().setBlockedDollars(m1.getSellingOrders().peek().getPrice()*amount);
						traders.get(traderId).getWallet().setDollars(m1.getSellingOrders().peek().getPrice()*amount*-1);
						m1.checkTransactions(traders);
					}
					else {
						m1.setNumberOfInvalidOperations();
					}
				}
				else {
					m1.setNumberOfInvalidOperations();
				}
			}
			else if (number==20) {
				int traderId=in.nextInt();
				int price=in.nextInt();
				int amount=in.nextInt();
				if (traders.get(traderId).getWallet().getCoins()>=amount) {
					SellingOrder o1=new SellingOrder(traderId, amount, price);
					m1.giveSellOrder(o1);
					traders.get(traderId).getWallet().setBlockedCoins(amount);
					traders.get(traderId).getWallet().setCoins(amount*-1);
					m1.checkTransactions(traders);
				}
				else {
					m1.setNumberOfInvalidOperations();
				}
			}
			else if (number==21) {
				int traderId=in.nextInt();
				int amount=in.nextInt();
				if (m1.getBuyingOrders().peek()!=null) {
					if (traders.get(traderId).getWallet().getCoins()>amount) {
						SellingOrder o1=new SellingOrder(traderId, amount, m1.getBuyingOrders().peek().getPrice());
						m1.giveSellOrder(o1);
						traders.get(traderId).getWallet().setBlockedCoins(amount);
						traders.get(traderId).getWallet().setCoins(amount*-1);
						m1.checkTransactions(traders);
					}
					else {
						m1.setNumberOfInvalidOperations();
					}
				}
				else {
					m1.setNumberOfInvalidOperations();
				}
			}
			else if (number==3) {
				traders.get(in.nextInt()).getWallet().setDollars(in.nextInt());
			}
			else if (number==4) {
				int id=in.nextInt();
				int withdrawal=in.nextInt();
				if (traders.get(id).getWallet().getDollars()>=withdrawal) {
					traders.get(id).getWallet().setDollars(withdrawal*-1);
				}
				else {
					m1.setNumberOfInvalidOperations();
				}
			}
			else if (number==5) {
				int requestedNumber=in.nextInt();
				out.print("Trader ");
				out.print(requestedNumber);
				out.print(": ");
				double doublex=traders.get(requestedNumber).getWallet().getDollars()+traders.get(requestedNumber).getWallet().getBlockedDollars();
				out.printf("%.5f", doublex);
				out.print("$");
				out.print(" ");
				double doublec=traders.get(requestedNumber).getWallet().getCoins()+traders.get(requestedNumber).getWallet().getBlockedCoins();
				out.printf("%.5f", doublec);
				out.println("PQ");
			}
			else if (number==777) {
				for (Trader b : traders) {
					b.getWallet().setCoins(myRandom.nextDouble()*10);
				}
			}
			else if (number==666) {
				int price=in.nextInt();
				if (m1.getBuyingOrders().peek()!=null && price<=m1.getBuyingOrders().peek().getPrice()) {
					while (m1.getBuyingOrders().peek()!=null && price<=m1.getBuyingOrders().peek().getPrice()) {
						traders.get(0).getWallet().setBlockedCoins(m1.getBuyingOrders().peek().getAmount());
						traders.get(0).getWallet().setCoins(m1.getBuyingOrders().peek().getAmount()*-1);
						m1.makeOpenMarketOperation(price);
						m1.checkTransactions(traders);
					}
					
				}
				else if (m1.getSellingOrders().peek()!=null && price>=m1.getSellingOrders().peek().getPrice()) {
					while (m1.getSellingOrders().peek()!=null && price>=m1.getSellingOrders().peek().getPrice()) {
						m1.makeOpenMarketOperation(price);
						traders.get(0).getWallet().setBlockedDollars(m1.getSellingOrders().peek().getPrice()*m1.getSellingOrders().peek().getAmount());
						traders.get(0).getWallet().setDollars(m1.getSellingOrders().peek().getPrice()*m1.getSellingOrders().peek().getAmount()*-1);
						m1.checkTransactions(traders);
						
					}
				}
			}
			else if (number==500) {
				double totalDollars=0;
				double totalCoins=0;
				for (int k=0; k<traders.size(); k++) {
					totalDollars+=traders.get(k).getWallet().getBlockedDollars();
					totalCoins+=traders.get(k).getWallet().getBlockedCoins();
				}
				out.print("Current market size: ");
				out.printf("%.5f", totalDollars);
				out.print(" ");
				out.printf("%.5f", totalCoins);
				out.println();
			}
			else if (number==501) {
				out.print("Number of successful transactions: ");
				out.println(m1.getNumberOfTransactions());
			}
			else if (number==502) {
				out.print("Number of invalid queries: ");
				out.println(m1.getNumberOfInvalidOperations());
			}
			else if (number==505) {
				if (m1.getBuyingOrders().peek()!=null) {
					if (m1.getSellingOrders().peek()!=null) {
						double peekBuying=m1.getBuyingOrders().peek().getPrice();
						double peekSelling=m1.getSellingOrders().peek().getPrice();
						out.print("Current prices: ");
						out.printf("%.5f", peekBuying);
						out.print(" ");
						out.printf("%.5f", peekSelling);
						out.print(" ");
						out.printf("%.5f", (peekSelling+peekBuying)/2);
						out.println();
					}
					else {
						double peekBuying=m1.getBuyingOrders().peek().getPrice();
						double peekSelling=0;
						out.print("Current prices: ");
						out.printf("%.5f", peekBuying);
						out.print(" ");
						out.printf("%.5f", peekSelling);
						out.print(" ");
						out.printf("%.5f", peekBuying);
						out.println();
					}
				}
				else {
					if (m1.getSellingOrders().peek()!=null) {
						double peekBuying=0;
						double peekSelling=m1.getSellingOrders().peek().getPrice();
						out.print("Current prices: ");
						out.printf("%.5f", peekBuying);
						out.print(" ");
						out.printf("%.5f", peekSelling);
						out.print(" ");
						out.printf("%.5f", peekSelling);
						out.println();
					}
					else {
						double peekBuying=0;
						double peekSelling=0;
						out.print("Current prices: ");
						out.printf("%.5f", peekBuying);
						out.print(" ");
						out.printf("%.5f", peekSelling);
						out.print(" ");
						out.printf("%.5f", peekSelling);
						out.println();
					}
				}
			}
			else if (number==555) {
				for (int j = 0; j < numberOfPCoinUsers ; j++) {
					out.print("Trader ");
					out.print(j);
					out.print(": ");
					double doublex=traders.get(j).getWallet().getDollars()+traders.get(j).getWallet().getBlockedDollars();
					out.printf("%.5f", doublex);
					out.print("$");
					out.print(" ");
					double doublec=traders.get(j).getWallet().getCoins()+traders.get(j).getWallet().getBlockedCoins();
					out.printf("%.5f", doublec);
					out.println("PQ");
				}
			}		
		}
	}	
}


