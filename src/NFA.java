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
			startState.getTransitions().add(new Transition(symbol, finalState));
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
	
	public void concatenate(NFA other)
	{
		Set<State> finalStates = getAcceptStates();
		State otherStartState = other.getStartState();
		
		// add all states from the other NFA
		addAllStates(other);
		
		// remove all our final states, and add epsilon transitions
		// from them to the other NFA's start state
		for (State state : finalStates)
		{
			state.setIsAcceptState(false);
			state.addTransition(new Transition(EPSILON, otherStartState));
		}
		
		// remove start state from the other NFA
		otherStartState.setIsStartState(false);
	}
	
	public void wrapInKleeneClosure()
	{
		throw new RuntimeException("not implemented");
	}
	
	public void union(NFA other)
	{
		State ourStartState = getStartState();
		State otherStartState = other.getStartState();
		
		// add all states from the other NFA
		addAllStates(other);
		
		// add a new state start state with epsilon transitions to the
		// old start states
		State newStartState = new State();
		newStartState.setIsStartState(true);
		newStartState.addTransition(new Transition(EPSILON, ourStartState));
		newStartState.addTransition(new Transition(EPSILON, otherStartState));
		addState(newStartState);
		
		// remove start state status from the old start states
		ourStartState.setIsStartState(false);
		otherStartState.setIsStartState(false);
	}
	
	//--------------------------------------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------------------------------------
	public Set<State> getStates()
	{
		return states;
	}
}
