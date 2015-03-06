package state;

import globalRegistry.AgentRegistry;
import globalRegistry.StateActionRegistry;
import location.Location;
import actions.Action;
import actions.AskTo;
import actions.Give;
import actions.Go;
import actions.Steal;
import actions.Take;
import agent.ActiveAgent;
import agent.Agent;



public class AtState extends State {
	public Location loc = null; // This means the owner is at this actual location
	public Agent locationOf = null; // This means the owner is at the location of this agent, whereever that agent is
	public ActiveAgent owner = null;

	public AtState() {
		ID  = StateActionRegistry.S.AT;
		name = "at";
		toDo.add(StateActionRegistry.A.GO);
	}
	
	public AtState(ActiveAgent p, Location l)
	{
		ID  = StateActionRegistry.S.AT;
		name = "at";
		toDo.add(StateActionRegistry.A.GO);
		loc = l;
		owner = p;
		locationOf = null;
	}
	
	public AtState(ActiveAgent p, Agent l)
	{
		ID  = StateActionRegistry.S.AT;
		name = "at";
		toDo.add(StateActionRegistry.A.GO);
		locationOf = l;
		owner = p;
		loc = null;
	}


	@Override
	public boolean satisfied() {
		if (owner == null)
			return false;
		if (loc != null)
			return owner.loc == loc;
		if (locationOf != null)
			return owner.loc == locationOf.loc;
		return false;
	}

	@Override
	public boolean execute() {
		Location l = null;
		if (loc == null && locationOf == null)
			return false;
		if (loc != null)
			l = loc;
		else if (locationOf != null)
			l = locationOf.loc;
		
		if (l == null)
			return false;
		owner.loc = l;
		
		// owner now knows the locations of all agents in this location
		for (Agent a : AgentRegistry.alist)
		{
			if (a.loc == owner.loc && a != owner)
				owner.knowsLocationOf.put(a, loc);
		}
		
		// owner's inventory moves too
		for (Agent a : owner.inventory)
		{
			a.loc = owner.loc;
		}
		return true;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setUp(Action action) {
		// TODO Auto-generated method stub
		if (action == null)
			return false;
		
		// ACTIONS THAT LEAD TO THIS STATE
		if (action.ID == StateActionRegistry.A.GO)
		{
			Go g = (Go) action;
			if (g.owner == null || (g.loc == null && g.locationOf == null))
					return false;
			owner = g.owner;
			if (g.loc != null)
			{
				loc = g.loc;
			}
			else
			{
				locationOf = g.locationOf;
			}
			return true;
		}
		
		
		// ACTIONS WHOSE PRECOND INCLUDES THIS STATE
		if (action.ID == StateActionRegistry.A.TAKE)
		{
			Take t = (Take) action;
			if (t.owner == null || t.taken == null)
				return false;
			owner = t.owner;
			locationOf = t.taken;
			return true;
		} else if (action.ID == StateActionRegistry.A.STEAL)
		{
			
			Steal s = (Steal) action;
			if (s.owner == null || s.stolen == null)
				return false;
			owner = s.owner;
			locationOf = s.stolen;
			return true;
		} else if (action.ID == StateActionRegistry.A.ASKTO)
		{
			AskTo a = (AskTo) action;
			if (a.asker == null || a.askee == null)
				return false;
			owner = a.asker;
			locationOf = a.askee;
			return true;
		} else if (action.ID == StateActionRegistry.A.GIVE)
		{
			Give g = (Give) action;
			if (g.giver == null || g.givee == null || g.obj == null || g.obj.owner != g.giver)
				return false;
			owner = g.giver;
			locationOf = g.givee;
			return true;
		}
		
		
		return false;
	}

}
