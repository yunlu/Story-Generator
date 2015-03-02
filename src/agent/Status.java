package agent;

public class Status {
	public int ID;
	public String name;
	public int amount;
	public int priority;
	public int threshold; // When amount < threshold, goal is activated
	public Goal goal;
	public boolean goalActive;
	
	public Status(int id, String n, int amt, int pri, int thresh, Goal g)
	{
		ID = id;
		name = n;
		amount = amt;
		priority = pri;
		threshold = thresh;
		goal = g;
		goalActive = false;
	}
	
	
}
