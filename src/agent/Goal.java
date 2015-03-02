package agent;
import java.util.List;

import planner.Planner;
import planner.PlannerRetVal;
import state.State;
import actions.Action;


public class Goal {
	public String name;
	private List<State> toBe = null;
	private Action toDo = null;
	public Goal(String n, List<State> s, Action a) // name of goal, 
												   // states to be in (in the order you want them to be done), 
												   // action to do when states are achieved
	{
		name = n;
		toBe = s;
		toDo = a;
	}
	public boolean execute(Planner p, int maxNumSteps) // return true if something was done
	{
		if (toBe != null)
		{
			for (State s : toBe)
			{
				if (!s.reset())
					return false;
				PlannerRetVal plan = p.plan(s, maxNumSteps);
				if (plan == null)
					return false;
				if (plan.toDo != null)
				{
					return plan.toDo.execute();
				}
				else
				{
					return true;
				}
			}
		}
		if (toDo != null)
			return toDo.execute();
		return false;
	}
}
