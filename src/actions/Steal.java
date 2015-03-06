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
		ID = StateActionRegistry.A.STEAL;
		name = "take";
		preConds.add(StateActionRegistry.S.AT);
		postConds.add(StateActionRegistry.S.OWN);
		defaultCost = 100;
	}
	
	public Steal(ActiveAgent o, Agent s)
	{
		ID = StateActionRegistry.A.STEAL;
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
		Characteristic c = owner.findChar(StatusCharacteristicRegistry.C.HONEST);
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
		if (s.ID == StateActionRegistry.S.OWN)
		{
			Own o = (Own) s;
			if (o.owned == null || o.owner == null
								|| o.owned.owner == null
								|| o.owned.owner == o.owner) // can't steal from yourself...
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
