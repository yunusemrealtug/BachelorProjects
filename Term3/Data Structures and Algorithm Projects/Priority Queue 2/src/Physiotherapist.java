
public class Physiotherapist implements Comparable <Physiotherapist> {
	
	private double time;
	private int id;
	
	public Physiotherapist (int id, double time){
		
		this.id=id;
		this.time=time;
		
	}
	

	public double getTime() {
	return time;
}


	@Override
	public int compareTo(Physiotherapist theOther) {
		// TODO Auto-generated method stub
		if (this.id<theOther.id) {
			return -1;
		}
		else if (this.id>theOther.id) {
			return 1;
		}
		return 0;
}

}
