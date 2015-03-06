package actions;

import globalRegistry.StateActionRegistry;
import location.Location;
import state.AtState;
import state.State;
import agent.ActiveAgent;
import agent.Agent;

public class Go extends Action {
	public ActiveAgent owner = null;
	public Location loc = null;
	public Agent locationOf = null;
	
	public Go()
	{
		ID = StateActionRegistry.A.GO;
		name = "go";
		preConds.add(StateActionRegistry.S.START);
		postConds.add(StateActionRegistry.S.AT);
	}
	
	public Go(ActiveAgent o, Location l)
	{
		ID = StateActionRegistry.A.GO;
		name = "go";
		preConds.add(StateActionRegistry.S.START);
		postConds.add(StateActionRegistry.S.AT);
		
		owner = o;
		loc = l;
	}
	
	public Go(ActiveAgent o, Agent l)
	{
		ID = StateActionRegistry.A.GO;
		name = "go";
		preConds.add(StateActionRegistry.S.START);
		postConds.add(StateActionRegistry.S.AT);
		
		owner = o;
		locationOf = l;
	}
	
	@Override
	public Action getNewInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCost() {
		if (owner == null || (loc == null && locationOf == null))
			return Integer.MAX_VALUE;	
		return defaultCost;
	}


	@Override
	public boolean setUp(State s) {
		if (s == null)
		{
			return false;
		}
		// PRECONDS
		
		// POSTCONDS
		if (s.ID == StateActionRegistry.S.AT)
		{
			AtState a = (AtState) s;
			if (a.owner == null || (a.loc == null && a.locationOf == null))
				return false;
			owner = a.owner;
			if (a.loc != null)
			{
				loc = a.loc;
			} else
			{
				locationOf = a.locationOf;
			}
			return true;
			
		}
		
		
		
		return false;
	}

	@Override
	public void print() {
		if (owner == null || (loc == null && locationOf == null))
		{
			return;
		}
		if (loc != null)
		{
			System.out.println(owner.name + " goes from " + owner.loc.name + " to " + loc.name  );
		}
		if (locationOf != null)
		{
			System.out.println(owner.name + " goes from " + owner.loc.name + " to " + locationOf.name + " which is at " + locationOf.loc.name);
		}

	}

}
