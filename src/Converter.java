import java.util.LinkedHashSet;
import java.util.Set;

public class Converter
{
	public static DFA convertNfaToDfa(Automaton nfa, Set<String> alphabet)
	{
		DFA dfa = new DFA();
		
		Set<State> startStates = nfa.getStartState().epsilonClosure();
		ConverterState startState = new ConverterState(startStates);
		startState.setIsStartState(true);
		dfa.addState(startState);
		
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