import java.util.LinkedHashSet;
import java.util.Set;


public class NFA
{
	public static final String EPSILON = "e";
	
	private Set<State> states;
	
	public static NFA createNfaForSymbol(String symbol)
	{
		if (symbol.equalsIgnoreCase(EPSILON))
		{
			State state = new State();
			state.setIsStartState(true);
			state.setIsAcceptState(true);
			
			NFA nfa = new NFA();
			nfa.addState(state);
			return nfa;
		}
		else
		{
			NFA nfa = new NFA();
			State startState = new State();
			State finalState = new State();
			
			startState.setIsStartState(true);
			startState.getTransitions().put(symbol, finalState);
			finalState.setIsAcceptState(true);
			
			nfa.addState(startState);
			nfa.addState(finalState);
			return nfa;
		}
	}
	
	public NFA()
	{
		states = new LinkedHashSet<State>();
	}
	
	public void addState(State state)
	{
		states.add(state);
	}
	
	public NFA concatenate(NFA other)
	{
		
		
		throw new RuntimeException("not implemented");
	}
	
	public NFA wrapInKleeneClosure()
	{
		throw new RuntimeException("not implemented");
	}
	
	public NFA union(NFA other)
	{
		throw new RuntimeException("not implemented");
	}
	
	//--------------------------------------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------------------------------------
	public Set<State> getStates()
	{
		return states;
	}
}
