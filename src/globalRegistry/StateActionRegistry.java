package globalRegistry;
import state.AtState;
import state.IncreaseSaturation;
import state.Own;
import state.StartingState;
import state.State;
import actions.Action;
import actions.Eat;
import actions.Go;
import actions.Steal;
import actions.Take;


public abstract class StateActionRegistry {
	public static int AT = 0;
	public static int INCREASESAT = 1;
	public static int EAT = 2;
	public static int OWN = 3;
	public static int TAKE = 4;
	public static int GO = 5;
	public static int START = 6;
	public static int STEAL = 7;
	
	public static Action getActionFromID(int ID)
	{
		if (ID == EAT)
		{
			return new Eat();
		}
		if (ID == TAKE)
		{
			return new Take();
		}
		if (ID == GO)
		{
			return new Go();
		}
		if (ID == STEAL)
		{
			return new Steal();
		}
		return null;
	}
	public static State getStateFromID(int ID)
	{
		if (ID == OWN)
		{
			return new Own();
		}
		if (ID == AT)
		{
			return new AtState();
		}
		if (ID == INCREASESAT)
		{
			return new IncreaseSaturation();
		}
		if (ID == START)
		{
			return new StartingState();
		}
		
		return null;
	}
}
