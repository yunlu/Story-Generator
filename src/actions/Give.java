package actions;

import globalRegistry.StateActionRegistry;
import agent.ActiveAgent;
import agent.Agent;
import state.State;

public class Give extends Action {
	public ActiveAgent giver = null;
	public ActiveAgent givee = null;
	public Agent obj = null;
	
	public Give()
	{
		ID = StateActionRegistry.A.GIVE;
		name = "give";
		preConds.add(StateActionRegistry.S.AT);
		postConds.add(StateActionRegistry.S.OWN);
	}
	
	public Give(ActiveAgent askee, ActiveAgent asker, Agent owned) {
		ID = StateActionRegistry.A.GIVE;
		name = "give";
		giver = askee;
		givee = asker;
		obj = owned;
		preConds.add(StateActionRegistry.S.AT);
		postConds.add(StateActionRegistry.S.OWN);
	}
	
	
	
	

	@Override
	public Action getNewInstance() {
		return new Give();
	}

	@Override
	public int getCost() {
		return defaultCost + 100;
	}

	@Override
	public boolean setUp(State s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void print() {
		if (giver == null || givee == null || obj == null)
			return;
		System.out.println(giver.name + " gives " + givee.name + " " + obj.name);

	}

}
