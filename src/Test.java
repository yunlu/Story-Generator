import globalRegistry.AgentRegistry;
import globalRegistry.StatusCharacteristicRegistry;

import java.util.LinkedList;
import java.util.List;

import location.Location;
import planner.LazyPlanner;
import planner.Planner;
import state.IncreaseSaturation;
import state.State;
import agent.ActiveAgent;
import agent.Characteristic;
import agent.Goal;
import agent.PassiveAgent;
import agent.Status;




public class Test {
	public static void main(String args[])
	{
		Location loc = new Location();
		loc.name = "Silent Hill";
		Location loc1 = new Location();
		loc1.name = "Uncanny Valley";
		Location loc2 = new Location();
		loc2.name = "Racoon City";
		Location loc3 = new Location();
		loc3.name = "Minakami Village";
		

		
		loc.connectedTo.add(loc3);
		loc3.connectedTo.add(loc);
	
		loc1.connectedTo.add(loc3);
		loc3.connectedTo.add(loc1);
		
		loc1.connectedTo.add(loc2);
		loc2.connectedTo.add(loc1);
	
		
		ActiveAgent a = new ActiveAgent();
		a.name = "Agatha";
		a.loc = loc;
		
		PassiveAgent b = new PassiveAgent();
		b.name = "Apple";
		b.loc = loc2;
		
		ActiveAgent c = new ActiveAgent();
		c.name = "Leon";
		c.loc = loc2;
		
		PassiveAgent d = new PassiveAgent();
		d.name = "Porsche";
		d.loc = loc;
		
		b.owner = c;
		c.inventory.add(b);
		
		d.owner = a;
		a.inventory.add(d);
		
		
		Characteristic edible = new Characteristic();
		edible.ID = StatusCharacteristicRegistry.EDIBLE;
		edible.amount = 50;
		edible.name = "edible";
		b.characteristics.add(edible);
		
		
		
		AgentRegistry.alist.add(a);
		AgentRegistry.alist.add(b);
		
		
		
		List<State> incrSat = new LinkedList<State>();
		incrSat.add(new IncreaseSaturation(a));
		a.statuses.add(new Status(StatusCharacteristicRegistry.SATURATION, 
				"SATURATION", 20, 10, 50, 
				new Goal("Eat", incrSat, null)));
		Planner p = new LazyPlanner();
		while (true)
		{
			boolean ta = false, tc = false;
			if (a.statuses.size() > 0 && a.statuses.get(0).goal.execute(p, 5))
			{
				ta = true;
			}
			if (c.statuses.size() > 0 && c.statuses.get(0).goal.execute(p, 5))
			{
				tc = true;
			}
			if (ta == false && tc == false)
				break;
		}
		System.out.println(a.statuses.get(0).amount);
	}
}
