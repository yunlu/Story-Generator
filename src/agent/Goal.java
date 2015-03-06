package agent;
import java.util.List;

import planner.Planner;
import planner.PlannerRetVal;
import state.State;
import actions.Action;


public class Goal {
	public String name;
	public List<State> toBe = null;
	public Action toDo = null;
	public Goal(String n, List<State> s, Action a) // name of goal, 
												   // states to be in (in the order you want them to be done), 
												   // action to do when states are achieved
	{
		name = n;
		toBe = s;
		toDo = a;
	}
	

}
