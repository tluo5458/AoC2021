import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class D22P1 {
	// i am terrified of what part 2 might be
	private static class Step {
		int[] x;
		int[] y;
		int[] z;
		boolean set;
		
		Step(String line) {
			String[] onOff = line.split(" ");
			set = onOff[0].charAt(1) == 'n';
			String[] coords = line.split(",");
			String[] xParse = coords[0].split("=")[1].split("\\.\\.");
			String[] yParse = coords[1].split("=")[1].split("\\.\\.");
			String[] zParse = coords[2].split("=")[1].split("\\.\\.");
			x = new int[] {Integer.parseInt(xParse[0]), Integer.parseInt(xParse[1])};
			y = new int[] {Integer.parseInt(yParse[0]), Integer.parseInt(yParse[1])};
			z = new int[] {Integer.parseInt(zParse[0]), Integer.parseInt(zParse[1])}; 
		}
	}
	
	public static int ans() {
		int ans = 0;
		
		File file = new File("inputs/d-22.txt");

		BufferedReader br;
		String st;
		ArrayList<Step> steps = new ArrayList<Step>();
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// we do a bit of parsing
				steps.add(new Step(st));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean[][][] reactor = new boolean[101][101][101];
		for (Step s : steps) {
			for (int x = Math.max(s.x[0], -50); x <= Math.min(s.x[1], 50); x++)  {
				for (int y = Math.max(s.y[0], -50); y <= Math.min(s.y[1], 50); y++)  {
					for (int z = Math.max(s.z[0], -50); z <= Math.min(s.z[1], 50); z++)  {
						reactor[x + 50][y + 50][z + 50] = s.set;
					}
				}
			}
		}
		for (int i = 0; i < 101; i++) {
			for (int j = 0; j < 101; j++) {
				for (int k = 0; k < 101; k++) {
					if (reactor[i][j][k]) {
						ans++;
					}
				}
			}
		}
		return ans;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
