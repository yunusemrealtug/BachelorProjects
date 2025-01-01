import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Scanner;

public class project4main {
	
	public static int numOfRedTrains;
	public static int numOfRedReindeers;
	public static int numOfGreenTrains;
	public static int numOfGreenReindeers;
	public static int numOfBags;
	public static int atTheHand;
	public static int totalTrainCapacity;
	public static int totalGreenTrainCapacity;
	public static int totalRedTrainCapacity;
	public static int totalGreenReindeerCapacity;
	public static int totalRedReindeerCapacity;
	public static int totalReindeerCapacity;
	public static int totalCapacity;
	public static int totalRedCapacity;
	public static int totalGreenCapacity;
	public static int totalGifts;
	
	public static Vertex bd;
	public static Vertex be;
	public static Vertex cd;
	public static Vertex ce;
	public static Vertex b;
	public static Vertex c;
	public static Vertex d;
	public static Vertex e;
	public static Vertex s;
	public static Vertex t;
	public static PriorityQueue<Vertex> redTrains;
	public static PriorityQueue<Vertex> greenTrains;
	public static PriorityQueue<Vertex> redReindeers;
	public static PriorityQueue<Vertex> greenReindeers;
	public static PriorityQueue<Edge> edges;
	public static ArrayList<Vertex> vertexes;
	public static ArrayList<Integer> levels;
	public static int visitedNumber;
	
	public static boolean Check (int number1, int number2) {
		if (number1>number2)
			return true;
		else 
			return false;
	}
	private static int checkPath(int flow, Vertex vertex) {
		 
        if(vertex.equals(t))
            return flow;
 
        vertex.setVisited(visitedNumber);
 
        for(Edge edge: vertex.getEdges()){
 
            if(edge.getCapacity()-edge.getFlow() > 0 && edge.getTargetVertex().isVisited() !=visitedNumber){
 
                int bottleNeck = checkPath(Integer.min(flow,edge.getCapacity()-edge.getFlow()),edge.getTargetVertex());
 
                if(bottleNeck > 0){
                    edge.setFlow(edge.getFlow()+bottleNeck);
                    edge.getOpposentPath().setFlow(edge.getOpposentPath().getFlow()-bottleNeck);
                    return bottleNeck;
                }
            }
        }
        return 0;
    }

//	public static int make() {
//		boolean broken=false;
//		while (broken==false) {
//			broken=true;
//			for (int i=0; i<s.getEdges().size(); i++) {
//				Edge a1=s.getEdges().get(i);
//				if (a1.getCapacity()>0) {
//					for (int j=0; j<a1.getTargetVertex().getEdges().size(); j++) {
//						a1.getTargetVertex().setVisited(true);
//						Edge a2=a1.getTargetVertex().getEdges().get(j);
//						if (a2.getCapacity()>0) {
//							for (int k=0; k<a2.getTargetVertex().getEdges().size(); k++) {
//								a2.getTargetVertex().setVisited(true);
//								Edge a3=a2.getTargetVertex().getEdges().get(k);
//								if (a3.getCapacity()>0 && a3.getTargetVertex().getType().equals("t")) {
//									int bottleNeck=Math.min(a1.getCapacity(), a2.getCapacity());
//									bottleNeck=Math.min(bottleNeck, a3.getCapacity());
//									a1.setCapacity(a1.getCapacity()-bottleNeck);
//									a2.getOpposentPath().setCapacity(a2.getOpposentPath().getCapacity()+bottleNeck);
//									a2.setCapacity(a2.getCapacity()-bottleNeck);
//									a3.setCapacity(a3.getCapacity()-bottleNeck);
//									atTheHand+=bottleNeck;
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		return atTheHand;
//	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Locale.setDefault(new Locale("en", "US"));
		PrintStream out = new PrintStream(new File(args[1]));
		Scanner in = new Scanner(new File(args[0]));
		bd=new Vertex(new LinkedList<Edge>(), "bd");
		be=new Vertex(new LinkedList<Edge>(), "be");
		cd=new Vertex(new LinkedList<Edge>(), "cd");
		ce=new Vertex(new LinkedList<Edge>(), "ce");
		s=new Vertex(new LinkedList<Edge>(), "s");
		b=new Vertex(new LinkedList<Edge>(), "b");
		c=new Vertex(new LinkedList<Edge>(), "c");
		d=new Vertex(new LinkedList<Edge>(), "d");
		e=new Vertex(new LinkedList<Edge>(), "e");
		t=new Vertex(new LinkedList<Edge>(), "t");
		Edge l1=new Edge(s, b, 0);Edge l11=new Edge(b,s,0);
		Edge l2=new Edge(s, c, 0);Edge l12=new Edge(c,s,0);
		Edge l3=new Edge(s, d, 0);Edge l13=new Edge(d,s,0);
		Edge l4=new Edge(s, e, 0);Edge l14=new Edge(e,s,0);
		Edge l5=new Edge(s, bd, 0);Edge l15=new Edge(bd,s,0);
		Edge l6=new Edge(s, be, 0);Edge l16=new Edge(be,s,0);
		Edge l7=new Edge(s, cd, 0);Edge l17=new Edge(cd,s,0);
		Edge l8=new Edge(s, ce, 0);Edge l18=new Edge(ce,s,0);
		
