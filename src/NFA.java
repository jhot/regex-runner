import java.util.Set;


public class NFA extends Automaton
{	
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
		State oldStartState = getStartState();
		Set<State> finalStates = getAcceptStates();
		
		// add a new start state, set as accept state, and add epsilon
		// transition from it to the old start state
		State newStartState = new State();
		newStartState.setIsStartState(true);
		newStartState.setIsAcceptState(true);
		newStartState.addTransition(new Transition(EPSILON, oldStartState));
		addState(newStartState);
		
		// add epsilon transitions from all final states to the old
		// start state
		for (State finalState : finalStates)
		{
			finalState.addTransition(new Transition(EPSILON, oldStartState));
		}
		
		// remove old start state's accept status
		oldStartState.setIsStartState(false);
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
}