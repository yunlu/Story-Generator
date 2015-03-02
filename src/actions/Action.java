package actions;


import globalRegistry.StateActionRegistry;

import java.util.LinkedList;
import java.util.List;

import state.State;

public abstract class Action {
	public int ID;
	public String name;
	public List<Integer> postConds = new LinkedList<Integer>();
	public List<Integer> preConds = new LinkedList<Integer>();;
	
	protected int defaultCost = 10;
	
	public abstract Action getNewInstance();
	public boolean execute()
	{
		if (getCost() == Integer.MAX_VALUE)
			return false;
		print();
		for (int i : postConds)
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
		for (int i : preConds)
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
