import java.util.ArrayList;
import java.util.List;

public class State
{
	public static int newStateId = 0;
	
	private int id;
	private boolean isStartState;
	private boolean isAcceptState;
	private List<Transition> transitions;
	
	public State()
	{
		id = newStateId++;
		transitions = new ArrayList<Transition>();
	}
	
	public void addTransition(Transition transition)
	{
		transitions.add(transition);
	}
	
	//--------------------------------------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------------------------------------
	public int getId()
	{
		return id;
	}

	public boolean isStartState()
	{
		return isStartState;
	}

	public void setIsStartState(boolean isStartState)
	{
		this.isStartState = isStartState;
	}

	public boolean isAcceptState()
	{
		return isAcceptState;
	}

	public void setIsAcceptState(boolean isAcceptState)
	{
		this.isAcceptState = isAcceptState;
	}

	public List<Transition> getTransitions()
	{
		return transitions;
	}
	
	//--------------------------------------------------------------------------------
	// Other Methods and Helpers
	//--------------------------------------------------------------------------------
	@Override
	public boolean equals(Object obj)
	{
		if (obj != null && obj instanceof State)
		{
			return ((State)obj).id == this.id;
		}
		return super.equals(obj);
	}
}