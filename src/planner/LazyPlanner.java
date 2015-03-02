package planner;
import globalRegistry.StateActionRegistry;

import java.util.List;

import location.Location;
import state.AtState;
import state.State;
import actions.Action;


public class LazyPlanner implements Planner {
	
	@Override
	public PlannerRetVal plan(State start, int maxNumSteps) {
		PlannerRetVal m = lazyPlanner(start, 0, maxNumSteps, null);
		if (m == null || m.cost == Integer.MAX_VALUE)
			return null;
		return m;
	}
	
	public PlannerRetVal lazyPlanner(State start,
							int currCost,
							int maxNumSteps,  
							Action prevAction)
	{
		// Error check
		if (maxNumSteps < 0 || start == null || currCost == Integer.MAX_VALUE)
		{
			//System.out.println("This path doesn't work: maxNumSteps = " + maxNumSteps + " currCost = " + currCost); 
			return null; 
		}
		
		// Check if prevState is null -- ie. if this is the first time we called the planner
		int prevActionCost = 0;
		if (prevAction != null)
		{
			prevActionCost = prevAction.getCost();
			if (prevActionCost == Integer.MAX_VALUE)
				return null;
		}
		
		// Check if start state is already satisfied
		if (start.satisfied())
		{
			return new PlannerRetVal(currCost + prevActionCost, 
							maxNumSteps, 
							prevAction);
		}
		
		// Special case when state is "AT", since we need to invoke the map
		if (start.ID == StateActionRegistry.AT)
		{
			return lazyPathPlanner((AtState) start, maxNumSteps, currCost, null);
		}
		
		// Try to get the lowest cost plan 
		int minCost = Integer.MAX_VALUE;
		PlannerRetVal minMess = null;
		
		// Try the possible actions that lead to this state
		List<Action> allNextActions = start.getAllNextAction();
		for (Action nextAction : allNextActions)
		{
			// Each action may have multiple preconditions
			List<State> preconds = nextAction.getPrecond();
			
			int totalActionCost = currCost + prevActionCost;
			int stepsAvailableForAction = maxNumSteps - 1;
			
			// Store the first precondition's plan so that it will be performed first
			PlannerRetVal firstPrecondPlan = null;
			
			// Add up the costs for each precondition
			for (int i = 0; i < preconds.size(); i++)
			{
				State s = preconds.get(i);
				PlannerRetVal m = null;
				m = lazyPlanner(s, 
							totalActionCost, 
							stepsAvailableForAction, 
							nextAction);
				
				// If one of the preconds cannot be achieved or is too expensive, skip this action
				if (m == null || m.cost >= minCost)
				{
					firstPrecondPlan = null;
					break;
				}
				if (i == 0)
				{
					firstPrecondPlan = m;
				}
				totalActionCost = m.cost;
				stepsAvailableForAction = m.numStepsLeft;
				firstPrecondPlan.cost = totalActionCost;
				firstPrecondPlan.numStepsLeft = stepsAvailableForAction;
				
			}
			
			// We always perform the first precond first
			if (firstPrecondPlan != null && firstPrecondPlan.cost < minCost)
			{
				minMess = firstPrecondPlan;
				minCost = firstPrecondPlan.cost;
			}
		}
		
		// If no next action of no next action succeeds
		if (minCost == Integer.MAX_VALUE)
		{
			return null;
		}
		
		// Otherwise just return the minimum-cost result
		return minMess;
	}
	
	public PlannerRetVal lazyPathPlanner(AtState start,
								int currCost,
								int maxNumSteps,
								Action prevAction)
	{
		//
		// Find a plan to get to this place, assuming we are at a location directly connected to it
		//
		
		// Error check
		if (maxNumSteps < 0 || start == null || currCost == Integer.MAX_VALUE)
		{
			return null; 
		}
		
		// Check if prevState is null -- ie. if this is the first time we called the planner
		int prevActionCost = 0;
		if (prevAction != null)
		{
			prevActionCost = prevAction.getCost();
			if (prevActionCost == Integer.MAX_VALUE)
				return null;
		}
		
		// Check if start state is already satisfied
		if (start.satisfied())
		{
			return new PlannerRetVal(currCost + prevActionCost, 
							maxNumSteps, 
							prevAction);
		}

		// Try to get the lowest cost plan 
		int minCost = Integer.MAX_VALUE;
		PlannerRetVal minMess = null;
		
		// Try the possible actions that lead to this state
		List<Action> allNextActions = start.getAllNextAction();
		for (Action nextAction : allNextActions)
		{
			// Each action may have multiple preconditions
			List<State> preconds = nextAction.getPrecond();
			
			int totalActionCost = currCost + prevActionCost;
			int stepsAvailableForAction = maxNumSteps - 1;
			
			// Store the first precondition's plan so that it will be performed first
			PlannerRetVal firstPrecondPlan = null;
			
			// Add up the costs for each precondition
			for (int i = 0; i < preconds.size(); i++)
			{
				State s = preconds.get(i);
				PlannerRetVal m = null;
				m = lazyPlanner(s, 
							totalActionCost, 
							stepsAvailableForAction, 
							nextAction);
				
				// If one of the preconds cannot be achieved or is too expensive, skip this action
				if (m == null || m.cost >= minCost)
				{
					firstPrecondPlan = null;
					break;
				}
				if (i == 0)
				{
					firstPrecondPlan = m;
				}
				totalActionCost = m.cost;
				stepsAvailableForAction = m.numStepsLeft;
				firstPrecondPlan.cost = totalActionCost;
				firstPrecondPlan.numStepsLeft = stepsAvailableForAction;
				
			}
			
			// We always perform the first precond first
			if (firstPrecondPlan != null && firstPrecondPlan.cost < minCost)
			{
				minMess = firstPrecondPlan;
				minCost = firstPrecondPlan.cost;
			}
		}
		
		// If no next action of no next action succeeds
		if (minCost == Integer.MAX_VALUE || minMess == null)
		{
			return null;
		}
		
		//
		// END Find a plan to get to this place, assuming we are at a location directly connected to it
		//
		
		//
		// Find a path to a place connected to here
		//
		
		// Get the list of locations connected to this one
		List<Location> locs = null;
		if (start.loc != null)
			locs = start.loc.connectedTo;
		else if (start.locationOf != null)
			locs = start.locationOf.loc.connectedTo;
		int minLocCost = Integer.MAX_VALUE;
		PlannerRetVal minLocMess = null;
		for(Location l : locs)
		{
			//System.out.println("Trying to get to... " + l.name);
			PlannerRetVal m = lazyPathPlanner(new AtState(start.owner, l), 
									currCost + minCost, 
									minMess.numStepsLeft, 
									minMess.toDo);
			if (m == null)
				continue;
			if (m.cost < minLocCost)
			{
				minLocMess = m;
				minLocCost = m.cost;
			}
		}
		
		if (minLocCost == Integer.MAX_VALUE)
		{
			return null;
		}
		
		return minLocMess;
		
		//
		// END Find a path to a place connected to here
		//
		
	}

}
