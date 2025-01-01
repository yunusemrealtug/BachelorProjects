
import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class project1main {
	
	public static String inputType;
	
	public static PriorityQueue<House> passedHouses;
	public static PriorityQueue<Student> students;
	public static PriorityQueue<Student> outStudents;
	public static PriorityQueue<House> unavailibleHouses;
	public static PriorityQueue<Student> graduatedStudents;
	public static PriorityQueue<House> availibleHouses;

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		passedHouses=new PriorityQueue<House>();
		unavailibleHouses=new PriorityQueue<House>();
		availibleHouses=new PriorityQueue<House>();
		students=new PriorityQueue<Student>();
		outStudents=new PriorityQueue<Student>();
		graduatedStudents=new PriorityQueue<Student>();
		
		inputType=in.next();
		
		while (inputType.equals("s") || inputType.equals("h")) {
			
			if (inputType.equals("s")) {
				Student s1= new Student (in.nextInt(),in.next(),in.nextInt(),in.nextDouble());
				if (s1.getDuration()!=0) {
					students.add(s1);;
				}
				else {
					graduatedStudents.add(s1);
				}
			
			}
			else {	
				House h1= new House (in.nextInt(),in.nextInt(),in.nextDouble());
				if (h1.getDuration()==0) {
					availibleHouses.add(h1);
				}
				else {
					unavailibleHouses.add(h1);
				}
			
			}
			if (in.hasNext()) {	
				inputType=in.next();
			
			}
			else {
				break;
				
			}
		}

		while (!students.isEmpty()) {
			
			while (!students.isEmpty()) {
				
				while (!availibleHouses.isEmpty()) {
					if (students.isEmpty()) {
						break;
					}
					
					if (students.peek().getDuration()==0) {
						graduatedStudents.add(students.peek());
						students.poll();
					}
					
				
					else if (students.peek().getRating()<=availibleHouses.peek().getRating()) {
						availibleHouses.peek().setDuration(students.peek().getDuration());
						students.poll();
						unavailibleHouses.add(availibleHouses.poll());
						while (!passedHouses.isEmpty()) {
							availibleHouses.add(passedHouses.poll());
						}
					}
					else {
						passedHouses.add(availibleHouses.poll());
					}
				}
				
				if (students.isEmpty()) {
					break;
				}
				outStudents.add(students.peek());
				students.poll();
				while (!passedHouses.isEmpty()) {
					availibleHouses.add(passedHouses.poll());
				}
			}
			while (!outStudents.isEmpty()) {
				students.add(outStudents.poll());
			}
			while (!students.isEmpty()) {
				if (students.peek().getDuration()!=0) {
					students.peek().setDuration(students.peek().getDuration()-1);
					outStudents.add(students.peek());
					students.poll();
				}
				else {
					
					graduatedStudents.add(students.poll());
				}
			}
			
			while (!outStudents.isEmpty()) {
				students.add(outStudents.poll());
			}
			
			
			while (!unavailibleHouses.isEmpty()) {
				
				if(unavailibleHouses.peek().getDuration()>1) {
				
				unavailibleHouses.peek().setDuration(unavailibleHouses.peek().getDuration()-1);
				passedHouses.add(unavailibleHouses.poll());
				
				}
				else {
					unavailibleHouses.peek().setDuration(unavailibleHouses.peek().getDuration()-1);
					availibleHouses.add(unavailibleHouses.poll());
				}
				
			}
			while (!passedHouses.isEmpty()) {
				unavailibleHouses.add(passedHouses.poll());
			}
		}	
		
		
		while (!graduatedStudents.isEmpty()) {
			out.println(graduatedStudents.poll().getName());
		}
	}
}
