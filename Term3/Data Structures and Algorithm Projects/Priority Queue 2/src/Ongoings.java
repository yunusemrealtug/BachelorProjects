
public class Ongoings implements Comparable <Ongoings> {
	
	private Player player;
	private double leavingTime;
	private final String type;

	public Ongoings (Player player, double time, String type){
		
		this.player=player;
		this.leavingTime=time;
		this.type=type;
		
	}

	@Override
	public int compareTo(Ongoings o) {
		// TODO Auto-generated method stub
		if (this.leavingTime<o.leavingTime) {
			return -1;
		}
		else if (this.leavingTime>o.leavingTime) {
			return 1;
		}
		else if (this.player.getId()<o.player.getId()) {
			return -1;
		}
		else if (this.player.getId()>o.player.getId()) {
			return 1;
		}
		return 0;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public double getLeavingTime() {
		return leavingTime;
	}

	public void setLeavingTime(double leavingTime) {
		this.leavingTime = leavingTime;
	}

	public String getType() {
		return type;
	}

}
