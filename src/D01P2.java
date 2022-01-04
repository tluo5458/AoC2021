import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class D01P2 {
	public static int ans() {
		int ans = 0;
		
		File file = new File("inputs/d-01.txt");

		BufferedReader br;
		String st;
		Integer third = null;
		Integer second = null;
		Integer last = null;
		try {
			br = new BufferedReader(new FileReader(file));
			// suffices to compare current with 3rd to last item
			// all this logic just to keep track and do everything
			// in one pass, def can make it cleaner with a counter
			// and doing mod 3 tho
			while ((st = br.readLine()) != null) {
				if (last == null) {
					last = Integer.parseInt(st);
				} else if (second == null) {
					second = last;
					last = Integer.parseInt(st);
				} else if (third == null) {
					third = second;
					second = last;
					last = Integer.parseInt(st);
				} else {
					int curr = Integer.parseInt(st);
					if (curr > third) {
						ans++;
					}
					third = second;
					second = last;
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
