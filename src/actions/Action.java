package actions;


import globalRegistry.StateActionRegistry;

import java.util.LinkedList;
import java.util.List;

import state.State;

public abstract class Action {
	public StateActionRegistry.A ID;
	public String name;
	public List<StateActionRegistry.S> postConds = new LinkedList<StateActionRegistry.S>();
	public List<StateActionRegistry.S> preConds = new LinkedList<StateActionRegistry.S>();;
	
	protected int defaultCost = 10;
	
	public abstract Action getNewInstance();
	public boolean execute()
	{
		if (getCost() == Integer.MAX_VALUE)
			return false;
		print();
		for (StateActionRegistry.S i : postConds)
		{
			State s = StateActionRegistry.getStateFromID(i);
			if (s == null)
				continue;
			if (s.setUp(this));
				s.execute();
		}
		return true;
	}
	public abstract int getCost();
	public List<State> getPrecond()
	{
		List<State> ret = new LinkedList<State>();
		for (StateActionRegistry.S i : preConds)
		{
			State s = StateActionRegistry.getStateFromID(i);
			if (s == null)
				continue;
			if (!s.setUp(this))
				continue;
			ret.add(s);
		}
		return ret;
	}
	public abstract boolean setUp(State s); // Do for postConds (for State's "getAllNextAction")
	public abstract void print();
}
