import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class D14P2 {
	// quite literally identical to p1 but using longs instead
	public static long ans() {
		File file = new File("inputs/d-14.txt");

		String template = "";
		HashMap<String, String> rules = new HashMap<String, String>();
		HashMap<String, Long> counts = new HashMap<String, Long>();
		
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// we do a bit of parsing
				if (st.trim().length() > 0) {
					String[] parse = st.split(" -> ");
					if (parse.length > 1) {
						rules.put(parse[0], parse[1]);
						counts.put(parse[0], (long) 0);
					} else {
						template = st;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// the idea is to only keep track of the number of each pair of
		// characters, which then acts nicely with the iterations
		// space = O(c^2), where c is the size of the alphabet
		// constant in terms of number of turns which is good
		
		// turn the template string into counts
		for (int i = 1; i < template.length(); i++) {
			String duple = template.substring(i - 1, i + 1);
			counts.put(duple, counts.get(duple) + 1);
		}
		
		// now iterate
		for (int step = 0; step < 40; step++) {
			HashMap<String, Long> newCounts = new HashMap<String, Long>();
			for (String s : counts.keySet()) {
				newCounts.put(s, (long) 0);
			}
			// do the rules
			for (String s : rules.keySet()) {
				String first = s.substring(0, 1) + rules.get(s);
				String second = rules.get(s) + s.substring(1, 2);
				newCounts.put(first, newCounts.get(first) + counts.get(s));
				newCounts.put(second, newCounts.get(second) + counts.get(s));
			}
			counts = newCounts;
		}
		
		// finally count the number of each char
		HashMap<Character, Long> chars = new HashMap<Character, Long>();
		for (String s : rules.keySet()) {
			chars.put(s.charAt(0), (long) 0);
		}
		for (String s : counts.keySet()) {
			chars.put(s.charAt(0), chars.get(s.charAt(0)) + counts.get(s));
			chars.put(s.charAt(1), chars.get(s.charAt(1)) + counts.get(s));
		}
		// add one of the beginning and ending character
		chars.put(template.charAt(0), chars.get(template.charAt(0)) + 1);
		chars.put(template.charAt(template.length() - 1), chars.get(template.charAt(template.length() - 1)) + 1);
		// and divide everything by 2
		for (Character c : chars.keySet()) {
			chars.put(c, chars.get(c) / 2);
		}
		
		// finally find the highest and lowest character counts
		long max = 0;
		long min = Long.MAX_VALUE;
		for (Character c : chars.keySet()) {
			min = Math.min(min, chars.get(c));
			max = Math.max(max, chars.get(c));
		}
		return max - min;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
