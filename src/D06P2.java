import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class D06P2 {
	// this entire thing is identical to P1 but using longs instead
	
	// this is a pretty simple recurrence - there's almost certainly
	// a nice closed form to avoid doing a loop but i'm too lazy
	// and this is definitely efficient enough
	public static long[] simulate(long[] fish, int days) {
		long[] ret = new long[9];
		for (int i = 0; i < 9; i++) {
			ret[i] = fish[i];
		}
		for (int day = 0; day < days; day++) {
			long[] temp = new long[9];
			for (int i = 1; i < 9; i++) {
				temp[i - 1] += ret[i];
			}
			temp[6] += ret[0];
			temp[8] += ret[0];
			for (int i = 0; i < 9; i++) {
				ret[i] = temp[i];
			}
		}
		return ret;
	}
	
	public static long ans() {
		long ans = 0;
		
		File file = new File("inputs/d6.txt");

		// fish[i] = how many fish have counter i
		long[] fish = new long[9];
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// we do some parsing
				String[] indiv = st.split(",");
				for (String s : indiv) {
					fish[Integer.parseInt(s)]++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long[] grown = simulate(fish, 256);
		for (long i : grown) {
			ans += i;
		}
		return ans;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
