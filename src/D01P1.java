import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class D01P1 {
	public static int ans() {
		int ans = 0;
		
		File file = new File("inputs/d-01.txt");

		BufferedReader br;
		String st;
		Integer last = null;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// iterate through and direct comparison
				if (last == null) {
					last = Integer.parseInt(st);
				} else {
					int curr = Integer.parseInt(st);
					if (curr > last) {
						ans++;
					}
					last = curr;
				}
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
