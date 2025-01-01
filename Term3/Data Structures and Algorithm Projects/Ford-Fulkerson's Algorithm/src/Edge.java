
public class Edge implements Comparable <Edge>{
	
	private Vertex targetVertex;
	private Vertex sourceVertex;
	private int capacity;
	private int flow;
	private Edge opposentPath;
	
	public Edge (Vertex sourceVertex, Vertex targetVertex, int capacity) {
		this.targetVertex=targetVertex;
		this.sourceVertex=sourceVertex;
		this.capacity=capacity;
		this.flow=0;
		this.opposentPath=null;
		}

	public Edge getOpposentPath() {
		return opposentPath;
	}

	public void setOpposentPath(Edge opposentPath) {
		this.opposentPath = opposentPath;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getFlow() {
		return flow;
	}

	public void setFlow(int flow) {
		this.flow = flow;
	}

	public Vertex getTargetVertex() {
		return targetVertex;
	}

	public Vertex getSourceVertex() {
		return sourceVertex;
	}

	@Override
	public int compareTo(Edge o) {
		// TODO Auto-generated method stub
		if (this.capacity<o.capacity) {
			return -1;
		}
		else {
			return 1;
		}
	}
	
}
