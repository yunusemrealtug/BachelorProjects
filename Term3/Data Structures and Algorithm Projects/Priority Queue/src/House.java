
public class House implements Comparable <House> {
	
	private final int id;
	private int duration;
	private final double rating;
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getRating() {
		return rating;
	}

	public House (int id, int duration, double rating){
		this.id=id;
		this.duration=duration;
		this.rating=rating;
		
	}

	@Override
	public int compareTo(House theOther) {
		// TODO Auto-generated method stub
		if (this.id<theOther.id) {
			return -1;
		}
		else if (this.id>theOther.id) {
			return 1;
		}
		return 0;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

}
