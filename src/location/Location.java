package location;
import java.util.LinkedList;
import java.util.List;


public class Location {
	public String name;
	public List<Location> connectedTo = new LinkedList<Location>();
}
