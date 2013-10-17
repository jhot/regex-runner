import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class State
{
	public static int newStateId = 1;
	
	private int id;
	private boolean visited;
	private Object data;
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
	
	public Set<State> getNextStatesForSymbol(String symbol)
	{
		Set<State> result = new LinkedHashSet<State>();
		for (Transition t : transitions)
		{
			if (symbol.equals(t.getSymbol()))
			{
				result.add(t.getNextState());
			}
		}
		return result;
	}
	
	public Set<State> epsilonClosure()
	{
		return epsilonClosure(new ArrayList<State>());
	}
	
	private Set<State> epsilonClosure(List<State> visitedStates)
	{
		visitedStates.add(this);
		
		Set<State> result = new LinkedHashSet<State>();
		result.add(this);
		
		for (Transition t : transitions)
		{
			if (Transition.EPSILON.equals(t.getSymbol()))
			{
				State next = t.getNextState();
				if (!visitedStates.contains(next))
				{
					result.addAll(next.epsilonClosure());
				}
			}
		}
		return result;
	}
	
	//--------------------------------------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------------------------------------
	public int getId()
	{
		return id;
	}
	
	public Object getData()
	{
		return data;
	}
	
	public boolean isVisited()
	{
		return visited;
	}
	
	public void setVisited(boolean visited)
	{
		this.visited = visited;
	}
	
	public void setData(Object data)
	{
		this.data = data;
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
	
	@Override
	public String toString()
	{
		String result = "";
		if (isStartState)
		{
			result += "S";
		}
		if (isAcceptState)
		{
			result += "A";
		}
		result += "(" + id + "){";
		for (Transition t : transitions)
		{
			result += t + ",";
		}
		int commaIndex = result.lastIndexOf(",");
		if (commaIndex >= 0)
		{
			result = result.substring(0, commaIndex);
		}
		result += "}";
		return result;
	}
}