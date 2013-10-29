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
	
	// any string with an even length
	@Test
	public void extraTestCase1_1()
	{
		assertTrue(acceptsString("((a|b)(a|b))*", "e"));
	}
	
	@Test
	public void extraTestCase1_2()
	{
		assertFalse(acceptsString("((a|b)(a|b))*", "b"));
	}
	
	@Test
	public void extraTestCase1_3()
	{
		assertTrue(acceptsString("((a|b)(a|b))*", "ba"));
	}
	
	@Test
	public void extraTestCase1_4()
	{
		assertFalse(acceptsString("((a|b)(a|b))*", "aaa"));
	}
	
	@Test
	public void extraTestCase1_5()
	{
		assertTrue(acceptsString("((a|b)(a|b))*", "bbab"));
	}
	
	// any string with an odd length
	@Test
	public void extraTestCase2_1()
	{
		assertFalse(acceptsString("((a|b)(a|b))*(a|b)", "e"));
	}
	
	@Test
	public void extraTestCase2_2()
	{
		assertTrue(acceptsString("((a|b)(a|b))*(a|b)", "b"));
	}
	
	@Test
	public void extraTestCase2_3()
	{
		assertFalse(acceptsString("((a|b)(a|b))*(a|b)", "ba"));
	}
	
	@Test
	public void extraTestCase2_4()
	{
		assertTrue(acceptsString("((a|b)(a|b))*(a|b)", "aaa"));
	}
	
	@Test
	public void extraTestCase2_5()
	{
		assertFalse(acceptsString("((a|b)(a|b))*(a|b)", "bbab"));
	}
	
	// any string with substring aba
	@Test
	public void extraTestCase3_1()
	{
		assertFalse(acceptsString("(a|b)*aba(a|b)*", "ab"));
	}
	
	@Test
	public void extraTestCase3_2()
	{
		assertFalse(acceptsString("(a|b)*aba(a|b)*", "ba"));
	}
	
	@Test
	public void extraTestCase3_3()
	{
		assertFalse(acceptsString("(a|b)*aba(a|b)*", "bba"));
	}
	
	@Test
	public void extraTestCase3_4()
	{
		assertTrue(acceptsString("(a|b)*aba(a|b)*", "aba"));
	}
	
	@Test
	public void extraTestCase3_5()
	{
		assertFalse(acceptsString("(a|b)*aba(a|b)*", "aaaaaaaab"));
	}
	
	@Test
	public void extraTestCase3_6()
	{
		assertTrue(acceptsString("(a|b)*aba(a|b)*", "aaaaaaaaba"));
	}
	
	// any string without substring ba
	@Test
	public void extraTestCase4_1()
	{
		assertTrue(acceptsString("a*b*", "e"));
	}
	
	@Test
	public void extraTestCase4_2()
	{
		assertFalse(acceptsString("a*b*", "ba"));
	}
	
	@Test
	public void extraTestCase4_3()
	{
		assertTrue(acceptsString("a*b*", "aaaabbbb"));
	}
	
	@Test
	public void extraTestCase4_4()
	{
		assertTrue(acceptsString("a*b*", "aaaab"));
	}
	
	@Test
	public void extraTestCase4_5()
	{
		assertFalse(acceptsString("a*b*", "bbbbbbba"));
	}
	
	// any string with an even number of b's
	@Test
	public void extraTestCase5_1()
	{
		assertTrue(acceptsString("a*(ba*ba*)*", "bb"));
	}
	
	@Test
	public void extraTestCase5_2()
	{
		assertFalse(acceptsString("a*(ba*ba*)*", "bbb"));
	}
	
	@Test
	public void extraTestCase5_3()
	{
		assertTrue(acceptsString("a*(ba*ba*)*", "bbabb"));
	}
	
	@Test
	public void extraTestCase5_4()
	{
		assertFalse(acceptsString("a*(ba*ba*)*", "bbab"));
	}
	
	@Test
	public void extraTestCase5_5()
	{
		assertTrue(acceptsString("a*(ba*ba*)*", "aaaaabaaabaaaabbaaabbaabab"));
	}
	
	// any string without substring bb
	@Test
	public void extraTestCase6_1()
	{
		assertTrue(acceptsString("(a|ba)*(e|b)", "e"));
	}
	
	@Test
	public void extraTestCase6_2()
	{
		assertTrue(acceptsString("(a|ba)*(e|b)", "aaaba"));
	}
	
	@Test
	public void extraTestCase6_3()
	{
		assertFalse(acceptsString("(a|ba)*(e|b)", "aaabba"));
	}
	
	@Test
	public void extraTestCase6_4()
	{
		assertTrue(acceptsString("(a|ba)*(e|b)", "aabaab"));
	}
	
	@Test
	public void extraTestCase6_5()
	{
		assertFalse(acceptsString("(a|ba)*(e|b)", "aaabaabaabbaab"));
	}
	
	// difficult to parse string
	@Test
	public void extraTestCase7_1()
	{
		assertTrue(acceptsString("(bb|bbbb|bbbbbb)*(((((a)))))*|(e)", "e"));
	}
	
	@Test
	public void extraTestCase7_2()
	{
		assertFalse(acceptsString("(bb|bbbb|bbbbbb)*(((((a)))))*|(e)", "ba"));
	}
	
	@Test
	public void extraTestCase7_3()
	{
		assertFalse(acceptsString("(bb|bbbb|bbbbbb)*(((((a)))))*|(e)", "bbb"));
	}
	
	@Test
	public void extraTestCase7_4()
	{
		assertTrue(acceptsString("(bb|bbbb|bbbbbb)*(((((a)))))*|(e)", "bbbbbbbba"));
	}
	
	private boolean acceptsString(String regex, String inputString)
	{
		NFA nfa = NFAParser.parseStringToNfa(regex);
		DFA dfa = Converter.convertNfaToDfa(nfa, alphabet);
		return dfa.acceptsString(alphabet, inputString);
	}
}
