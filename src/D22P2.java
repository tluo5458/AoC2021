import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class D22P2 {
	// part 2 is exactly what i was afraid of
	private static class Step {
		Rect r;
		boolean set;
		
		Step(String line) {
			String[] onOff = line.split(" ");
			set = onOff[0].charAt(1) == 'n';
			r = new Rect(onOff[1]); 
		}
		
		Step(Rect rO, boolean s) {
			r = new Rect(rO.x, rO.y, rO.z);
			set = s;
		}
		
		public long vol() {
			if (set) {
				return r.vol();
			}
			return -1 * r.vol();
		}
		
		public String toString() {
			return (set ? "turn on  " : "turn off ") + r.toString();
		}
	}
	
	private static class Rect {
		long[] x;
		long[] y;
		long[] z;
		
		Rect(String line) {
			String[] coords = line.split(",");
			String[] xParse = coords[0].split("=")[1].split("\\.\\.");
			String[] yParse = coords[1].split("=")[1].split("\\.\\.");
			String[] zParse = coords[2].split("=")[1].split("\\.\\.");
			x = new long[] {Integer.parseInt(xParse[0]), Integer.parseInt(xParse[1])};
			y = new long[] {Integer.parseInt(yParse[0]), Integer.parseInt(yParse[1])};
			z = new long[] {Integer.parseInt(zParse[0]), Integer.parseInt(zParse[1])}; 
		}
		
		Rect(long[] xN, long[] yN, long[] zN) {
			x = new long[2];
			y = new long[2];
			z = new long[2];
			x[0] = xN[0];
			x[1] = xN[1];
			y[0] = yN[0];
			y[1] = yN[1];
			z[0] = zN[0];
			z[1] = zN[1];
		}
		
		public long vol() {
			return (Math.abs(x[1] - x[0]) + 1) * (Math.abs(y[1] - y[0]) + 1) * (Math.abs(z[1] - z[0]) + 1);
		}
		
		public String toString() {
			return "x: " + x[0] + ".." + x[1] + ", y: " + y[0] + ".." + y[1] + ", z: " + z[0] + ".." + z[1];
		}
	}
	
	private static long[] intersect(long[] a, long[] b) {
		if ((a[1] - b[0]) * (b[1] - a[0]) >= 0) {
			return new long[] {Math.max(a[0], b[0]), Math.min(a[1], b[1])};
		}
		return null;
	}
	
	private static Rect intersect(Rect a, Rect b) {
		long[] x = intersect(a.x, b.x);
		long[] y = intersect(a.y, b.y);
		long[] z = intersect(a.z, b.z);
		if (x == null || y == null || z == null) {
			return null;
		}
		return new Rect(x, y, z);
	}
	
	// i think this algorithm is actually O(2^(#cubes))
	// on average it's pretty good tho
	public static long ans() {
		long ans = 0;
		
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
		// iterate through steps, keeping track of all relevant rects
		// so far along with whether to add or subtract
		// goal is to get a list of rects that we can just add the
		// volumes of 
		// the main idea is that if you want to turn a rect on, you
		// have to also subtract away its intersections with any other
		// already on rects, but then add any intersections of 
		// intersections, and so on
		// similarly if you want to turn a rect off, you subtract
		// away any intersections that are already on, but then add
		// back the intersections of those intersections
		// so we keep track of a running list of rects, along with
		// whether we want to add or subtract it
		// and with intersections, add the intersection with the opposite
		// bool value as the rect in the running list
		ArrayList<Step> fin = new ArrayList<Step>();
		for (Step s : steps) {
			ArrayList<Step> toAdd = new ArrayList<Step>();
			for (Step in : fin) {
				Rect inter = intersect(s.r, in.r);
				if (inter != null) {
					toAdd.add(new Step(inter, !in.set));
				}
			}
			for (Step add : toAdd) {
				fin.add(add);
			}
			if (s.set) {
				fin.add(s);
			}
		}
		for (Step s : fin) {
			ans += s.vol();
		}
		return ans;
	}
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
