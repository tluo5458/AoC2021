import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Stack;

public class D09P2 {
	// effectively a dfs to traverse all points in the basin
	public static int basinSize(int[][] grid, int x, int y) {
		HashSet<Integer> visited = new HashSet<Integer>();
		Stack<Integer> dfsStack = new Stack<Integer>();
		dfsStack.add(x * grid[0].length + y);
		while (!dfsStack.empty()) {
			int coord = dfsStack.pop();
			if (visited.contains(coord)) {
				continue;
			}
			visited.add(coord);
			int i = coord / grid[0].length;
			int j = coord % grid[0].length;
			if (i > 0) {
				if (grid[i - 1][j] >= grid[i][j] && grid[i - 1][j] < 9) {
					dfsStack.add((i - 1) * grid[0].length + j);
				}
			}
			if (i < grid.length - 1) {
				if (grid[i + 1][j] >= grid[i][j] && grid[i + 1][j] < 9) {
					dfsStack.add((i + 1) * grid[0].length + j);
				}
			}
			if (j > 0) {
				if (grid[i][j - 1] >= grid[i][j] && grid[i][j - 1] < 9) {
					dfsStack.add(i * grid[0].length + j - 1);
				}
			}
			if (j < grid[0].length - 1) {
				if (grid[i][j + 1] >= grid[i][j] && grid[i][j + 1] < 9) {
					dfsStack.add(i * grid[0].length + j + 1);
				}
			}
		}
		return visited.size();
	}
	
	public static int ans() {
		int ans = 1;
		
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
		
		ArrayList<Integer> basinSizes = new ArrayList<Integer>();
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
				basinSizes.add(basinSize(grid, i, j));
			}
		}
		
		Collections.sort(basinSizes);
		for (int i = basinSizes.size() - 1; i >= basinSizes.size() - 3; i--) {
			ans *= basinSizes.get(i);
		}
		return ans;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
