package state;
import globalRegistry.StateActionRegistry;

import java.util.LinkedList;
import java.util.List;

import actions.Action;


public abstract class State {
	public StateActionRegistry.S ID;
	public String name;
	
	protected List<StateActionRegistry.A> toDo = new LinkedList<StateActionRegistry.A>(); // List of Action IDs
	public abstract boolean satisfied();
	public abstract boolean execute();
	public abstract void print();
	public boolean reset()
	{
		return true;
	}
	public List<Action> getAllNextAction()
	{
		List<Action> ret = new LinkedList<Action>();
		for (StateActionRegistry.A i : toDo)
		{
			Action a = StateActionRegistry.getActionFromID(i);
			if (a == null)
				continue;
			if (a.setUp(this))
				ret.add(a);
		}
		return ret;
	}
	public abstract boolean setUp(Action action); 
	// Only have to handle actions whose preconds (for Action.getPreconds) /
	// postconds (for Action.execute) are this state
}
