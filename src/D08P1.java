import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class D08P1 {
	public static int ans() {
		int ans = 0;
		
		File file = new File("inputs/d-08.txt");

		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				String[] inout = st.split(" \\| ");
				String[] out = inout[1].split("\\s+");
				for (String s : out) {
					if (s.length() != 5 && s.length() != 6) {
						ans++;
					}
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
