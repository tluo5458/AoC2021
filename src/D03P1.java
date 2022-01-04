import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class D03P1 {
	public static int ans() {
		File file = new File("inputs/d-03.txt");
		
		// array keeping track of difference between # of 1's encountered
		// so far and # of 0's encountered
		int[] diffs = null;
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// set to appropriate length
				if (diffs == null) {
					diffs = new int[st.length()];
				}
				for (int i = 0; i < st.length(); i++) {
					if (st.charAt(i) == '0') {
						diffs[i]--;
					} else if (st.charAt(i) == '1') {
						diffs[i]++;
					} else {
						// just in case
						System.out.println("Uh oh");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// turn diffs into two numbers
		// iterate through diffs to add the appropriate 1's to gamma/eps
		int gamma = 0;
		int epsilon = 0;
		int curr = 1;
		for (int i = diffs.length - 1; i >= 0; i--) {
			if (diffs[i] > 0) {
				gamma += curr;
			} else {
				epsilon += curr;
			}
			curr *= 2;
		}
		return gamma * epsilon;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
