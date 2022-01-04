import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class D05P2 {
	private static class Segment {
		int x1;
		int y1;
		int x2;
		int y2;
		
		Segment(int xa, int ya, int xb, int yb) {
			x1 = xa;
			x2 = xb;
			y1 = ya;
			y2 = yb;
		}
	}
	
	public static int ans() {
		int ans = 0;
		
		File file = new File("inputs/d5.txt");

		ArrayList<Segment> vents = new ArrayList<Segment>();
		BufferedReader br;
		String st;
		try {
			// parse shit
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				String[] coords = st.split(" -> ");
				String[] c1 = coords[0].split(",");
				String[] c2 = coords[1].split(",");
				int[] xy1 = {Integer.parseInt(c1[0]), Integer.parseInt(c1[1])};
				int[] xy2 = {Integer.parseInt(c2[0]), Integer.parseInt(c2[1])};
				vents.add(new Segment(xy1[0], xy1[1], xy2[0], xy2[1]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// now find the max x and y coords
		int maxX = 0;
		int maxY = 0;
		for (Segment s : vents) {
			maxX = Math.max(maxX, Math.max(s.x1, s.x2));
			maxY = Math.max(maxY, Math.max(s.y1, s.y2));
		}
		// this is horrible but it works innit
		int[][] grid = new int[maxX + 1][maxY + 1];
		for (Segment s : vents) {
			int xDiff = s.x2 - s.x1;
			int yDiff = s.y2 - s.y1;
			// get the magnitude of the difference
			int mag = Math.max(Math.abs(xDiff), Math.abs(yDiff));
			// this is the amount each coordinate moves in each step
			// moving from x1,y1 to x2,y2
			int xDelta = xDiff / mag;
			int yDelta = yDiff / mag;
			// now move along the segment
			for (int i = 0; i <= mag; i++) {
				grid[s.x1 + xDelta * i][s.y1 + yDelta * i]++;
			}
		}
		// iterate through grid to find intersections
		for (int i = 0; i <= maxX; i++) {
			for (int j = 0; j <= maxY; j++) {
				if (grid[i][j] > 1) {
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
