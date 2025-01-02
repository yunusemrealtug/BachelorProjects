package elements;

public class Wallet {
	
	private double dollars;
	private double coins;
	private double blockedDollars;
	private double blockedCoins;
	
	public Wallet(double dollars, double coins) {
		
		this.dollars=dollars;
		this.coins=coins;
		blockedCoins=0;
		blockedDollars=0;
		
	}

	public void setBlockedDollars(double blockedDollars) {
		this.blockedDollars+= blockedDollars;
	}

	public void setBlockedCoins(double blockedCoins) {
		this.blockedCoins+= blockedCoins;
	}



	public double getDollars() {
		return dollars;
	}



	public double getCoins() {
		return coins;
	}

	public double getBlockedDollars() {
		return blockedDollars;
	}

	public double getBlockedCoins() {
		return blockedCoins;
	}

	public void setDollars(double dollars) {
		this.dollars+= dollars;
	}

	public void setCoins(double coins) {
		this.coins+= coins;
	}

}
