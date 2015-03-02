package actions;

import globalRegistry.StateActionRegistry;
import globalRegistry.StatusCharacteristicRegistry;
import agent.ActiveAgent;
import agent.Agent;
import agent.Characteristic;
import state.Own;
import state.State;

public class Steal extends Action {
	public ActiveAgent owner = null;
	public Agent stolen = null;
	
	public Steal()
	{
		ID = StateActionRegistry.STEAL;
		name = "take";
		preConds.add(StateActionRegistry.AT);
		postConds.add(StateActionRegistry.OWN);
		defaultCost = 100;
	}
	
	public Steal(ActiveAgent o, Agent s)
	{
		ID = StateActionRegistry.STEAL;
		name = "steal";
		owner = o;
		stolen = s;
	}
	
	@Override
	public Action getNewInstance() {
		return new Steal();
	}

	@Override
	public int getCost() {
		if (stolen == null || owner == null || stolen.owner == null)
		{
			return Integer.MAX_VALUE;
		}
		Characteristic c = owner.findChar(StatusCharacteristicRegistry.HONEST);
		if (c != null)
		{
			return c.amount + defaultCost;
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
			if (o.owned == null || o.owner == null || o.owned.owner == null)
				return false;
			owner = o.owner;
			stolen = o.owned;				
			return true;
		}
		return false;
	}

	@Override
	public void print() {
		if (stolen == null || owner == null || stolen.owner == null)
		{
			return;
		}
		System.out.println(owner.name + " steals " + stolen.name + " from " + stolen.owner.name);
	}

}
