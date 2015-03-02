package planner;
import state.State;


public interface Planner {
	public PlannerRetVal plan(State start, int maxNumSteps);
}
