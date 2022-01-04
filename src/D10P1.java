import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Stack;

public class D10P1 {
	public static char lineStatus(String line, HashMap<Character, Character> pairs) {
		Stack<Character> curr = new Stack<Character>();
		for (char c : line.toCharArray()) {
			if (pairs.containsKey(c)) {
				curr.push(c);
			} else {
				if (curr.isEmpty()) {
					return c;
				} else {
					if (c == pairs.get(curr.peek())) {
						curr.pop();
					} else {
						return c;
					}
				}
			}
		}
		if (curr.size() == 0) {
			return 'c';
		}
		return 'i';
	}
	
	public static int ans() {
		int ans = 0;
		
		HashMap<Character, Character> pairs = new HashMap<Character, Character>();
		pairs.put('(', ')');
		pairs.put('[', ']');
		pairs.put('{', '}');
		pairs.put('<', '>');
		
		HashMap<Character, Integer> points = new HashMap<Character, Integer>();
		points.put(')', 3);
		points.put(']', 57);
		points.put('}', 1197);
		points.put('>', 25137);
		points.put('c', 0);
		points.put('i', 0);
		File file = new File("inputs/d-10.txt");

		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				ans += points.get(lineStatus(st, pairs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ans;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
