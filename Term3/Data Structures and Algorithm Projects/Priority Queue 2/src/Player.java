
public class Player  {
	
	private final int level;
	private final int id;
	private int massageCount;
	private boolean inEvent;
	private double totalQueueDuration1;
	private double totalQueueDuration2;
	

	public double getTotalQueueDuration2() {
		return totalQueueDuration2;
	}


	public void setTotalQueueDuration2(double totalQueueDuration2) {
		this.totalQueueDuration2+=totalQueueDuration2;
	}


	public Player (int id, int level){
		this.id=id;
		this.level=level;
		this.massageCount=0;
		inEvent=false;
		
	}
	
	
	public double getTotalQueueDuration1() {
		return totalQueueDuration1;
	}
	

	public void setTotalQueueDuration1(double queueDuration) {
		this.totalQueueDuration1+=queueDuration;
	}


	public int getMassageCount() {
		return massageCount;
	}

	public void setMassageCount(int massageCount) {
		this.massageCount = massageCount;
	}


	public boolean getInEvent() {
		return inEvent;
	}

	public void setInEvent(boolean inEvent) {
		this.inEvent = inEvent;
	}


	public int getLevel() {
		return level;
	}

	
	public int getId() {
		return id;
	}
	
	

}
