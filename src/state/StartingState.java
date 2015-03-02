package state;

import actions.Action;

public final class StartingState extends State {


	@Override
	public boolean satisfied() {
		return true;
	}

	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setUp(Action action) {
		// TODO Auto-generated method stub
		return true;
	}

}
