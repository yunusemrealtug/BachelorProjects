
public class Student implements Comparable<Student> {
	
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

	public String getName() {
		return name;
	}

	private final String name;
	
	public Student (int id, String name, int duration, double rating){
		this.id=id;
		this.name=name;
		this.duration=duration;
		this.rating=rating;
		
		
	}

	public int getId() {
		return id;
	}

	@Override
	public int compareTo(Student theOther) {
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
