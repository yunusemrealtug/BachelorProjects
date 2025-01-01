import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Scanner;

public class project3main {
	
	public static double timeLimit;
	public static int numOfCities;
	public static City leylasCity;
	public static City mecnunsCity;
	public static HashMap <String, City> cityMap;
	public static PriorityQueue <City> availibleCities;
	public static ArrayList <String> honeymoonCities;
	public static PriorityQueue<Path> availiblePaths;
	public static boolean broken;
	public static boolean beturn;
	public static int minDistance;
	
	public static void main(String[] args) throws FileNotFoundException {
		Locale.setDefault(new Locale("en", "US"));
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		cityMap=new HashMap <String, City>();
		availibleCities=new PriorityQueue <City>();
		availiblePaths = new PriorityQueue <Path>();
		honeymoonCities=new ArrayList <String>();
		broken=false;
		beturn=false;
		
		timeLimit=in.nextDouble();
		numOfCities=in.nextInt();
		mecnunsCity= new City(in.next(), new ArrayList<Path>(), new ArrayList<Path>(), 0);
		cityMap.put(mecnunsCity.getId(), mecnunsCity);
		mecnunsCity.setShortestPath("");
		leylasCity=new City(in.next(), new ArrayList<Path>(), new ArrayList<Path>(), Integer.MAX_VALUE);
		cityMap.put(leylasCity.getId(), leylasCity);
		in.nextLine();
		
		for (int i=0; i<numOfCities; i++) {
			String[] array = in.nextLine().split(" ");
			if (array[0].equals(leylasCity.getId())) {
				for (int j=1; j<array.length; j=j+2) {
					int number = Integer.parseInt(array[j+1]);
					Path p1=new Path(number, leylasCity.getId(), array[j]);
					leylasCity.getRoads().add(p1);
				}
			}
			else if (array[0].equals(mecnunsCity.getId())) {
				for (int j=1; j<array.length; j=j+2) {
					int number = Integer.parseInt(array[j+1]);
					Path p1=new Path(number, mecnunsCity.getId(), array[j]);
					mecnunsCity.getRoads().add(p1);
				}
			}
			else {
				City c1=new City(array[0], new ArrayList<Path>(), new ArrayList<Path>(),Integer.MAX_VALUE);
				cityMap.put(c1.getId(), c1);
				if (c1.getId().contains("d")) {
					honeymoonCities.add(c1.getId());
				}
				for (int j=1; j<array.length; j=j+2) {
					int number = Integer.parseInt(array[j+1]);
					Path p1=new Path(number, c1.getId(), array[j]);
					c1.getRoads().add(p1);
				}
			}
		}
		City ongoingCity=mecnunsCity;
		while (!ongoingCity.equals(leylasCity)) {
			ongoingCity.setVisited(true);
			for (int i=0; i<ongoingCity.getRoads().size(); i++) {
				if (!cityMap.get(ongoingCity.getRoads().get(i).getTargetCity()).isVisited() 
						&& !cityMap.get(ongoingCity.getRoads().get(i).getTargetCity()).isAvailible()) {
					availibleCities.add(cityMap.get(ongoingCity.getRoads().get(i).getTargetCity()));
					cityMap.get(ongoingCity.getRoads().get(i).getTargetCity()).setAvailibility(true);
				}
				int targetDistance=cityMap.get(ongoingCity.getRoads().get(i).getTargetCity()).getDistance();
				int potantialDistance=ongoingCity.getRoads().get(i).getWeight()+ongoingCity.getDistance();
				if (targetDistance>potantialDistance) {
					cityMap.get(ongoingCity.getRoads().get(i).getTargetCity()).setDistance(potantialDistance);
					availibleCities.remove(cityMap.get(ongoingCity.getRoads().get(i).getTargetCity()));
					availibleCities.add(cityMap.get(ongoingCity.getRoads().get(i).getTargetCity()));
					cityMap.get(ongoingCity.getRoads().get(i).getTargetCity()).setShortestPath(ongoingCity.getShortestPath());
				}
			}
			ongoingCity.setAvailibility(false);
			if (!availibleCities.isEmpty()) {
				ongoingCity=availibleCities.poll();
			}
			else {
				break;
			} 	
		}
		if (leylasCity.getDistance()!=Integer.MAX_VALUE) {
			out.println(leylasCity.getShortestPath().strip());
			if (leylasCity.getDistance()>timeLimit) {
				out.println(-1);
			}
			else {
				for (int i=0; i<leylasCity.getRoads().size(); i++) {
					if (leylasCity.getRoads().get(i).getTargetCity().contains("d")) {
						cityMap.get(leylasCity.getRoads().get(i).getTargetCity()).getRoads().add(leylasCity.getRoads().get(i));
					}
				}
				for (int i=0; i<honeymoonCities.size(); i++) {
					for (int j=0; j<cityMap.get(honeymoonCities.get(i)).getRoads().size(); j++) {
						cityMap.get(cityMap.get(honeymoonCities.get(i)).getRoads().get(j).getTargetCity()).getSourceRoads().add(cityMap.get(honeymoonCities.get(i)).getRoads().get(j));
					}
				}
				for (int i=0; i<leylasCity.getRoads().size(); i++) {
					if (leylasCity.getRoads().get(i).getTargetCity().contains("d")) {
						beturn=true;
						break;
					}
				}
				for (int i=0; i<honeymoonCities.size(); i++) {	
					if (cityMap.get(honeymoonCities.get(i)).getRoads().isEmpty() && cityMap.get(honeymoonCities.get(i)).getSourceRoads().isEmpty() || beturn==false) {
						broken=true;
						break;
					}
				}
				if (broken==true) {
					out.println(-2);
				}
				else {
					while (!honeymoonCities.isEmpty()) {
						ongoingCity.setVisited(true);
						for (int i=0; i<(ongoingCity.getRoads().size()+ongoingCity.getSourceRoads().size()); i++) {
							if (i<ongoingCity.getRoads().size()) {
								if (!ongoingCity.getRoads().get(i).isAvailible() && !cityMap.get(ongoingCity.getRoads().get(i).getTargetCity()).isVisited() 
										&& ongoingCity.getRoads().get(i).getTargetCity().contains("d")) {
									availiblePaths.add(ongoingCity.getRoads().get(i));
									ongoingCity.getRoads().get(i).setAvailible(true);
								}
							}
							else {
								if (!ongoingCity.getSourceRoads().get(i-ongoingCity.getRoads().size()).isAvailible() 
										&& !cityMap.get(ongoingCity.getSourceRoads().get(i-ongoingCity.getRoads().size()).getSourceCity()).isVisited()) {
									availiblePaths.add(ongoingCity.getSourceRoads().get(i-ongoingCity.getRoads().size()));
									ongoingCity.getSourceRoads().get(i-ongoingCity.getRoads().size()).setAvailible(true);
								}
							}
						}
						while (cityMap.get(availiblePaths.peek().getTargetCity()).isVisited() 
								&& cityMap.get(availiblePaths.peek().getSourceCity()).isVisited()) {
							availiblePaths.poll();
						}
						if (cityMap.get(availiblePaths.peek().getTargetCity()).isVisited()) {
							minDistance+=availiblePaths.peek().getWeight();
							ongoingCity=cityMap.get(availiblePaths.poll().getSourceCity());
						}
						else if (cityMap.get(availiblePaths.peek().getSourceCity()).isVisited()){
							minDistance+=availiblePaths.peek().getWeight();
							ongoingCity=cityMap.get(availiblePaths.poll().getTargetCity());
						}
						honeymoonCities.remove(ongoingCity.getId());
					}
					out.println(minDistance*2);
				}
			}
		}
		else {
			out.println(-1);
			out.println(-1);	
		}
	}
	
}
