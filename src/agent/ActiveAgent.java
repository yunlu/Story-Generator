package agent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import location.Location;


public class ActiveAgent extends Agent {
	public List<Status> statuses = new LinkedList<Status>();
	public List<Agent> inventory = new LinkedList<Agent>();
	public Map<Agent,Location> knowsLocationOf = new HashMap<Agent,Location>();
	
}
