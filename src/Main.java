import java.util.LinkedHashSet;
import java.util.Set;


public class Main
{
	public static void main(String[] args)
	{
		NFAParser.parseStringToNfa("(a|b)*a");
		NFAParser.parseStringToNfa("((a|b)(a|b))*");
		NFAParser.parseStringToNfa("aaa*b*a*a");
		
		NFA nfa = new NFA();
		State[] states = new State[11];
		for (int i = 0; i < states.length; i++)
		{
			states[i] = new State();
			nfa.addState(states[i]);
		}
		Set<String> alphabet = new LinkedHashSet<String>();
		alphabet.add("a");
		alphabet.add("b");

		states[0].addTransition(new Transition(Transition.EPSILON, states[1]));
		states[0].addTransition(new Transition(Transition.EPSILON, states[7]));
		states[1].addTransition(new Transition(Transition.EPSILON, states[2]));
		states[1].addTransition(new Transition(Transition.EPSILON, states[4]));
		states[2].addTransition(new Transition("a", states[3]));
		states[4].addTransition(new Transition("b", states[5]));
		states[3].addTransition(new Transition(Transition.EPSILON, states[6]));
		states[5].addTransition(new Transition(Transition.EPSILON, states[6]));
		states[6].addTransition(new Transition(Transition.EPSILON, states[1]));
		states[6].addTransition(new Transition(Transition.EPSILON, states[7]));
		states[7].addTransition(new Transition("a", states[8]));
		states[8].addTransition(new Transition("b", states[9]));
		states[9].addTransition(new Transition("b", states[10]));
		
		states[0].setIsStartState(true);
		states[10].setIsAcceptState(true);
		
		Automaton dfa = Converter.convertNfaToDfa(nfa, alphabet);
		
		System.out.println("\nState 1 Epsilon Closure:");
		System.out.println(states[1].epsilonClosure());
		
		System.out.println("\nState 6 Epsilon Closure:");
		System.out.println(states[6].epsilonClosure());
		
		System.out.println("\nSample Converted DFA:");
		System.out.println(dfa.toString());
	}
}
