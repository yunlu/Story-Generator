package agent;

import planner.Planner;
import planner.PlannerRetVal;
import state.State;
import globalRegistry.StatusCharacteristicRegistry;

public class Status {
	public StatusCharacteristicRegistry.S ID;
	public String name;
	public int amount;
	public int priority;
	public int threshold; // When amount < threshold, goal is activated
	public Goal goal;
	public boolean goalActive;
	
	public Status(StatusCharacteristicRegistry.S id, String n, int amt, int pri, int thresh, Goal g)
	{
		ID = id;
		name = n;
		amount = amt;
		priority = pri;
		threshold = thresh;
		goal = g;
		goalActive = true;
	}
	public boolean execute(Planner p, int maxNumSteps) // return true if something was done
	{
		if (goal == null || goalActive == false)
			return false;
		if (amount > threshold)
		{
			goalActive = false;
			return false;
		}
		
		if (goal.toBe != null)
		{
			for (State s : goal.toBe)
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
				
			}
			// all states are satisfied
			if (goal.toDo == null)
				goalActive = false;
			return true;
			
		}
		if (goal.toDo != null)
		{
			if (goal.toDo.execute())
			{
				goalActive = false; // after executing the action, deactivate
				return true;
			}
		}
		return false;
	}
	
}
