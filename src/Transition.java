/**
 * Data model class which holds a symbol and the next state
 * for that symbol.
 */
public class Transition
{
	public static final String EPSILON = "e";
	
	private String symbol;
	private State nextState;
	
	public Transition(String symbol, State nextState)
	{
		this.symbol = symbol;
		this.nextState = nextState;
	}
	
	public String getSymbol()
	{
		return symbol;
	}
	
	public State getNextState()
	{
		return nextState;
	}
	
	@Override
	public String toString()
	{
		return symbol + "->" + nextState.getId();
	}
}
