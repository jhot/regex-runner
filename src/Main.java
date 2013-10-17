import java.util.LinkedHashSet;
import java.util.Set;


public class Main
{
	public static void main(String[] args)
	{
		Set<String> alphabet = new LinkedHashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		
		NFA nfa1 = NFAParser.parseStringToNfa("(a|b)*a");
		NFA nfa2 = NFAParser.parseStringToNfa("((a|b)(a|b))*");
		NFA nfa3 = NFAParser.parseStringToNfa("aaa*b*a*a");
		
		DFA dfa1 = Converter.convertNfaToDfa(nfa1, alphabet);
		DFA dfa2 = Converter.convertNfaToDfa(nfa2, alphabet);
		DFA dfa3 = Converter.convertNfaToDfa(nfa3, alphabet);
		
		String input11 = "aaaa";
		String input12 = "aba";
		String input13 = "bba";
		String input14 = "a";
		String input15 = "b";
		String input16 = "bbb";
		
		String input21 = "abbabb";
		String input22 = "e";
		String input23 = "aa";
		String input24 = "ab";
		String input25 = "aaaaa";
		String input26 = "bba";
		
		String input31 = "aabaa";
		String input32 = "aaa";
		String input33 = "aabba";
		String input34 = "abbaa";
		String input35 = "abbbbbbbbbbbbbbbbbbba";
		String input36 = "bbaa";
		
		System.out.println(String.format("%s accepts %s ? %s", "(a|b)*a",
				input11, dfa1.acceptsString(alphabet, input11)));
		System.out.println(String.format("%s accepts %s ? %s", "(a|b)*a",
				input12, dfa1.acceptsString(alphabet, input12)));
		System.out.println(String.format("%s accepts %s ? %s", "(a|b)*a",
				input13, dfa1.acceptsString(alphabet, input13)));
		System.out.println(String.format("%s accepts %s ? %s", "(a|b)*a",
				input14, dfa1.acceptsString(alphabet, input14)));
		System.out.println(String.format("%s accepts %s ? %s", "(a|b)*a",
				input15, dfa1.acceptsString(alphabet, input15)));
		System.out.println(String.format("%s accepts %s ? %s", "(a|b)*a",
				input16, dfa1.acceptsString(alphabet, input16)));
		
		System.out.println(String.format("\n%s accepts %s ? %s", "((a|b)(a|b))*",
				input21, dfa1.acceptsString(alphabet, input21)));
		// System.out.println(String.format("%s accepts %s ? %s", "((a|b)(a|b))*",
		// 		input22, dfa1.acceptsString(alphabet, input22)));
		System.out.println(String.format("%s accepts %s ? %s", "((a|b)(a|b))*",
				input23, dfa1.acceptsString(alphabet, input23)));
		System.out.println(String.format("%s accepts %s ? %s", "((a|b)(a|b))*",
				input24, dfa1.acceptsString(alphabet, input24)));
		System.out.println(String.format("%s accepts %s ? %s", "((a|b)(a|b))*",
				input25, dfa1.acceptsString(alphabet, input25)));
		System.out.println(String.format("%s accepts %s ? %s", "((a|b)(a|b))*",
				input26, dfa1.acceptsString(alphabet, input26)));
		
		System.out.println(String.format("\n%s accepts %s ? %s", "aaa*b*a*a",
				input31, dfa1.acceptsString(alphabet, input31)));
		System.out.println(String.format("%s accepts %s ? %s", "aaa*b*a*a",
				input32, dfa1.acceptsString(alphabet, input32)));
		System.out.println(String.format("%s accepts %s ? %s", "aaa*b*a*a",
				input33, dfa1.acceptsString(alphabet, input33)));
		System.out.println(String.format("%s accepts %s ? %s", "aaa*b*a*a",
				input34, dfa1.acceptsString(alphabet, input34)));
		System.out.println(String.format("%s accepts %s ? %s", "aaa*b*a*a",
				input35, dfa1.acceptsString(alphabet, input35)));
		System.out.println(String.format("%s accepts %s ? %s", "aaa*b*a*a",
				input36, dfa1.acceptsString(alphabet, input36)));
		
		NFA nfa = new NFA();
		State[] states = new State[11];
		for (int i = 0; i < states.length; i++)
		{
			states[i] = new State();
			nfa.addState(states[i]);
		}

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
