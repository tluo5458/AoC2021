import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class D15P2 {
	// identical to p1 except a larger grid
	private static class GridSpot implements Comparable<GridSpot> {
		int x;
		int y;
		int dist;

		GridSpot(int i, int j, int d) {
			x = i;
			y = j;
			dist = d;
		}
		
		@Override
		public int compareTo(GridSpot o) {
			return dist - o.dist;
		}
	}
	
	public static int ans() {
		File file = new File("inputs/d-15.txt");

		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// we do a bit of parsing
				lines.add(st);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// standard djikstra's
		int[][] grid = new int[lines.size() * 5][lines.get(0).length() * 5];
		int[][] dist = new int[lines.size() * 5][lines.get(0).length() * 5];

		// create the grid
		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.get(0).length(); j++) {
				for (int a = 0; a < 5; a++) {
					for (int b = 0; b < 5; b++) {
						int temp = Integer.parseInt(lines.get(i).substring(j, j + 1)) + a + b;
						if (temp > 9) {
							temp -= 9;
						}
						grid[i + a * lines.size()][j + b * lines.get(0).length()] = temp;
					}
				}
			}
		}
		
		// initialize things
		HashMap<Integer, GridSpot> spots = new HashMap<Integer, GridSpot>();
		PriorityQueue<GridSpot> pq = new PriorityQueue<GridSpot>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				dist[i][j] = Integer.MAX_VALUE;
				if (i == 0 && j == 0) {
					dist[i][j] = 0;
				}
				GridSpot curr = new GridSpot(i, j, Integer.MAX_VALUE);
				spots.put(i * grid.length + j, curr);
				pq.add(curr);
			}
		}
		
		// djikstra it up baby
		while (!pq.isEmpty()) {
			GridSpot curr = pq.poll();
			int x = curr.x;
			int y = curr.y;
			// for each neighbor, try going to it
			// up
			if (x > 0) {
				int alt = dist[x][y] + grid[x - 1][y];
				if (alt < dist[x - 1][y]) {
					dist[x - 1][y] = alt;
					GridSpot next = new GridSpot(x - 1, y, alt);
					int ind = (x - 1) * grid.length + y;
					pq.remove(spots.get(ind));
					pq.add(next);
					spots.put(ind, next);
				}
			}
			// left
			if (y > 0) {
				int alt = dist[x][y] + grid[x][y - 1];
				if (alt < dist[x][y - 1]) {
					dist[x][y - 1] = alt;
					GridSpot next = new GridSpot(x, y - 1, alt);
					int ind = x * grid.length + (y - 1);
					pq.remove(spots.get(ind));
					pq.add(next);
					spots.put(ind, next);
				}
			}
			// down
			if (x < grid.length - 1) {
				int alt = dist[x][y] + grid[x + 1][y];
				if (alt < dist[x + 1][y]) {
					dist[x + 1][y] = alt;
					GridSpot next = new GridSpot(x + 1, y, alt);
					int ind = (x + 1) * grid.length + y;
					pq.remove(spots.get(ind));
					pq.add(next);
					spots.put(ind, next);
				}
			}
			// right
			if (y < grid[0].length - 1) {
				int alt = dist[x][y] + grid[x][y + 1];
				if (alt < dist[x][y + 1]) {
					dist[x][y + 1] = alt;
					GridSpot next = new GridSpot(x, y + 1, alt);
					int ind = x * grid.length + (y + 1);
					pq.remove(spots.get(ind));
					pq.add(next);
					spots.put(ind, next);
				}
			}
			if (x == grid.length - 1 && y == grid[0].length - 1) {
				break;
			}
		}
		return dist[dist.length - 1][dist[0].length - 1];
	}
		
	public static void main(String[] args) {
		// takes like 5 seconds on my computer, a bit slow oops
		// honestly i blame java for having no good built in
		// priority queue, i'm pretty sure it would be way faster
		// in python
		System.out.println(ans());
	}
}
