
import java.util.LinkedList;

public class Vertex implements Comparable<Vertex> {
	
	private LinkedList<Edge> edges;
	private String type;
	private Edge sourceEdge;
	private int visitNum;
	
	public Vertex (LinkedList<Edge> linkedList, String type) {
		this.edges=linkedList;
		this.type=type;
		this.sourceEdge=null;
		this.visitNum=0;
	}

	public int isVisited() {
		return visitNum;
	}

	public void setVisited(int visited) {
		this.visitNum = visited;
	}

	public Edge getSourceEdge() {
		return sourceEdge;
	}

	public void setSourceEdge(Edge sourceEdge) {
		this.sourceEdge = sourceEdge;
	}

	public String getType() {
		return type;
	}

	public LinkedList<Edge> getEdges() {
		return edges;
	}

	
	@Override
	public int compareTo(Vertex o) {
		// TODO Auto-generated method stub
		if (this.getEdges().getFirst().getCapacity()>o.getEdges().getFirst().getCapacity()) {
			return -1;
		}
		else {
			return 1;
		}
	}

}
