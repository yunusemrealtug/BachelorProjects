
public class Path implements Comparable <Path> {
	
	private final int weight;
	private final String sourceCity;
	private final String targetCity;
	private boolean availible;
	

	public boolean isAvailible() {
		return availible;
	}


	public void setAvailible(boolean availible) {
		this.availible = availible;
	}


	public Path (int weight, String sourceCity, String targetCity) {
		this.sourceCity=sourceCity;
		this.targetCity=targetCity;
		this.weight=weight;
		this.availible=false;
	}


	public String getTargetCity() {
		return targetCity;
	}


	public int getWeight() {
		return weight;
	}


	public String getSourceCity() {
		return sourceCity;
	}


	@Override
	public int compareTo(Path o) {
		// TODO Auto-generated method stub
		if (this.weight<o.weight) {
			return -1;
		}
		else {
			return 1;
		}
	}
	

	

	
	
	
}
