
public class Transition
{
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
}
