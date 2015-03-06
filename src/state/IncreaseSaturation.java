package state;

import globalRegistry.AgentRegistry;
import globalRegistry.StateActionRegistry;
import globalRegistry.StatusCharacteristicRegistry;

import java.util.LinkedList;
import java.util.List;

import actions.Action;
import actions.Eat;
import agent.ActiveAgent;
import agent.Agent;
import agent.Characteristic;
import agent.Status;

public class IncreaseSaturation extends State {

	public ActiveAgent owner;
	public List<Agent> food = new LinkedList<Agent>();
	public Agent currFood;
	public IncreaseSaturation(ActiveAgent o)
	{
		ID = StateActionRegistry.S.INCREASESAT;
		name = "IncreaseSat";
		owner = o;
		toDo.add(StateActionRegistry.A.EAT);
		currFood = null;
		for (Agent f : AgentRegistry.alist)
		{
			for (Characteristic c : f.characteristics)
			{
				if(c.ID == StatusCharacteristicRegistry.C.EDIBLE)
				{
					food.add(f);
					currFood = f;
					break;
				}
			}
		}
	}
	public IncreaseSaturation()
	{
		ID = StateActionRegistry.S.INCREASESAT;
		name = "IncreaseSaturation";
		toDo.add(StateActionRegistry.A.EAT);
		currFood = null;
		for (Agent f : AgentRegistry.alist)
		{
			for (Characteristic c : f.characteristics)
			{
				if(c.ID == StatusCharacteristicRegistry.C.EDIBLE)
				{
					food.add(f);
					currFood = f;
					break;
				}
			}
		}
	}
	
	public boolean reset()
	{
		currFood = null;
		for (Agent f : AgentRegistry.alist)
		{
			for (Characteristic c : f.characteristics)
			{
				if(c.ID == StatusCharacteristicRegistry.C.EDIBLE)
				{
					food.add(f);
					currFood = f;
					break;
				}
			}
		}
		return true;
	}
	
	public List<Action> getAllNextAction()
	{
		List<Action> ret = new LinkedList<Action>();
		for(Agent f : AgentRegistry.alist)
		{
			for (Characteristic c : f.characteristics)
			{
				if(c.ID == StatusCharacteristicRegistry.C.EDIBLE)
				{
					ret.add(new Eat(owner, f));
				}
			}
		}
		return ret;
	}

	@Override
	public boolean satisfied() {
		return false;
	}

	@Override
	public boolean execute() {
		if (currFood == null || currFood.owner != owner)
		{
			return false;
		}
		
		// Find out the amount this food gives
		int amountOfIncrease = 0;
		for (int i = 0; i < currFood.characteristics.size(); i++)
		{
			Characteristic c = currFood.characteristics.get(i);
			if (c.ID == StatusCharacteristicRegistry.C.EDIBLE)
			{
				amountOfIncrease = c.amount;
				
				// Also, get rid of the "C.EDIBLE" quality of this food
				currFood.characteristics.remove(i);
			}
		}
		if (amountOfIncrease == 0)
		{
			return false;
		}
		
		// Find the owner's saturation status and increase it
		for (Status c : owner.statuses)
		{
			if (c.ID == StatusCharacteristicRegistry.S.SATURATION)
			{
				c.amount += amountOfIncrease;
				return true;
			}
		}
		return false;
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
		if (action.ID == StateActionRegistry.A.EAT)
		{
			Eat e = (Eat) action;
			if (e.owner == null || e.food == null)
			{
				return false;
			}
			owner = e.owner;
			currFood = e.food;
			return true;
		}
		
		// ACTIONS WHOSE PRECOND INCLUDES THIS STATE
		
		return false;
	}

}
