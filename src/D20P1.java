import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class D20P1 {
	// extra boolean parameter to account for if rule[0] is #
	public static boolean[][] enhance(boolean[][] grid, String rule, boolean outTrue) {
		// pad the grid with 2 layers of false
		boolean[][] padded = new boolean[grid.length + 4][grid[0].length + 4];
		if (outTrue) {
			for (int i = 0; i < padded.length; i++) {
				for (int j = 0; j < padded[0].length; j++) {
					padded[i][j] = true;
				}
			}
		}
		for (int i = 2; i <= grid.length + 1; i++) {
			for (int j = 2; j <= grid[0].length + 1; j++) {
				padded[i][j] = grid[i - 2][j - 2];
			}
		}
		boolean[][] ret = new boolean[grid.length + 2][grid[0].length + 2];
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret[0].length; j++) {
				int total = 0;
				int curr = 256;
				for (int x = -1; x <= 1; x++) {
					for (int y = -1; y <= 1; y++) {
						if (padded[i + 1 + x][j + 1 + y]) {
							total += curr;
						}
						curr /= 2;
					}
				}
				ret[i][j] = rule.charAt(total) == '#';
			}
		}
		return ret;
	}
	
	public static int ans() {
		int ans = 0;
		
		File file = new File("inputs/d-20.txt");

		boolean[][] grid;
		ArrayList<String> lines = new ArrayList<String>();
		String rule = "";
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// we do a bit of parsing
				if (rule.length() == 0) {
					rule = st;
				} else if (st.length() > 0) {
					lines.add(st);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		grid = new boolean[lines.size()][lines.get(0).length()];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j] = lines.get(i).charAt(j) == '#';
			}
		}
		for (int i = 0; i < 2; i++) {
			grid = enhance(grid, rule, i % 2 == 1);
		}
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j]) {
					ans++;
				}
			}
		}
		return ans;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
