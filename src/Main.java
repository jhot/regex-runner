public class Main
{
	public static void main(String[] args)
	{
		NFAParser.parseStringToNfa("(a|b)*a");
		NFAParser.parseStringToNfa("((a|b)(a|b))*");
		NFAParser.parseStringToNfa("aaa*b*a*a");
	}
}