		s.getEdges().add(l1);b.getEdges().add(l11);
		s.getEdges().add(l2);c.getEdges().add(l12);
		s.getEdges().add(l3);d.getEdges().add(l13);
		s.getEdges().add(l4);e.getEdges().add(l14);
		s.getEdges().add(l5);bd.getEdges().add(l15);
		s.getEdges().add(l6);be.getEdges().add(l16);
		s.getEdges().add(l7);cd.getEdges().add(l17);
		s.getEdges().add(l8);ce.getEdges().add(l18);
		
		l1.setOpposentPath(l11);l11.setOpposentPath(l1);
		l2.setOpposentPath(l12);l12.setOpposentPath(l2);
		l3.setOpposentPath(l13);l13.setOpposentPath(l3);
		l4.setOpposentPath(l14);l14.setOpposentPath(l4);
		l5.setOpposentPath(l15);l15.setOpposentPath(l5);
		l6.setOpposentPath(l16);l16.setOpposentPath(l6);
		l7.setOpposentPath(l17);l17.setOpposentPath(l7);
		l8.setOpposentPath(l18);l18.setOpposentPath(l8);
		
		
		
		totalTrainCapacity=0;
		totalReindeerCapacity=0;
		totalGreenCapacity=0;
		totalRedCapacity=0;
		totalRedTrainCapacity=0;
		totalGreenTrainCapacity=0;
		totalRedReindeerCapacity=0;
		totalGreenReindeerCapacity=0;
		totalCapacity=0;
		totalGifts=0;
		
		atTheHand=0;
		
		vertexes=new ArrayList<Vertex>();
		levels=new ArrayList<Integer>();
		vertexes.add(s);
		levels.add(0);
		vertexes.add(b);vertexes.add(c);
		vertexes.add(d);vertexes.add(e);
		vertexes.add(be);vertexes.add(bd);
		vertexes.add(ce);vertexes.add(cd);
		
		redTrains=new PriorityQueue<Vertex>();
		greenTrains=new PriorityQueue<Vertex>();
		redReindeers=new PriorityQueue<Vertex>();
		greenReindeers=new PriorityQueue<Vertex>();
		edges=new PriorityQueue<Edge>();
		ArrayList<Vertex> rt=new ArrayList<Vertex>();
		ArrayList<Vertex> gt=new ArrayList<Vertex>();
		ArrayList<Vertex> rr=new ArrayList<Vertex>();
		ArrayList<Vertex> gr=new ArrayList<Vertex>();
		
