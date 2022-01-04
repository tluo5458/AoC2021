import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class D09P1 {
	public static int ans() {
		int ans = 0;
		
		File file = new File("inputs/d-09.txt");
		
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				lines.add(st);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// turn the list of strings into a 2d array
		int[][] grid = new int[lines.size()][lines.get(0).length()];
		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.get(0).length(); j++) {
				grid[i][j] = (int) (lines.get(i).charAt(j)) - 48;
			}
		}
		
		// find the low points
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				// try catches because i'm too lazy to check indices
				try {
					if (grid[i - 1][j] <= grid[i][j]) {
						continue;
					}
				} catch (IndexOutOfBoundsException e) {}
				try {
					if (grid[i + 1][j] <= grid[i][j]) {
						continue;
					}
				} catch (IndexOutOfBoundsException e) {}
				try {
					if (grid[i][j + 1] <= grid[i][j]) {
						continue;
					}
				} catch (IndexOutOfBoundsException e) {}
				try {
					if (grid[i][j - 1] <= grid[i][j]) {
						continue;
					}
				} catch (IndexOutOfBoundsException e) {}
				ans += grid[i][j] + 1;
			}
		}
		
		return ans;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
