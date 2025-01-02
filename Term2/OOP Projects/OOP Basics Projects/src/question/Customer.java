
package question;

public class Customer {
	
	//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

	private int ID;
	private String name;
	private int age;
	private Operator operator;
	private Bill bill;
	private int talkingTime;
	private int messageQuantity;
	private double networkUsed;
	
	protected Customer (int ID, String name, int age, Operator operator, double LimitingAmount) {
		
		this.ID=ID;
		this.name=name;
		this.age=age;
		this.operator=operator;
		this.bill=new Bill(LimitingAmount);
		this.talkingTime=0;
		this.messageQuantity=0;
		this.networkUsed=0;
	}
	
	protected void talk (int minute, Customer other) {
		
		other.talkingTime=other.talkingTime+minute;
		this.talkingTime=this.talkingTime+minute;
		this.getOperator().setTalkingTime(minute+this.getOperator().getTalkingTime());
		other.getOperator().setTalkingTime(minute+other.getOperator().getTalkingTime());
		
	}
	
	protected void message (int quantity, Customer other) {
		this.messageQuantity=this.messageQuantity+quantity;
		this.getOperator().setMessageQuantity(quantity+this.getOperator().getMessageQuantity());
	}
	
	protected void connection (double amount) {
		this.networkUsed=this.networkUsed+amount;
		this.getOperator().setNetworkUsed(amount+this.getOperator().getNetworkUsed());
	}
	
	protected int getAge() {
		return age;
	}
	protected void setAge(int age) {
		this.age = age;
	}
	protected Operator getOperator() {
		return operator;
	}
	protected void setOperator(Operator operator) {
		this.operator = operator;
	}
	protected Bill getBill() {
		return bill;
	}
	protected void setBill(Bill bill) {
		this.bill = bill;
	}

	protected int getTalkingTime() {
		return talkingTime;
	}

	protected int getMessageQuantity() {
		return messageQuantity;
	}

	protected double getNetworkUsed() {
		return networkUsed;
	}

	protected String getName() {
		return name;
	}
	
	
	

	//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
}

