import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class D08P2 {
	// this solution is horrible verbose in code but logically sound
	// also assumes the characters are a to g but that's easy to fix
	// if needed (it was not needed)
	public static int solve(String[] in, String[] out) {
		String[] sortedIn = new String[10];
		for (int i = 0; i < 10; i++) {
			char[] temp = in[i].trim().toCharArray();
			Arrays.sort(temp);
			sortedIn[i] = String.valueOf(temp);
		}
		// this array stores the values we already know
		String[] found = new String[10];
		String[] fives = new String[3];
		String[] sixes = new String[3];
		char[] sixMiss = new char[3];
		
		// find 1, 4, 7, 8 and sort the other six
		int found5 = 0;
		int found6 = 0;
		for (String s : sortedIn) {
			if (s.length() == 2) {
				found[1] = s;
			} else if (s.length() == 4) {
				found[4] = s;
			} else if (s.length() == 3) {
				found[7] = s;
			} else if (s.length() == 7) {
				found[8] = s;
			} else if (s.length() == 5) {
				fives[found5] = s;
				found5++;
			} else if (s.length() == 6) {
				sixes[found6] = s;
				for (char check = 'a'; check <= 'g'; check++) {
					if (!s.contains(Character.toString(check))) {
						sixMiss[found6] = check;
					}
				}
				found6++;
			}
		}
		
		// find 0 by finding the length 6 missing a char in 4 but not in 1
		String in4no1 = "";
		for (char c : found[4].toCharArray()) {
			if (!found[1].contains(Character.toString(c))) {
				in4no1 += c;
			}
		}
		for (int i = 0; i < 3; i++) {
			if (in4no1.contains(Character.toString(sixMiss[i]))) {
				found[0] = sixes[i];
				sixes[i] = "";
				break;
			}
		}
		// find 6 by finding the length 6 missing a char in 1
		for (int i = 0; i < 3; i++) {
			if (found[1].contains(Character.toString(sixMiss[i]))) {
				found[6] = sixes[i];
				sixes[i] = "";
				break;
			}
		}
		// 9 is the last length 6
		for (String s : sixes) {
			if (s.length() == 6) {
				found[9] = s;
			}
		}
		
		// find 2 by finding the length 5 with all 3 chars missing from
		// the length 6s
		for (int i = 0; i < 3; i++) {
			boolean good = true;
			for (char c : sixMiss) {
				if (!fives[i].contains(Character.toString(c))) {
					good = false;
					break;
				}
			}
			if (good) {
				found[2] = fives[i];
				fives[i] = "";
				break;
			}
		}
		// find 3 by finding the length 5 with 4 chars in common with 2
		for (int i = 0; i < 3; i++) {
			if (fives[i].length() == 5) {
				int counter = 0;
				for (char c : fives[i].toCharArray()) {
					if (found[2].contains(Character.toString(c))) {
						counter++;
					}
				}
				if (counter == 4) {
					found[3] = fives[i];
					fives[i] = "";
					break;
				}
			}
		}
		// 5 is the last one
		for (String s : fives) {
			if (s.length() == 5) {
				found[5] = s;
				break;
			}
		}
		
		// now we interpret
		char[] ret = new char[4];
		for (int i = 0; i < 4; i++) {
			char[] temp = out[i].toCharArray();
			Arrays.sort(temp);
			String sorted = String.valueOf(temp);
			for (int j = 0; j < 10; j++) {
				if (sorted.equals(found[j])) {
					ret[i] = (char) (j + 48);
					break;
				}
			}
		}
		return Integer.parseInt(String.valueOf(ret));
	}
	
	public static int ans() {
		int ans = 0;
		
		File file = new File("inputs/d-08.txt");

		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				String[] inout = st.split(" \\| ");
				String[] in = inout[0].split(" ");
				String[] out = inout[1].split("\\s+");
				ans += solve(in, out);
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
