
public class Physiotherapy implements Comparable <Physiotherapy> {
	
	private Training training;
	private Physiotherapist physiotherapist;
	private double entranceTime;
	private double duration;
	private double queueDuration;
	private double leavingTime;

	

	public Physiotherapy (Training training){
		
		this.training=training;
		this.physiotherapist=null;
		entranceTime=this.training.getLeavingTime();
		this.queueDuration=0;
		
	
	}
	
	public void adjustTime () {
		entranceTime=this.training.getLeavingTime();
		if (this.physiotherapist!=null) {
			this.duration=this.physiotherapist.getTime();
			this.leavingTime=this.entranceTime+this.queueDuration+this.duration;
		}
		
	}
	
	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	public Physiotherapist getPhysiotherapist() {
		return physiotherapist;
	}

	public void setPhysiotherapist(Physiotherapist physiotherapist) {
		this.physiotherapist = physiotherapist;
	}

	public double getEntranceTime() {
		return entranceTime;
	}

	public void setEntranceTime(double entranceTime) {
		this.entranceTime = entranceTime;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
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

	@Override
	public int compareTo(Physiotherapy theOther) {
		// TODO Auto-generated method stub
		if (this.training.getDuration()>theOther.training.getDuration()) {
			return -1;
		}
		else if (this.training.getDuration()<theOther.training.getDuration()) {
			return 1;
		}
		else if (this.entranceTime<theOther.entranceTime) {
			return -1;
		}
		else if (this.entranceTime>theOther.entranceTime) {
			return 1;
		}
		else if (this.training.getPlayer().getId()<theOther.training.getPlayer().getId()) {
			return -1;
		}
		else if (this.training.getPlayer().getId()>theOther.training.getPlayer().getId()) {
			return 1;
		}
		return 0;
	}

}
