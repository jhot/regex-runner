/**
 * Converts regular expression input strings into equivalent NFA's.
 */
public class NFAParser
{
	private static final boolean DEBUG = false;
	
	public static NFA parseStringToNfa(String inputString)
	{
		if (DEBUG) System.out.println("\n********** Parse to NFA: " + inputString + " **********");
		inputString = inputString.toLowerCase().trim();
		
		if (DEBUG) System.out.println("NFAParser->added parenthesis to create string \"" + inputString + "\"");
		
		int index = 0;
		NFA result = null;
		
		while (index < inputString.length())
		{
			String symbol = inputString.charAt(index) + "";
			if (DEBUG) System.out.println("NFAParser->read symbol " + symbol);
			
			int charactersToSkip = 0;
			NFA newNfa = null;
			
			// build a new NFA for the symbol. if an 'a, b, or e' is read, build an Nfa for the
			// single symbol. if an '(' or '|' is read, recursively call this function to build an Nfa for the
			// sub expression. if a '*' is read, do nothing.
			switch (symbol)
			{
				case "a":
				case "b":
				case "e":
				{
					if (DEBUG) System.out.println("NFAParser->createNfaForSymbol->" + symbol);
					newNfa = NFA.createNfaForSymbol(symbol);
					
					break;
				}
				case "(":
				{
					// cut everything from this "(" to its associated closing ")"
					String remainingInput = inputString.substring(index);
					String innerExpression = getFirstInnerExpressionSubstring(remainingInput);
					
					if (DEBUG) System.out.println("NFAParser->inner expression is \"" + innerExpression + "\"");
					
					// parse everything between ( ... ) to a new NFA
					newNfa = parseStringToNfa(innerExpression);
					if (DEBUG) System.out.println();
				
					// innerExpression does not include the ( ... ), so add 1 (since
					// we are currently on the "(" character.
					charactersToSkip = innerExpression.length() + 1;
					
					break;
				}
				case "|":
				{
					// cut everything from the "|(" to the end since | is the lowest priority
					// in order of operations
					String remainingInput = inputString.substring(index + 1);
					
					if (DEBUG) System.out.println("NFAParser->expression to union to current NFA is \"" + remainingInput + "\"");
					
					// parse everything between |( ... ) to a new NFA
					newNfa = parseStringToNfa(remainingInput);
					if (DEBUG) System.out.println();
					
					charactersToSkip = remainingInput.length();
					
					break;
				}
				case "*":
				{
					// nothing to do... * is handled by looking at the next symbol.
					break;
				}
			}
			// find the next symbol, i.e. pretend next symbol is the symbol
			// after the ( ... ) or |( ... ) if a "(" or "|" has been read
			int nextSymbolIndex = index + 1 + charactersToSkip;
			String nextSymbol = (nextSymbolIndex < inputString.length()) ? inputString.charAt(nextSymbolIndex) + "" : "";
			
			if (DEBUG) System.out.println("NFAParser->next symbol is " + nextSymbol);
			
			// combine the Nfa with the current result. check ahead for the * symbol,
			// and wrap the current Nfa in a kleene closure if the *next* input symbol
			// is a '*'. otherwise, concatenate / union appropriately
			if (newNfa != null)
			{
				// apply the kleene closure if necessary
				if (nextSymbol.equals("*"))
				{
					if (DEBUG) System.out.println("NFAParser->next symbol is * - wrapping NFA in Kleene closure");
					newNfa.wrapInKleeneClosure();
				}
				
				// if there is no result yet, set result to newNfa
				if (result == null)
				{
					if (DEBUG) System.out.println("NFAParser->set result to new NFA");
					result = newNfa;
				}
				// if we are on a union symbol, union newNfa with the result
				else if (symbol.equals("|"))
				{
					if (DEBUG) System.out.println("NFAParser->union new NFA with previous NFA");
					result.union(newNfa);
				}
				else
				{
					if (DEBUG) System.out.println("NFAParser->concatenate new NFA to previous NFA");
					result.concatenate(newNfa);
				}
			}
			index += charactersToSkip;
			index++;
		}
		if (DEBUG) System.out.println("--------------------------------------------------");
		if (DEBUG) System.out.println("Finished parsing \"" + inputString + "\" to:");
		if (DEBUG) System.out.println(result);
		if (DEBUG) System.out.println("--------------------------------------------------");
		
		return result;
	}
	
	/**
	 * Finds the first complete inner expression by counting parenthesis. For example,
	 * running this method with the input "((a*aa)(a|b))(aa)" will return "(a*aa)(a|b)"
	 *
	 * @param expression the input string. Must begin with "("
	 * @return the substring containing everything between the "(" at index 0 and its associated
	 * closing symbol ")".
	 */
	private static String getFirstInnerExpressionSubstring(String expression)
	{
		if (expression.charAt(0) != '(')
		{
			throw new IllegalArgumentException("Expression must begin with \"(\"");
		}
		int index = 1;
		int count = 1;
		while (count != 0)
		{
			char c = expression.charAt(index);
			if (c == ')')
			{
				count--;
			}
			else if (c == '(')
			{
				count++;
			}
			index++;
		}
		return expression.substring(1, index - 1);
	}
}
