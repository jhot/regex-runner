import java.util.LinkedHashSet;
import java.util.Set;


public class NFA
{
	private Set<State> states;
	
	public static NFA createNfaForSymbol(String symbol)
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
