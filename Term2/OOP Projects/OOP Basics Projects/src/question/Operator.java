
package question;

public class Operator {
	//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

	private int ID;
	private double talkingCharge;
	private double messageCost;
	private double networkCharge;
	private int discountRate;
	private int talkingTime;
	private int messageQuantity;
	private double networkUsed;
	
	
	protected Operator (int ID, double talkingCharge, double messageCost, double networkCharge, int discountRate) {
		this.ID=ID;
		this.talkingCharge=talkingCharge;
		this.messageCost=messageCost;
		this.networkCharge=networkCharge;
		this.discountRate=discountRate;
		this.talkingTime=0;
		this.messageQuantity=0;
		this.networkUsed=0;
		
	
	}
	
	protected double calculateTalkingCost (int minute, Customer customer) {
		
		if (customer.getAge()>65) {
			double a=100;
			double discount=(a-this.discountRate)/100;
			
			return minute*discount*this.talkingCharge;
		}
			
		else if (customer.getAge()<18) {
			
			double a=100;
			double discount=(a-this.discountRate)/100;
			
			return minute*discount*this.talkingCharge;
		}
		else {
			
			return minute*this.talkingCharge;
		}	
	}
	
	protected double calculateMessageCost(int quantity, Customer customer, Customer other) {
		
		if (customer.getOperator()==other.getOperator()) {
			double a=100;
			double discount=(a-this.discountRate)/100;
			
			return quantity*discount*this.messageCost;
		}
		else {
			
			return quantity*this.messageCost;
		}	
	}
	
	
	protected double calculateNetworkCost(double amount) {
		
		return amount*this.networkCharge;
	}

	protected double getTalkingCharge() {
		return talkingCharge;
	}

	protected void setTalkingCharge(double talkingCharge) {
		this.talkingCharge = talkingCharge;
	}

	protected double getMessageCost() {
		return messageCost;
	}

	protected void setMessageCost(double messageCost) {
		this.messageCost = messageCost;
	}

	protected double getNetworkCharge() {
		return networkCharge;
	}

	protected void setNetworkCharge(double networkCharge) {
		this.networkCharge = networkCharge;
	}

	protected int getDiscountRate() {
		return discountRate;
	}

	protected void setDiscountRate(int discountRate) {
		this.discountRate = discountRate;
	}

	protected int getTalkingTime() {
		return talkingTime;
	}

	protected void setTalkingTime(int talkingTime) {
		this.talkingTime = talkingTime;
	}

	protected int getMessageQuantity() {
		return messageQuantity;
	}

	protected void setMessageQuantity(int messageQuantity) {
		this.messageQuantity = messageQuantity;
	}

	protected double getNetworkUsed() {
		return networkUsed;
	}

	protected void setNetworkUsed(double networkUsed) {
		this.networkUsed = networkUsed;
	}
	
	
	
	
	

	//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
}

