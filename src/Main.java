import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Main
{
	public static void main(String[] args)
	{
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
			String output = dfa.acceptsString(alphabet, inputString) ? "yes" : "no";
			System.out.println(output);
		}
		scanner.close();
	}
}
