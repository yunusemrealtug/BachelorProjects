
public class Facility implements Comparable <Facility> {
	
	private Player player;
	private double time;
	private double duration;
	private final String type;

	public Facility (Player player, double time, double duration, String type){
		
		this.player=player;
		this.time=time;
		this.duration=duration;
		this.type=type;
		
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public String getType() {
		return type;
	}
	
	@Override
	public int compareTo(Facility theOther) {
		// TODO Auto-generated method stub
		if (this.time<theOther.time) {
			return -1;
		}
		else if (this.time>theOther.time) {
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