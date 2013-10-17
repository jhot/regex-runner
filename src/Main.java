
public class Main
{
	public static void main(String[] args)
	{
		NFAParser.parseStringToNfa("(a|b)*a");
		NFAParser.parseStringToNfa("((a|b)(a|b))*");
		NFAParser.parseStringToNfa("aaa*b*a*a");
		
		State state1 = new State();
		State state2 = new State();
		State state3 = new State();
		State state4 = new State();
		State state5 = new State();
		State state6 = new State();

		state1.addTransition(new Transition(Transition.EPSILON, state2));
		state1.addTransition(new Transition(Transition.EPSILON, state4));
		state2.addTransition(new Transition("a", state3));
		state4.addTransition(new Transition("b", state5));
		state3.addTransition(new Transition(Transition.EPSILON, state6));
		state5.addTransition(new Transition(Transition.EPSILON, state6));
		state6.addTransition(new Transition(Transition.EPSILON, state1));
		
		System.out.println("State 1 Epsilon Closure:");
		System.out.println(state1.epsilonClosure());
		
		System.out.println("State 2 Epsilon Closure:");
		System.out.println(state2.epsilonClosure());
		
		System.out.println("State 3 Epsilon Closure:");
		System.out.println(state3.epsilonClosure());
		
		System.out.println("State 4 Epsilon Closure:");
		System.out.println(state4.epsilonClosure());
		
		System.out.println("State 5 Epsilon Closure:");
		System.out.println(state5.epsilonClosure());
		
		System.out.println("State 6 Epsilon Closure:");
		System.out.println(state6.epsilonClosure());
	}
}
