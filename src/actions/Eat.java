package actions;

import globalRegistry.StateActionRegistry;
import state.IncreaseSaturation;
import state.Own;
import state.State;
import agent.ActiveAgent;
import agent.Agent;

@SuppressWarnings("unused")
public class Eat extends Action {
	public ActiveAgent owner;
	public Agent food;

	public Eat(ActiveAgent o, Agent f)
	{
		ID = StateActionRegistry.EAT;
		name = "eat";
		defaultCost = 10;
		preConds.add(StateActionRegistry.OWN);
		postConds.add(StateActionRegistry.INCREASESAT);
		owner = o;
		food = f;
	}
	public Eat()
	{
		ID = StateActionRegistry.EAT;
		name = "eat";
		defaultCost = 10;
	}
	
	@Override
	public Action getNewInstance() {
		return new Eat();
	}



	@Override
	public int getCost() {
		return defaultCost;
	}


	@Override
	public boolean setUp(State s) {
		if (s == null)
			return false;
		// PRECOND?
/*		if (s.ID == StateActionRegistry.OWN)
		{
			Own o = (Own) s;
			if (o.owner == null || o.owned == null)
				return false;
			owner = o.owner;
			food = o.owned;
			return true;
		}*/
		
		//POSTCOND? MAYBE? Now is handled by IncreaseSaturation
		return false;

	}
	@Override
	public void print() {
		// TODO Auto-generated method stub
		if (owner == null || food == null)
			return;
		System.out.println(owner.name + " eats " + food.name);
		
	}

}
