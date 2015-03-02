package agent;
import java.util.LinkedList;
import java.util.List;

import location.Location;


public abstract class Agent {
	public int ID;
	public String name;
	public List<Characteristic> characteristics = new LinkedList<Characteristic>();
	public Location loc = null;
	public ActiveAgent owner = null;
	public Characteristic findChar(int i)
	{
		for (Characteristic c : characteristics)
		{
			if (c.ID == i)
				return c;
		}
		return null;
	}
}
