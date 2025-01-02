
package question;

public class Bill {

	//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

	private double limitingAmount;
	private double currentDebt;
	private double payments;
	
	protected Bill (double limitingAmount) {
		this.limitingAmount=limitingAmount;
		this.currentDebt=0;
		this.payments=0;
	}
	
	protected boolean check (double amount) {
		if (amount+this.currentDebt<=this.limitingAmount) {
			return true;
		}
		else {
			return false;
		}
	}
	
	protected void add (double amount) {
		this.currentDebt=this.currentDebt+amount;
		
	}
	
	protected void pay (double amount) {
		if (amount >=this.currentDebt) {
			this.payments=this.payments+this.currentDebt;
			this.currentDebt=0;
			
		}
		else {
			this.currentDebt=this.currentDebt-amount;
			this.payments=this.payments+amount;
		}
		
	}
	
	protected void changeTheLimit (double amount) {
		
		if (amount>=this.currentDebt) {
			this.limitingAmount=amount;
		}
		
	}

	protected double getLimitingAmount() {
		return limitingAmount;
	}

	protected void setLimitingAmount(double limitingAmount) {
		this.limitingAmount = limitingAmount;
	}

	protected double getCurrentDebt() {
		return currentDebt;
	}

	protected void setCurrentDebt(double currentDebt) {
		this.currentDebt = currentDebt;
	}

	protected double getPayments() {
		return payments;
	}
	
	
	

	//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
}

