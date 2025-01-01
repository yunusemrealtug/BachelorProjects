
public class Training implements Comparable <Training>{
	
	private Player player;
	private final double entranceTime;
	private final double duration;
	private double queueDuration;
	private double leavingTime;

	public Training (Player player, double time, double duration){
		
		this.player=player;
		this.entranceTime=time;
		this.duration=duration;
		queueDuration=0;
		leavingTime=this.entranceTime+this.queueDuration+this.duration;
		
	}
	
	public void adjustTime () {
		leavingTime=this.entranceTime+this.queueDuration+this.duration;
	}
	
	
	
	public double getQueueDuration() {
		return queueDuration;
	}


	public void setQueueDuration(double queueDuration) {
		this.queueDuration = queueDuration;
	}


	public double getEntranceTime() {
		return entranceTime;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}


	public void setLeavingTime(double leavingTime) {
		this.leavingTime = leavingTime;
	}


	public double getDuration() {
		return duration;
	}
	

	public double getLeavingTime() {
		return leavingTime;
	}


	public Player getPlayer() {
		return player;
	}


	@Override
	public int compareTo(Training theOther) {
		// TODO Auto-generated method stub
		if (this.entranceTime<theOther.entranceTime) {
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
