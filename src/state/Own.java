package state;

import globalRegistry.StateActionRegistry;
import actions.Action;
import actions.Eat;
import actions.Give;
import actions.Steal;
import actions.Take;
import agent.ActiveAgent;
import agent.Agent;

public class Own extends State {
	public ActiveAgent owner = null;
	public Agent owned = null;

	public Own()
	{
		ID = StateActionRegistry.S.OWN;
		name = "own";
		toDo.add(StateActionRegistry.A.TAKE);
		toDo.add(StateActionRegistry.A.STEAL);
		toDo.add(StateActionRegistry.A.ASKTO);
	}
	public Own(ActiveAgent o, Agent i)
	{
		ID = StateActionRegistry.S.OWN;
		name = "own";
		toDo.add(StateActionRegistry.A.TAKE);
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
		if(owned.owner != null)
		{
			owned.owner.inventory.remove(owned);
		}
		owner.inventory.add(owned);
		
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
		if (action.ID == StateActionRegistry.A.EAT)
		{
			Eat e = (Eat) action;
			if (e.owner == null || e.food == null)
				return false;
			owner = e.owner;
			owned = e.food;
			return true;
		}
		
		// ACTIONS WHOS POSTCONDS INCLUDE THIS STATE
		if (action.ID == StateActionRegistry.A.TAKE)
		{
			Take t = (Take) action;
			if (t.owner == null || t.taken == null)
				return false;
			owner = t.owner;
			owned = t.taken;
			return true;
		}
		else if (action.ID == StateActionRegistry.A.STEAL)
		{
			Steal s = (Steal) action;
			if (s.owner == null || s.stolen == null)
				return false;
			owner = s.owner;
			owned = s.stolen;
			return true;
		} else if (action.ID == StateActionRegistry.A.GIVE)
		{
			Give g = (Give) action;
			if (g.giver == null || g.givee == null || g.obj == null || g.obj.owner != g.giver)
				return false;
			owner = g.givee;
			owned = g.obj;
			return true;
		}
		
		return false;
	}

}
