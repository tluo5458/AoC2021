import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class D06P1 {
	// this is a pretty simple recurrence - there's almost certainly
	// a nice closed form to avoid doing a loop but i'm too lazy
	// and this is definitely efficient enough
	public static int[] simulate(int[] fish, int days) {
		int[] ret = new int[9];
		for (int i = 0; i < 9; i++) {
			ret[i] = fish[i];
		}
		for (int day = 0; day < days; day++) {
			int[] temp = new int[9];
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
	
	public static int ans() {
		int ans = 0;
		
		File file = new File("inputs/d-06.txt");

		// fish[i] = how many fish have counter i
		int[] fish = new int[9];
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
		int[] grown = simulate(fish, 80);
		for (int i : grown) {
			ans += i;
		}
		return ans;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
