import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Converts NFA's to DFA's using a ConverterState subclass and the algorithm 
 * described in class.
 */
public class Converter
{
	public static DFA convertNfaToDfa(Automaton nfa, Set<String> alphabet)
	{
		DFA dfa = new DFA();
		
		Set<State> startStates = nfa.getStartState().epsilonClosure();
		ConverterState startState = new ConverterState(startStates);
		startState.setIsStartState(true);
		dfa.addState(startState);
		
		// each converter state only needs to be visited once. keep running
		// this loop until all converter states are visited (i.e. a transition
		// is added to some other converter states for every symbol in the alphabet)
		// some new converter states may be added during this process
		ConverterState state = null;
		while ((state = getFirstUnvisitedState(dfa)) != null)
		{
			state.setVisited(true);
			for (String symbol : alphabet)
			{
				ConverterState nextState = state.createNextStateForSymbol(symbol);
				nextState = addStateIfNotExists(dfa, nextState);
				state.addTransition(new Transition(symbol, nextState));
			}
		}
		return dfa;
	}
	
	private static ConverterState getFirstUnvisitedState(Automaton dfa)
	{
		for (State state : dfa.getStates())
		{
			if (!state.isVisited())
			{
				return (ConverterState)state;
			}
		}
		return null;
	}
	
	private static ConverterState addStateIfNotExists(Automaton dfa, ConverterState state)
	{
		for (State existing : dfa.getStates())
		{
			if (existing.equals(state))
			{
				return (ConverterState)existing;
			}
		}
		dfa.addState(state);
		return (ConverterState)state;
	}
	
	/**
	 * Each converter state represents a single state in the new DFA. It is uniquely identified
	 * by its subset of NFA states. i.e. two ConverterStates are equal if their set of
	 * NFA states are equal.
	 */
	private static class ConverterState extends State
	{
		public ConverterState(Set<State> nfaStates)
		{
			super();
			setData(nfaStates);
			for (State nfaState : nfaStates)
			{
				if (nfaState.isAcceptState())
				{
					setIsAcceptState(true);
					break;
				}
			}
		}
		
		public Set<State> getNextNfaStatesForSymbol(String symbol)
		{
			@SuppressWarnings("unchecked")
			Set<State> nfaStates = (Set<State>)getData();
			Set<State> result = new LinkedHashSet<State>();
			
			for (State state : nfaStates)
			{
				result.addAll(state.getNextStatesForSymbol(symbol));
			}
			return result;
		}
		
		public ConverterState createNextStateForSymbol(String symbol)
		{
			// the new DFA state contains all the NFA states that can be reached
			// from one of our NFA states by following the given symbol and
			// then any number of epsilon's
			Set<State> nextNfaStates = getNextNfaStatesForSymbol(symbol);
			Set<State> result = new LinkedHashSet<State>();
			
			for (State nextState : nextNfaStates)
			{
				result.addAll(nextState.epsilonClosure());
			}
			return new ConverterState(result);
		}
		
		@Override
		public boolean equals(Object other)
		{
			if (other != null && other instanceof ConverterState)
			{
				ConverterState otherState = (ConverterState)other;
				Object otherData = otherState.getData();
				if (otherData != null && otherData instanceof Set)
				{
					return ((Set<?>)otherData).equals((Set<?>)this.getData());
				}
			}
			return false;
		}
	}
}