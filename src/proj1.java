import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Main class which reads input from stdin, builds an equivalent DFA for the given
 * regular expression, tests the given input strings against the DFA, and outputs the
 * results to stdout
 */
public class proj1
{
	public static void main(String[] args)
	{
		// alphabet is not user input
		Set<String> alphabet = new LinkedHashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		
		Scanner scanner = new Scanner(System.in);
		String regex = scanner.nextLine().trim().toLowerCase();
		
		NFA nfa = NFAParser.parseStringToNfa(regex);
		DFA dfa = Converter.convertNfaToDfa(nfa, alphabet);
		
		while (scanner.hasNextLine())
		{
			String inputString = scanner.nextLine().trim().toLowerCase();
			if (inputString.length() == 0 && !scanner.hasNextLine())
			{
				// the last line in the file is blank - don't print yes / no
				break;
			}
			String output = dfa.acceptsString(alphabet, inputString) ? "yes" : "no";
			System.out.println(output);
		}
		scanner.close();
	}
}
