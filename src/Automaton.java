import java.util.LinkedHashSet;
import java.util.Set;


public class Automaton
{	
	private Set<State> states;
	
	public Automaton()
	{
		states = new LinkedHashSet<State>();
	}
	
	public void addState(State state)
	{
		states.add(state);
	}
	
	public void addAllStates(NFA other)
	{
		for (State state : other.getStates())
		{
			states.add(state);
		}
	}
	
	public State getStartState()
	{
		for (State state : states)
		{
			if (state.isStartState())
			{
				return state;
			}
		}
		return null;
	}
	
	public Set<State> getAcceptStates()
	{
		Set<State> result = new LinkedHashSet<State>();
		for (State state : states)
		{
			if (state.isAcceptState())
			{
				result.add(state);
			}
		}
		return result;
	}
	
	//--------------------------------------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------------------------------------
	public Set<State> getStates()
	{
		return states;
	}
	
	//--------------------------------------------------------------------------------
	// Helpers
	//--------------------------------------------------------------------------------
	@Override
	public String toString()
	{
		return states.toString();
	}
}
