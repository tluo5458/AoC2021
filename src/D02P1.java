import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class D02P1 {
	public static int ans() {
		File file = new File("inputs/d-02.txt");

		int x = 0;
		int y = 0;
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				String[] parse = st.split(" ");
				if (parse[0].charAt(0) == 'f') {
					x += Integer.parseInt(parse[1]);
				} else if (parse[0].charAt(0) == 'd') {
					y += Integer.parseInt(parse[1]);
				} else if (parse[0].charAt(0) == 'u') {
					y -= Integer.parseInt(parse[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return x * y;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
