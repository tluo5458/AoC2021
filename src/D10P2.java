import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class D10P2 {
	public static long lineStatus(String line, HashMap<Character, Character> pairs, HashMap<Character, Integer> points) {
		Stack<Character> curr = new Stack<Character>();
		for (char c : line.toCharArray()) {
			if (pairs.containsKey(c)) {
				curr.push(c);
			} else {
				if (curr.isEmpty()) {
					return 0;
				} else {
					if (c == pairs.get(curr.peek())) {
						curr.pop();
					} else {
						return 0;
					}
				}
			}
		}
		if (curr.size() == 0) {
			return 0;
		}
		long ret = 0;
		while (!curr.empty()) {
			ret *= 5;
			ret += points.get(pairs.get(curr.pop()));
		}
		return ret;
	}
	
	public static long ans() {
		HashMap<Character, Character> pairs = new HashMap<Character, Character>();
		pairs.put('(', ')');
		pairs.put('[', ']');
		pairs.put('{', '}');
		pairs.put('<', '>');
		
		HashMap<Character, Integer> points = new HashMap<Character, Integer>();
		points.put(')', 1);
		points.put(']', 2);
		points.put('}', 3);
		points.put('>', 4);
		File file = new File("inputs/d-10.txt");

		ArrayList<Long> scores = new ArrayList<Long>();
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				long score = lineStatus(st, pairs, points);
				if (score > 0) {
					scores.add(score);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(scores);
		return scores.get(scores.size() / 2);
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
