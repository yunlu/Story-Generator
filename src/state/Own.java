package state;

import globalRegistry.StateActionRegistry;
import actions.Action;
import actions.Eat;
import actions.Steal;
import actions.Take;
import agent.ActiveAgent;
import agent.Agent;

public class Own extends State {
	public ActiveAgent owner = null;
	public Agent owned = null;

	public Own()
	{
		ID = StateActionRegistry.OWN;
		name = "own";
		toDo.add(StateActionRegistry.TAKE);
		toDo.add(StateActionRegistry.STEAL);
	}
	public Own(ActiveAgent o, Agent i)
	{
		ID = StateActionRegistry.OWN;
		name = "own";
		toDo.add(StateActionRegistry.TAKE);
		owner = o;
		owned = i;
	}

	@Override
	public boolean satisfied() {
		if (owner == null)
		{
			return false;
		}
		for (Agent a : owner.inventory)
		{
			if (a == owned)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean execute() {
		owner.inventory.add(owned);
		if(owned.owner != null)
		{
			owned.owner.inventory.remove(owned);
		}
		owned.owner = owner;
		return true;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean setUp(Action action) {
		// TODO Auto-generated method stub
		// ACTIONS WHOSE PRECONDS INCLUDE THIS STATE
		if (action == null)
			return false;
		if (action.ID == StateActionRegistry.EAT)
		{
			Eat e = (Eat) action;
			if (e.owner == null || e.food == null)
				return false;
			owner = e.owner;
			owned = e.food;
			return true;
		}
		
		// ACTIONS WHOS POSTCONDS INCLUDE THIS STATE
		if (action.ID == StateActionRegistry.TAKE)
		{
			Take t = (Take) action;
			if (t.owner == null || t.taken == null)
				return false;
			owner = t.owner;
			owned = t.taken;
			return true;
		}
		else if (action.ID == StateActionRegistry.STEAL)
		{
			Steal s = (Steal) action;
			if (s.owner == null || s.stolen == null)
				return false;
			owner = s.owner;
			owned = s.stolen;
			return true;
		}
		
		return false;
	}

}
