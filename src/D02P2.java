import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class D02P2 {
	public static long ans() {
		File file = new File("inputs/d-02.txt");

		long x = 0;
		long y = 0;
		int aim = 0;
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				String[] parse = st.split(" ");
				if (parse[0].charAt(0) == 'f') {
					x += Integer.parseInt(parse[1]);
					y += aim * Integer.parseInt(parse[1]);
				} else if (parse[0].charAt(0) == 'd') {
					aim += Integer.parseInt(parse[1]);
				} else if (parse[0].charAt(0) == 'u') {
					aim -= Integer.parseInt(parse[1]);
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
