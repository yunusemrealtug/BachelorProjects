
public class Massage implements Comparable <Massage>{
	
	private Player player;
	private final double entranceTime;
	private final double duration;
	private double queueDuration;
	private double leavingTime;
	

	public Massage (Player player, double time, double duration){
		
		this.player=player;
		this.entranceTime=time;
		this.duration=duration;
		queueDuration=0;
		leavingTime=this.entranceTime+this.queueDuration+this.duration;
		
		
		
	}
	
	public void adjustTime () {
		leavingTime=this.entranceTime+this.queueDuration+this.duration;
	}
	
	public Player getPlayer() {
		return player;
	}



	public void setPlayer(Player player) {
		this.player = player;
	}



	public double getQueueDuration() {
		return queueDuration;
	}



	public void setQueueDuration(double queueDuration) {
		this.queueDuration = queueDuration;
	}

	public double getLeavingTime() {
		return leavingTime;
	}



	public void setLeavingTime(double leavingTime) {
		this.leavingTime = leavingTime;
	}



	public double getEntranceTime() {
		return entranceTime;
	}



	public double getDuration() {
		return duration;
	}



	public int compareTo(Massage theOther) {
		// TODO Auto-generated method stub
		if (this.player.getLevel()>theOther.player.getLevel()) {
			return -1;
		}
		else if (this.player.getLevel()<theOther.player.getLevel()) {
			return 1;
		}
		else if (this.entranceTime<theOther.entranceTime) {
			return -1;
		}
		else if (this.entranceTime>theOther.entranceTime) {
			return 1;
		}
		else if (this.player.getId()<theOther.player.getId()) {
			return -1;
		}
		else if (this.player.getId()>theOther.player.getId()) {
			return 1;
		}
		return 0;
	}


}
