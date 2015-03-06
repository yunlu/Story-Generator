package actions;
import globalRegistry.StateActionRegistry;
import globalRegistry.StatusCharacteristicRegistry;
import state.Own;
import state.State;
import agent.ActiveAgent;
import agent.Goal;
import agent.Status;


public class AskTo extends Action {
	public ActiveAgent asker = null;
	public ActiveAgent askee = null;
	public Action toDo = null;
	
	public AskTo()
	{
		ID = StateActionRegistry.A.ASKTO;
		name = "ask to";
		preConds.add(StateActionRegistry.S.AT);
	}
	
	public AskTo(ActiveAgent ar, ActiveAgent ae, Action t)
	{
		ID = StateActionRegistry.A.ASKTO;
		name = "ask to";
		preConds.add(StateActionRegistry.S.AT);
		asker = ar;
		askee = ae;
		toDo = t;
	}
	
	@Override
	public Action getNewInstance() {
		return new AskTo();
	}

	@Override
	public int getCost() {
		return defaultCost+10;
	}

	@Override
	public boolean setUp(State s) {
		if (s == null)
			return false;
		// Deal with asking for...
		if (s.ID == StateActionRegistry.S.OWN)
		{
			Own o = (Own) s;
			if (o.owner == null || o.owned == null 
								|| o.owned.owner == null // the asked object has to have an owner
								|| o.owned.owner == o.owner) // cannot ask yourself to give yourself stuff
				return false;
			asker = o.owner;
			askee = o.owned.owner;
			toDo = new Give(askee, asker, o.owned);
			return true;
		}
		return false;
	}

	@Override
	public void print() {
		if (asker == null || askee == null || toDo == null)
			return;
		System.out.print(asker.name + " asks " + askee.name + " to: ");
		toDo.print();

	}

	public boolean execute() {
		print();
		Goal g = new Goal("give", null, toDo);
		Status request = new Status(StatusCharacteristicRegistry.S.REQUEST, "request", 0, 10, 10, g);
		askee.statuses.add(request);
		return true;
	}
}
