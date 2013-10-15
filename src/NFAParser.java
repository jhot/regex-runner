
public class NFAParser
{
	public static NFA parseStringToNfa(String inputString)
	{
		inputString = inputString.toLowerCase().trim();
		inputString = addExtraParenthesesToExpression(inputString);
		
		int index = 0;
		NFA result = null;
		
		while (index < inputString.length())
		{
			String symbol = inputString.charAt(index) + "";
			String nextSymbol = (index + 1) < inputString.length() ? inputString.charAt(index + 1) + "" : "";
			
			int charactersToSkip = 0;
			
			// build a new NFA for the symbol. if an 'a, b, or e' is read, build an Nfa for the
			// single symbol. if an '(' or '|' is read, recursively call this function to build an Nfa for the
			// sub expression. if a '*' is read, do nothing.
			NFA newNfa = null;
			switch (symbol)
			{
				case "a":
				case "b":
				case "e":
				{
					newNfa = NFA.createNfaForSymbol(symbol);
				}
				case "(":
				{
					// cut everything from this "(" to its associated closing ")"
					String remainingInput = inputString.substring(index + 1);
					String innerExpression = getFirstInnerExpressionSubstring(remainingInput);
					
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
			// combine the Nfa with the current result. check ahead for the * symbol,
			// and wrap the current Nfa in a kleene closure if the *next* input symbol
			// is a '*'. otherwise, concatenate / union appropriately
			if (newNfa != null)
			{
				// apply the kleene closure if necessary
				if (nextSymbol.equals("*"))
				{
					newNfa.wrapInKleeneClosure();
				}
				
				// if there is no result yet, set result to newNfa
				if (result == null)
				{
					result = newNfa;
				}
				// if we are on a union symbol, union newNfa with the result
				else if (symbol.equals("|"))
				{
					result.union(newNfa);
				}
				else
				{
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