		numOfGreenTrains=in.nextInt();
		for (int i=0; i<numOfGreenTrains; i++) {
			int capacity=in.nextInt();
			if (capacity!=0) {
				Vertex k=new Vertex(new LinkedList<Edge>(), "gt");
				vertexes.add(k);
				levels.add(2);
				Edge e1=new Edge(b, k, Integer.MAX_VALUE);
				Edge e2=new Edge(k, b, 0);
				b.getEdges().add(e1);
				k.getEdges().add(e2);
				e1.setOpposentPath(e2);
				e2.setOpposentPath(e1);
				Edge e3=new Edge(d, k, Integer.MAX_VALUE);
				Edge e4=new Edge(k, d, 0);
				d.getEdges().add(e3);
				k.getEdges().add(e4);
				e3.setOpposentPath(e4);
				e4.setOpposentPath(e3);
				Edge e7=new Edge(k, t, capacity);
				Edge e8=new Edge(t,k,0);
				k.getEdges().add(e7);
				t.getEdges().add(e8);
				e7.setOpposentPath(e8);
				e8.setOpposentPath(e7);
				Edge e5=new Edge(bd, k, Integer.MAX_VALUE);
				Edge e6=new Edge(k, bd, 0);
				bd.getEdges().add(e5);
				k.getEdges().add(e6);
				e5.setOpposentPath(e6);
				e6.setOpposentPath(e5);
				totalGreenCapacity+=capacity;
				totalTrainCapacity+=capacity;
				totalGreenTrainCapacity+=capacity;
				totalCapacity+=capacity;
				gt.add(k);
			}
		}
		numOfRedTrains=in.nextInt();
		for (int i=0; i<numOfRedTrains; i++) {
			int capacity=in.nextInt();
			if (capacity!=0) {
				Vertex k=new Vertex(new LinkedList<Edge>(), "rt");
				vertexes.add(k);
				levels.add(2);
				Edge e1=new Edge(c, k, Integer.MAX_VALUE);
				Edge e2=new Edge(k, c, 0);
				c.getEdges().add(e1);
				k.getEdges().add(e2);
				e1.setOpposentPath(e2);
				e2.setOpposentPath(e1);
				Edge e3=new Edge(d, k, Integer.MAX_VALUE);
				Edge e4=new Edge(k, d, 0);
				d.getEdges().add(e3);
				k.getEdges().add(e4);
				e3.setOpposentPath(e4);
				e4.setOpposentPath(e3);
				Edge e7=new Edge(k, t, capacity);
				Edge e8=new Edge(t,k,0);
				k.getEdges().add(e7);
				t.getEdges().add(e8);
				e7.setOpposentPath(e8);
				e8.setOpposentPath(e7);
				Edge e5=new Edge(cd, k, Integer.MAX_VALUE);
				Edge e6=new Edge(k, cd, 0);
				cd.getEdges().add(e5);
				k.getEdges().add(e6);
				e5.setOpposentPath(e6);
				e6.setOpposentPath(e5);
				totalRedCapacity+=capacity;
				totalTrainCapacity+=capacity;
				totalRedTrainCapacity+=capacity;
				totalCapacity+=capacity;
				rt.add(k);
			}
		}
		numOfGreenReindeers=in.nextInt();
		for (int i=0; i<numOfGreenReindeers; i++) {
			int capacity=in.nextInt();
			if (capacity!=0) {
				Vertex k=new Vertex(new LinkedList<Edge>(), "gr");
				vertexes.add(k);
				levels.add(2);
				Edge e1=new Edge(b, k, Integer.MAX_VALUE);
				Edge e2=new Edge(k, b, 0);
				b.getEdges().add(e1);
				k.getEdges().add(e2);
				e1.setOpposentPath(e2);
				e2.setOpposentPath(e1);
				Edge e3=new Edge(e, k, Integer.MAX_VALUE);
				Edge e4=new Edge(k, e, 0);
				e.getEdges().add(e3);
				k.getEdges().add(e4);
				e3.setOpposentPath(e4);
				e4.setOpposentPath(e3);
				Edge e7=new Edge(k, t, capacity);
				Edge e8=new Edge(t,k,0);
				k.getEdges().add(e7);
				t.getEdges().add(e8);
				e7.setOpposentPath(e8);
				e8.setOpposentPath(e7);
				Edge e5=new Edge(be, k, Integer.MAX_VALUE);
				Edge e6=new Edge(k, be, 0);
				be.getEdges().add(e5);
				k.getEdges().add(e6);
				e5.setOpposentPath(e6);
				e6.setOpposentPath(e5);
				totalGreenCapacity+=capacity;
				totalReindeerCapacity+=capacity;
				totalGreenReindeerCapacity+=capacity;
				totalCapacity+=capacity;
				gr.add(k);
			}
		}
		numOfRedReindeers=in.nextInt();
		for (int i=0; i<numOfRedReindeers; i++) {
			int capacity=in.nextInt();
			if (capacity!=0) {
				Vertex k=new Vertex(new LinkedList<Edge>(), "rr");
				vertexes.add(k);
				levels.add(2);
				Edge e1=new Edge(c, k, Integer.MAX_VALUE);
				Edge e2=new Edge(k, c, 0);
				c.getEdges().add(e1);
				k.getEdges().add(e2);
				e1.setOpposentPath(e2);
				e2.setOpposentPath(e1);
				Edge e3=new Edge(e, k, Integer.MAX_VALUE);
				Edge e4=new Edge(k, e, 0);
				e.getEdges().add(e3);
				k.getEdges().add(e4);
				e3.setOpposentPath(e4);
				e4.setOpposentPath(e3);
				Edge e7=new Edge(k, t, capacity);
				Edge e8=new Edge(t,k,0);
				k.getEdges().add(e7);
				t.getEdges().add(e8);
				e7.setOpposentPath(e8);
				e8.setOpposentPath(e7);
				Edge e5=new Edge(ce, k, Integer.MAX_VALUE);
				Edge e6=new Edge(k, ce, 0);
				ce.getEdges().add(e5);
				k.getEdges().add(e6);
				e5.setOpposentPath(e6);
				e6.setOpposentPath(e5);
				totalRedCapacity+=capacity;
				totalReindeerCapacity+=capacity;
				totalRedReindeerCapacity+=capacity;
				totalCapacity+=capacity;
				rr.add(k);
			}
		}
		numOfBags=in.nextInt();
		for (int i=0; i<numOfBags; i++) {
			String bag=in.next();
			int capacity=in.nextInt();
			totalGifts+=capacity;
			if (capacity!=0) {
				if (bag.equals("b")) {
					s.getEdges().get(0).setCapacity(s.getEdges().get(0).getCapacity()+capacity);
				}
				else if (bag.equals("c")) {
					s.getEdges().get(1).setCapacity(s.getEdges().get(1).getCapacity()+capacity);
				}
				else if (bag.equals("d")) {
					s.getEdges().get(2).setCapacity(s.getEdges().get(2).getCapacity()+capacity);
				}
				else if (bag.equals("e")) {
					s.getEdges().get(3).setCapacity(s.getEdges().get(3).getCapacity()+capacity);
				}
				else if (bag.equals("bd")) {
					s.getEdges().get(4).setCapacity(s.getEdges().get(4).getCapacity()+capacity);
				}
				else if (bag.equals("be")) {
					s.getEdges().get(5).setCapacity(s.getEdges().get(5).getCapacity()+capacity);
				}
				else if (bag.equals("cd")) {
					s.getEdges().get(6).setCapacity(s.getEdges().get(6).getCapacity()+capacity);
				}
				else if (bag.equals("ce")) {
					s.getEdges().get(7).setCapacity(s.getEdges().get(7).getCapacity()+capacity);
				}
				else if (bag.equals("a")) {
					Vertex x=new Vertex(new LinkedList<Edge>(), "a");
					vertexes.add(x);
					levels.add(1);
					Edge s1=new Edge(s, x, capacity);
					s.getEdges().add(s1);
					Edge s2=new Edge(x,s,0);
					x.getEdges().add(s2);
					s1.setOpposentPath(s2);
					s2.setOpposentPath(s1);
					for (Vertex aa : rr) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}
					for (Vertex aa : gr) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}
					for (Vertex aa : rt) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}
					for (Vertex aa : gt) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}
				}	
				else if (bag.equals("ab")) {
					Vertex x=new Vertex(new LinkedList<Edge>(), "ab");
					vertexes.add(x);
					Edge s1=new Edge(s, x, capacity);
					s.getEdges().add(s1);
					Edge s2=new Edge(x,s,0);
					x.getEdges().add(s2);
					s1.setOpposentPath(s2);
					s2.setOpposentPath(s1);
					for (Vertex aa : gr) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}
					for (Vertex aa : gt) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}
				}
				else if (bag.equals("ac")) {
					Vertex x=new Vertex(new LinkedList<Edge>(), "ac");
					vertexes.add(x);
					Edge s1=new Edge(s, x, capacity);
					s.getEdges().add(s1);
					Edge s2=new Edge(x,s,0);
					x.getEdges().add(s2);
					s1.setOpposentPath(s2);
					s2.setOpposentPath(s1);
					for (Vertex aa : rr) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}
					for (Vertex aa : rt) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}	
				}
				else if (bag.equals("ad")) {
					Vertex x=new Vertex(new LinkedList<Edge>(), "ad");
					vertexes.add(x);
					Edge s1=new Edge(s, x, capacity);
					s.getEdges().add(s1);
					Edge s2=new Edge(x,s,0);
					x.getEdges().add(s2);
					s1.setOpposentPath(s2);
					s2.setOpposentPath(s1);
					for (Vertex aa : gt) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}
					for (Vertex aa : rt) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}
				}
				else if (bag.equals("ae")) {
					Vertex x=new Vertex(new LinkedList<Edge>(), "ae");
					vertexes.add(x);
					Edge s1=new Edge(s, x, capacity);
					s.getEdges().add(s1);
					Edge s2=new Edge(x,s,0);
					x.getEdges().add(s2);
					s1.setOpposentPath(s2);
					s2.setOpposentPath(s1);
					for (Vertex aa : gr) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}
					for (Vertex aa : rr) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}	
				}
				else if (bag.equals("abd")) {
					Vertex x=new Vertex(new LinkedList<Edge>(), "abd");
					vertexes.add(x);
					Edge s1=new Edge(s, x, capacity);
					s.getEdges().add(s1);
					Edge s2=new Edge(x,s,0);
					x.getEdges().add(s2);
					s1.setOpposentPath(s2);
					s2.setOpposentPath(s1);
					for (Vertex aa : gt) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}
				}
				else if (bag.equals("abe")) {
					Vertex x=new Vertex(new LinkedList<Edge>(), "abe");
					vertexes.add(x);
					Edge s1=new Edge(s, x, capacity);
					s.getEdges().add(s1);
					Edge s2=new Edge(x,s,0);
					x.getEdges().add(s2);
					s1.setOpposentPath(s2);
					s2.setOpposentPath(s1);
					for (Vertex aa : gr) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}
				}
				else if (bag.equals("acd")) {
					Vertex x=new Vertex(new LinkedList<Edge>(), "acd");
					vertexes.add(x);
					Edge s1=new Edge(s, x, capacity);
					s.getEdges().add(s1);
					Edge s2=new Edge(x,s,0);
					x.getEdges().add(s2);
					s1.setOpposentPath(s2);
					s2.setOpposentPath(s1);
					for (Vertex aa : rt) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}
				}
				else if (bag.equals("ace")) {
					Vertex x=new Vertex(new LinkedList<Edge>(), "ace");
					vertexes.add(x);
					Edge s1=new Edge(s, x, capacity);
					s.getEdges().add(s1);
					Edge s2=new Edge(x,s,0);
					x.getEdges().add(s2);
					s1.setOpposentPath(s2);
					s2.setOpposentPath(s1);
					for (Vertex aa : rr) {
						Edge e1=new Edge(x, aa, 1);
						Edge e2=new Edge(aa, x, 0);
						x.getEdges().add(e1);
						aa.getEdges().add(e2);
						e1.setOpposentPath(e2);
						e2.setOpposentPath(e1);
					}
				}
			}	
		}
