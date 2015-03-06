package globalRegistry;
import state.AtState;
import state.IncreaseSaturation;
import state.Own;
import state.StartingState;
import state.State;
import actions.Action;
import actions.AskTo;
import actions.Eat;
import actions.Give;
import actions.Go;
import actions.Steal;
import actions.Take;


public abstract class StateActionRegistry {
	public enum S { AT, INCREASESAT, START, OWN}
	public enum A {STEAL, GO, ASKTO, GIVE, EAT, TAKE }
	//public static int AT = 0;
	//public static int INCREASESAT = 1;
	//public static int EAT = 2;
	//public static int OWN = 3;
	//public static int TAKE = 4;
	//public static int GO = 5;
	//public static int START = 6;
	//public static int STEAL = 7;
	//public static int ASKTO = 8;
	//public static int GIVE = 9;
	
	public static Action getActionFromID(A ID)
	{
		if (ID == A.EAT)
		{
			return new Eat();
		}
		if (ID == A.TAKE)
		{
			return new Take();
		}
		if (ID == A.GO)
		{
			return new Go();
		}
		if (ID == A.STEAL)
		{
			return new Steal();
		}
		if (ID == A.ASKTO)
		{
			return new AskTo();
		}
		if (ID == A.GIVE)
		{
			return new Give();
		}
		return null;
	}
	public static State getStateFromID(S ID)
	{
		if (ID == S.OWN)
		{
			return new Own();
		}
		if (ID == S.AT)
		{
			return new AtState();
		}
		if (ID == S.INCREASESAT)
		{
			return new IncreaseSaturation();
		}
		if (ID == S.START)
		{
			return new StartingState();
		}
		
		return null;
	}
}
