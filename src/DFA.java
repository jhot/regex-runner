import java.util.Set;

/**
 * Subclass of Automaton. Includes utility methods to tell if the Automaton
 * is a valid DFA and check whether input strings are accepted
 */
public class DFA extends Automaton
{
	public boolean acceptsString(Set<String> alphabet, String inputString)
	{		
		if (!isValid(alphabet))
		{
			return false;
		}
		State state = getStartState();
		for (int i = 0; i < inputString.length(); i++)
		{
			// ignore epsilon's, i.e. they don't move us to a new state. this
			// can only happen if the original string is just "e".
			String symbol = inputString.charAt(i) + "";
			if (Transition.EPSILON.equals(symbol))
			{
				continue;
			}
			state = state.getNextDfaStateForSymbol(symbol);
		}
		return state.isAcceptState();
	}
	
	public boolean isValid(Set<String> alphabet)
	{
		// must have exactly one transition for each symbol in the alphabet
		for (State state : getStates())
		{
			if (state.getTransitions().size() != alphabet.size())
			{
				return false;
			}
			for (Transition t : state.getTransitions())
			{
				if (!alphabet.contains(t.getSymbol()))
				{
					return false;
				}
			}
		}
		// must have exactly one start state
		int startStateCount = 0;
		for (State state : getStates())
		{
			if (state.isStartState())
			{
				startStateCount++;
			}
		}
		if (startStateCount != 1)
		{
			return false;
		}
		return true;
	}
}
