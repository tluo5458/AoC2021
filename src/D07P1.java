import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class D07P1 {
	public static int ans() {
		int ans = 0;
		
		File file = new File("inputs/d-07.txt");

		// the key insight is that the optimal spot is just the
		// closest integer to the median
		// this is pretty clear if you think about what happens if 
		// you move the target spot by 1: it will move away from
		// certain crabs while moving towards others
		// you then attain an optimal value when an equal number of 
		// crabs on either side of the line
		int[] crabs = null;
		int num = 0;
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				String[] parse = st.split(",");
				num = parse.length;
				crabs = new int[num];
				for (int i = 0; i < num; i++) {
					crabs[i] = Integer.parseInt(parse[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// find median
		Arrays.sort(crabs);
		int optimal = 0;
		if (crabs.length % 2 == 0) {
			optimal = (crabs[crabs.length / 2] + crabs[crabs.length / 2 - 1]) / 2;
		} else {
			optimal = crabs[crabs.length / 2];
		}
		for (int i : crabs) {
			ans += Math.abs(i - optimal);
		}
		return ans;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