//		System.out.println(vertexes.size());
//		System.out.println(levels.size());
//		vertexes.add(t);
//		levels.add(3);
//		LinkedList<Vertex>queue=new LinkedList<Vertex>();
//		LinkedList<Edge>edgeQueue=new LinkedList<Edge>();
//		int bottleNeck=Integer.MAX_VALUE;
//		queue.add(s);
//		boolean x=true;
//		while (x) {
//			for (int i=0; i<queue.getLast().getEdges().size(); i++) {
//				if (levels.get(vertexes.indexOf(queue.getLast().getEdges().get(i).getTargetVertex()))==
//						levels.get(vertexes.indexOf(queue.getLast()))+1 && queue.getLast().getEdges().get(i).getCapacity()>0 && 
//						(levels.get(vertexes.indexOf(queue.getLast().getEdges().get(i).getTargetVertex()))<levels.get(vertexes.indexOf(t)) || 
//								queue.getLast().getEdges().get(i).getTargetVertex().equals(t))) {
//					bottleNeck=Math.min(queue.getLast().getEdges().get(i).getCapacity(), bottleNeck);
//					edgeQueue.add(queue.getLast().getEdges().get(i));
//					queue.add(queue.getLast().getEdges().get(i).getTargetVertex());
//					break;
//				}
//
//			}
//			if (queue.getLast().equals(t)) {
//				for (int j=0; j<edgeQueue.size(); j++) {
//					edgeQueue.get(j).setCapacity(edgeQueue.get(j).getCapacity()-bottleNeck);
//					if (edgeQueue.get(j).getOpposentPath()!=null) {
//						edgeQueue.get(j).getOpposentPath().setCapacity(edgeQueue.get(j).getOpposentPath().getCapacity()+bottleNeck);
//					}
//				}
//				atTheHand+=bottleNeck;
//				queue.clear();
//				queue.add(s);
//				bottleNeck=Integer.MAX_VALUE;
//				System.out.println(totalGifts-atTheHand);
//			}
//		}
		
		visitedNumber=1;
		int flow=Integer.MAX_VALUE;
		while (flow>0) {
			flow=checkPath(flow, s);
			atTheHand+=flow;
			visitedNumber+=1;
			
		}
		
		
		out.print(totalGifts-atTheHand);
		
	}
	

}