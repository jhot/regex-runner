import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class RegexRunnerTestCase
{
	private Set<String> alphabet;
	
	@Before
	public void setUp()
	{
		alphabet = new LinkedHashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
	}
	
	@Test
	public void officialTestCase1_1()
	{
		assertTrue(acceptsString("(a|b)*a", "aaaa"));
	}
	
	@Test
	public void officialTestCase1_2()
	{
		assertTrue(acceptsString("(a|b)*a", "aba"));
	}
	
	@Test
	public void officialTestCase1_3()
	{
		assertTrue(acceptsString("(a|b)*a", "bba"));
	}
	
	@Test
	public void officialTestCase1_4()
	{
		assertTrue(acceptsString("(a|b)*a", "a"));
	}
	
	@Test
	public void officialTestCase1_5()
	{
		assertFalse(acceptsString("(a|b)*a", "b"));
	}
	
	@Test
	public void officialTestCase1_6()
	{
		assertFalse(acceptsString("(a|b)*a", "bbb"));
	}
	
	@Test
	public void officialTestCase2_1()
	{
		assertTrue(acceptsString("((a|b)(a|b))*", "abbabb"));
	}
	
	@Test
	public void officialTestCase2_2()
	{
		assertTrue(acceptsString("((a|b)(a|b))*", "e"));
	}
	
	@Test
	public void officialTestCase2_3()
	{
		assertTrue(acceptsString("((a|b)(a|b))*", "aa"));
	}
	
	@Test
	public void officialTestCase2_4()
	{
		assertTrue(acceptsString("((a|b)(a|b))*", "ab"));
	}
	
	@Test
	public void officialTestCase2_5()
	{
		assertFalse(acceptsString("((a|b)(a|b))*", "aaaaa"));
	}
	
	@Test
	public void officialTestCase2_6()
	{
		assertFalse(acceptsString("((a|b)(a|b))*", "bba"));
	}
	
	@Test
	public void officialTestCase3_1()
	{
		assertTrue(acceptsString("aaa*b*a*a", "aabaa"));
	}
	
	@Test
	public void officialTestCase3_2()
	{
		assertTrue(acceptsString("aaa*b*a*a", "aaa"));
	}
	
	@Test
	public void officialTestCase3_3()
	{
		assertTrue(acceptsString("aaa*b*a*a", "aabba"));
	}
	
	@Test
	public void officialTestCase3_4()
	{
		assertFalse(acceptsString("aaa*b*a*a", "abbaa"));
	}
	
	@Test
	public void officialTestCase3_5()
	{
		assertFalse(acceptsString("aaa*b*a*a", "abbbbbbbbbbbbbbbbbbba"));
	}
	
	@Test
	public void officialTestCase3_6()
	{
		assertFalse(acceptsString("aaa*b*a*a", "bbaa"));
	}
	
	private boolean acceptsString(String regex, String inputString)
	{
		NFA nfa = NFAParser.parseStringToNfa(regex);
		DFA dfa = Converter.convertNfaToDfa(nfa, alphabet);
		return dfa.acceptsString(alphabet, inputString);
	}
}
