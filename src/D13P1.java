import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class D13P1 {
	public static int ans() {
		int ans = 0;
		
		File file = new File("inputs/d-13.txt");

		ArrayList<String> lines = new ArrayList<String>();
		ArrayList<String> folds = new ArrayList<String>();
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// we do a bit of parsing
				if (st.length() > 0 && Character.isDigit(st.charAt(0))) {
					lines.add(st);	
				}
				if (st.length() > 0 && st.charAt(0) == 'f') {
					folds.add(st.split(" ")[2]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// turn the lines into a list of coords
		int[][] coords = new int[lines.size()][2];
		for (int i = 0; i < lines.size(); i++) {
			String[] parse = lines.get(i).split(",");
			coords[i][0] = Integer.parseInt(parse[0]);
			coords[i][1] = Integer.parseInt(parse[1]);
		}
		// find max of each coord
		int maxX = 0;
		int maxY = 0;
		for (int[] i : coords) {
			maxX = Math.max(maxX, i[0]);
			maxY = Math.max(maxY, i[1]);
		}
		// take the coords and turn it into a grid
		boolean[][] grid = new boolean[maxY + 1][maxX + 1];
		for (int[] i : coords) {
			grid[i[1]][i[0]] = true;
		}
		// now fold (once)
		String[] fold = folds.get(0).split("=");
		int along = Integer.parseInt(fold[1]);
		if (fold[0].equals("x")) {
			boolean[][] newGrid = new boolean[grid.length][along];
			for (int i = 0; i < newGrid.length; i++) {
				for (int j = 0; j < newGrid[0].length; j++) {
					newGrid[i][j] = grid[i][j];
					if (2 * along - j < grid[0].length) {
						newGrid[i][j] = newGrid[i][j] || grid[i][2 * along - j];						
					}
				}
			}
			grid = newGrid;
		} else {
			boolean[][] newGrid = new boolean[along][grid[0].length];
			for (int i = 0; i < newGrid.length; i++) {
				for (int j = 0; j < newGrid[0].length; j++) {
					newGrid[i][j] = grid[i][j];
					if (2 * along - i < grid.length) {
						newGrid[i][j] = newGrid[i][j] || grid[2 * along - i][j];						
					}
				}
			}
			grid = newGrid;
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
