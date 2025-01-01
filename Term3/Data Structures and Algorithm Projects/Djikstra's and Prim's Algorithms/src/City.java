import java.util.ArrayList;

public class City implements Comparable <City> {
	
	public ArrayList<Path> getSourceRoads() {
		return sourceRoads;
	}

	private final String id;
	private ArrayList<Path> roads;
	private ArrayList<Path> sourceRoads;
	private int distance;
	private boolean visited;
	private boolean availible;
	private String shortestPath;
	
	public City (String id, ArrayList<Path> roads, ArrayList<Path> sourceRoads, int distance) {
	
		this.id=id;
		this.roads=roads;
		this.sourceRoads=sourceRoads;
		this.distance=distance;
		this.visited=false;
		this.availible=false;
		this.shortestPath="";
	}

	public void setAvailibility(boolean availibility) {
		this.availible = availibility;
	}

	public boolean isAvailible() {
		return availible;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public ArrayList<Path> getRoads() {
		return roads;
	}
	
	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	

	public String getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(String shortestPath) {
		this.shortestPath = shortestPath+" "+this.id;
	}

	@Override
	public int compareTo(City o) {
		// TODO Auto-generated method stub
		if (this.distance<o.distance) {
			return -1;
		}
		else {
			return 1;
		}
	}

	

	

	
	
	

}
