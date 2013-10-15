

public class NFAParser
{
	private static final boolean DEBUG = false;
	
	public static NFA parseStringToNfa(String inputString)
	{
		if (DEBUG) System.out.println("\n********** Parse String to NFA **********");
		if (DEBUG) System.out.println("NFAParser->begin parsing string \"" + inputString + "\"");
		inputString = inputString.toLowerCase().trim();
		inputString = addExtraParenthesesToExpression(inputString);
		
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
				}
				case "(":
				{
					// cut everything from this "(" to its associated closing ")"
					String remainingInput = inputString.substring(index + 1);
					String innerExpression = getFirstInnerExpressionSubstring(remainingInput);
					
					if (DEBUG) System.out.println("NFAParser->inner expression is \"" + innerExpression + "\"");
					
					// parse everything between ( ... ) to a new NFA
					newNfa = parseStringToNfa(innerExpression);
				
					// innerExpression does not include the ( ... ), so add 1 (since
					// we are currently on the "(" character.
					charactersToSkip = innerExpression.length() + 1;
				}
				case "|":
				{
					// cut everything from the "|(" to its associated closing ")"
					String remainingInput = inputString.substring(index + 2);
					String innerExpression = getFirstInnerExpressionSubstring(remainingInput);
					
					if (DEBUG) System.out.println("NFAParser->inner expression is \"" + innerExpression + "\"");
					
					// parse everything between |( ... ) to a new NFA
					newNfa = parseStringToNfa(innerExpression);
					
					// innerExpression does not include the ( ... ), so add 2
					charactersToSkip = innerExpression.length() + 2;
				}
				case "*":
				{
					// nothing to do... * is handled by looking at the next symbol.
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
					if (DEBUG) System.out.println("NFAParser->set result to new NFA (concatenate to e)");
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
		return result;
	}
	
	/**
	 * Add extra parenthesis to expression to ensure that there is a '(' immediately
	 * following each union operator. For example, running this method with an input
	 * of "(a|aa*(b|b))" will return '(a|(aa*(b|(b))))
	 * 
	 * @param expression
	 * @return
	 */
	private static String addExtraParenthesesToExpression(String expression)
	{
		int indexOfFirstCorrection = -1;
		for (int i = 0; i < expression.length() - 1; i++)
		{
			char symbol = expression.charAt(i);
			char nextSymbol = expression.charAt(i + 1);
			if (symbol == '|' && nextSymbol != '(')
			{
				indexOfFirstCorrection = i;
				break;
			}
		}
		if (indexOfFirstCorrection != -1)
		{
			// find the length from the symbol after the '|' to the first ')' that is NOT
			// a complete subexpression itself. OR find the length from the symbol after the '|' to
			// the end of the string, whichever comes first.
			String remainingInput = expression.substring(indexOfFirstCorrection + 1);
			int unionExpressionLength = getUnionExpressionLength(remainingInput);
			
			int j = indexOfFirstCorrection + 1;
			int k = j + unionExpressionLength;
		
			String corrected = expression.substring(0, k) + ")" + expression.substring(k);
			corrected = corrected.substring(0, j) + "(" + corrected.substring(j);
			return addExtraParenthesesToExpression(corrected);
		}
		return expression;
	}
	
	private static int getUnionExpressionLength(String expression)
	{
		int index = 0;
		int count = 1;
		while (count != 0 && (index < expression.length()))
		{
			char c = expression.charAt(index);
			if (c == ')')
			{
				count--;
			}
			if (c == '(')
			{
				count++;
			}
			index++;
		}
		// two possible cases: first, the string ends in a regular character,
		// i.e. expression is "bb" from the original input string "a|bb".
		// second, the string ends in a parenthesis. i.e. "bb)" from the original input
		// string "(a|bb)". in the second case, one extra character needs to be cut off
		if (expression.charAt(expression.length() - 1) == ')')
		{
			index--;
		}
		return index;
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
			else if (c == '(');
			{
				count++;
			}
		}
		return expression.substring(1, index);
	}
}
