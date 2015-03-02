package actions;

import globalRegistry.StateActionRegistry;

import state.Own;
import state.State;
import agent.ActiveAgent;
import agent.Agent;

public class Take extends Action {
	public ActiveAgent owner = null;
	public Agent taken = null;
	
	public Take () 
	{
		ID = StateActionRegistry.TAKE;
		name = "take";
		preConds.add(StateActionRegistry.AT);
		postConds.add(StateActionRegistry.OWN);
	}
	
	public Take(ActiveAgent o, Agent t)
	{
		ID = StateActionRegistry.TAKE;
		name = "take";
		owner = o;
		taken = t;
	}
	
	@Override
	public Action getNewInstance() {
		return new Take();
	}



	@Override
	public int getCost() {
		if (taken == null || owner == null || taken.owner != null)
		{
			return Integer.MAX_VALUE;
		}
		return defaultCost;
	}



	@Override
	public boolean setUp(State s) {
		// TODO Auto-generated method stub
		// Needs to handle postconds, ie. Own
		if (s.ID == StateActionRegistry.OWN)
		{
			Own o = (Own) s;
			if (o.owned == null || o.owner == null || o.owned.owner != null)
				return false;
			owner = o.owner;
			taken = o.owned;
			return true;
		}
		return false;
	}

	@Override
	public void print() {
		if (owner == null || taken == null)
			return;
		System.out.println(owner.name + " takes " + taken.name);
	}

}
