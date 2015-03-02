package planner;
import actions.Action;


public class PlannerRetVal {
	public int cost;
	public int numStepsLeft;
	public Action toDo;
	
	public PlannerRetVal(int c, int n, Action t) {
	       cost = c;
	       numStepsLeft = n;
	       toDo = t;
	}
}
