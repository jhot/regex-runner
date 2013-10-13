import java.util.HashMap;
import java.util.Map;

public class State
{
	public static int newStateId = 0;
	
	private int id;
	private boolean isStartState;
	private boolean isAcceptState;
	private Map<String, State> transitions;
	
	public State()
	{
		id = newStateId++;
		transitions = new HashMap<String, State>();
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

	public Map<String, State> getTransitions()
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