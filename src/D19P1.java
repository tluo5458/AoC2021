import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class D19P1 {
	// this is basically a brute force solution
	// first, we check which pairs of scanners intersect each other
	// this is done by brute forcing through all ways of normalizing the 
	// two scanners (by translating one of the beacons to the origin)
	// then rotating one of the scanners, and then just counting
	// the max number of intersections
	// we also store the transform needed to take one scanner to
	// the other one (i.e. the transformation to make it so that
	// there are at least 12 beacons that are identical)
	// then, with those, we do a DFS to traverse this graph so that
	// we can find a path from each scanner to scanner 0
	// finally, we apply the necessary transformations on each scanner
	// to take it to scanner 0, and combine them all into one giant
	// scanner, then we can finally just count the number of beacons
	
	public static final Rotation[] ROTS = 
		{
				new Rotation(new int[][] {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}),
				new Rotation(new int[][] {{1, 0, 0}, {0, 0, 1}, {0, -1, 0}}),
				new Rotation(new int[][] {{1, 0, 0}, {0, -1, 0}, {0, 0, -1}}),
				new Rotation(new int[][] {{1, 0, 0}, {0, 0, -1}, {0, 1, 0}}),
				new Rotation(new int[][] {{-1, 0, 0}, {0, -1, 0}, {0, 0, 1}}),
				new Rotation(new int[][] {{-1, 0, 0}, {0, 0, 1}, {0, 1, 0}}),
				new Rotation(new int[][] {{-1, 0, 0}, {0, 1, 0}, {0, 0, -1}}),
				new Rotation(new int[][] {{-1, 0, 0}, {0, 0, -1}, {0, -1, 0}}),
				new Rotation(new int[][] {{0, 1, 0}, {-1, 0, 0}, {0, 0, 1}}),
				new Rotation(new int[][] {{0, 1, 0}, {0, 0, 1}, {1, 0, 0}}),
				new Rotation(new int[][] {{0, 1, 0}, {1, 0, 0}, {0, 0, -1}}),
				new Rotation(new int[][] {{0, 1, 0}, {0, 0, -1}, {-1, 0, 0}}),
				new Rotation(new int[][] {{0, -1, 0}, {1, 0, 0}, {0, 0, 1}}),
				new Rotation(new int[][] {{0, -1, 0}, {0, 0, 1}, {-1, 0, 0}}),
				new Rotation(new int[][] {{0, -1, 0}, {-1, 0, 0}, {0, 0, -1}}),
				new Rotation(new int[][] {{0, -1, 0}, {0, 0, -1}, {1, 0, 0}}),
				new Rotation(new int[][] {{0, 0, 1}, {0, 1, 0}, {-1, 0, 0}}),
				new Rotation(new int[][] {{0, 0, 1}, {1, 0, 0}, {0, 1, 0}}),
				new Rotation(new int[][] {{0, 0, 1}, {0, -1, 0}, {1, 0, 0}}),
				new Rotation(new int[][] {{0, 0, 1}, {-1, 0, 0}, {0, -1, 0}}),
				new Rotation(new int[][] {{0, 0, -1}, {0, 1, 0}, {1, 0, 0}}),
				new Rotation(new int[][] {{0, 0, -1}, {1, 0, 0}, {0, -1, 0}}),
				new Rotation(new int[][] {{0, 0, -1}, {0, -1, 0}, {-1, 0, 0}}),
				new Rotation(new int[][] {{0, 0, -1}, {-1, 0, 0}, {0, 1, 0}})
		};
	
	// a transform that acts on a scanner
	private static abstract class Transform {
		abstract Scanner transform(Scanner s);
		
		abstract Transform inverse();
	}
	
	// acts both as coordinates and as translations
	private static class Coord extends Transform {
		int[] coords;

		Coord(String[] parse) {
			coords = new int[3];
			for (int i = 0; i < 3; i++) {
				coords[i] = Integer.parseInt(parse[i]);
			}
		}
		
		Coord(int[] c) {
			coords = new int[3];
			for (int i = 0; i < 3; i++) {
				coords[i] = c[i];
			}
		}
		
		Coord(Coord c) {
			coords = new int[3];
			for (int i = 0; i < 3; i++) {
				coords[i] = c.coords[i];
			}
		}
		
		public Coord inverse() {
			return new Coord(new int[] {-1 * coords[0], -1 * coords[1], -1 * coords[2]});
		}
		
		public Coord translate(Coord o) {
			int[] ret = new int[3];
			for (int i = 0; i < 3; i++) {
				ret[i] = coords[i] + o.coords[i];
			}
			return new Coord(ret);
		}
		
		public Scanner transform(Scanner s) {
			Coord[] ret = new Coord[s.coords.length];
			for (int i = 0; i < ret.length; i++) {
				ret[i] = translate(s.coords[i]);
			}
			return new Scanner(ret);
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Coord)) {
				return false;
			}
			
			Coord c = (Coord) o;
			return Arrays.equals(coords, c.coords);
		}
		
		@Override
		public String toString() {
			return Arrays.toString(coords);
		}
	}
	
	// encodes rotations
	private static class Rotation extends Transform {
		int[][] rot;
		
		Rotation(int[][] rotation) {
			rot = new int[3][3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					rot[i][j] = rotation[i][j];
				}
			}
		}
		
		public Coord rotate(Coord o) {
			int[] ret = new int[3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					ret[j] += rot[i][j] * o.coords[i];
				}
			}
			return new Coord(ret);
		}
		
		public Scanner transform(Scanner s) {
			Coord[] ret = new Coord[s.coords.length];
			for (int i = 0; i < ret.length; i++) {
				ret[i] = rotate(s.coords[i]);
			}
			return new Scanner(ret);
		}
		
		public Rotation inverse() {
			int[][] next = new int[3][3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					next[j][i] = rot[i][j];
				}
			}
			return new Rotation(next);
		}
		
		@Override
		public boolean equals(Object object) {
			if (!(object instanceof Rotation)) {
				return false;
			}
			Rotation o = (Rotation) object;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (!(rot[i][j] == o.rot[i][j])) {
						return false;
					}
				}
			}
			return true;
		}
	}

	// has no relation to the I/O scanner
	private static class Scanner {
		int id;
		Coord[] coords;
		
		Scanner(ArrayList<String> lines, int num) {
			id = num;
			coords = new Coord[lines.size()];
			for (int i = 0; i < lines.size(); i++) {
				coords[i] = new Coord(lines.get(i).split(","));
			}
		}
		
		Scanner(Coord[] c) {
			coords = new Coord[c.length];
			for (int i = 0; i < c.length; i++) {
				coords[i] = new Coord(c[i]);
			}
		}
		
		@Override
		public String toString() {
			return "Scanner ID " + id + ": " + Arrays.toString(coords) + "\n";
		}
	}

	// just a wrapper class for a list of transforms
	private static class Transforms extends Transform {
		Transform[] transforms;
		
		Transforms(Transform[] trans) {
			transforms = new Transform[trans.length];
			for (int i = 0; i < trans.length; i++) {
				transforms[i] = trans[i];
			}
		}

		@Override
		public Scanner transform(Scanner s) {
			Scanner curr = s;
			for (Transform t : transforms) {
				curr = t.transform(curr);
			}
			return curr;
		}

		@Override
		public Transforms inverse() {
			Transform[] inverses = new Transform[transforms.length];
			for (int i = 0; i < transforms.length; i++) {
				inverses[i] = transforms[transforms.length - 1 - i].inverse();
			}
			return new Transforms(inverses);
		}
		
	}
	
	// combine two scanners
	public static Scanner combine(Scanner a, Scanner b) {
		ArrayList<Coord> temp = new ArrayList<Coord>();
		for (Coord c : a.coords) {
			temp.add(c);
		}
		for (Coord c : b.coords) {
			if (!temp.contains(c)) {
				temp.add(c);
			}
		}
		Coord[] fin = new Coord[temp.size()];
		for (int i = 0; i < temp.size(); i++) {
			fin[i] = temp.get(i);
		}
		return new Scanner(fin);
	}

	// count the number of elements in common between two scanners
	public static int numCommon(Scanner a, Scanner b) {
		int count = 0;
		for (Coord c : a.coords) {
			for (Coord d : b.coords) {
				if (c.equals(d)) {
					count++;
				}
			}
		}
		return count;
	}

	// returns a list of transforms to apply to b to get to a
	public static Transforms collides(Scanner a, Scanner b, int thresh) {
		// iterate through all possible origins of a
		// apply all possible rotations of b
		// along with all possible origins of b
		// then just directly compare and pray
		int max = 0;
		for (Coord aOrig : a.coords) {
			Scanner normA = aOrig.inverse().transform(a);
			for (Rotation r : ROTS) {
				Scanner rotB = r.transform(b);
				for (Coord bOrig : rotB.coords) {
					Scanner normRotB = bOrig.inverse().transform(rotB);
					int common = numCommon(normA, normRotB);
					max = Math.max(max, common);
					if (common >= thresh) {
						return new Transforms(new Transform[] {r, bOrig.inverse(), aOrig});
					}
				}
			}
		}
		return null;
	}
	
	public static int ans() {
		File file = new File("inputs/d-19.txt");

		BufferedReader br;
		String st;
		ArrayList<Scanner> scanners = new ArrayList<Scanner>();
		ArrayList<String> lines = new ArrayList<String>();
		int id = 0;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// we do a bit of parsing
				if (st.length() == 0) {
					scanners.add(new Scanner(lines, id));
				} else if (st.substring(0, 3).equals("---")) {
					String[] parse = st.split(" ");
					id = Integer.parseInt(parse[2]);
					lines = new ArrayList<String>();
				} else {
					lines.add(st);
				}
			}
			scanners.add(new Scanner(lines, id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// store the transitional transforms for any pairs that collide
		// transitions[i][j] = the transitions needed to go from scanners[i] to scanners[j]
		Transforms[][] transitions = new Transforms[scanners.size()][scanners.size()];
		for (int i = 0; i < scanners.size(); i++) {
			for (int j = i + 1; j < scanners.size(); j++) {
				Transforms t = collides(scanners.get(i), scanners.get(j), 12);
				transitions[j][i] = t;
				if (t != null) {
					transitions[i][j] = t.inverse();
				}
			}
		}
		// now dfs to find a path from every scanner to scanner 0
		boolean[] visited = new boolean[scanners.size()];
		int[] parent = new int[scanners.size()];
		visited[0] = true;
		for (int i = 1; i < parent.length; i++) {
			parent[i] = -1;
		}
		Stack<Integer> dfs = new Stack<Integer>();
		dfs.add(0);
		while (!dfs.isEmpty()) {
			int curr = dfs.pop();
			for (int i = 0; i < scanners.size(); i++) {
				if (!visited[i] && transitions[i][curr] != null) {
					visited[i] = true;
					dfs.add(i);
					parent[i] = curr;
				}
			}
		}
		Scanner total = scanners.get(0);
		for (int i = 1; i < scanners.size(); i++) {
			Scanner curr = scanners.get(i);
			int currInd = i;
			while (parent[currInd] != currInd) {
				curr = transitions[currInd][parent[currInd]].transform(curr);
				currInd = parent[currInd];
			}
			total = combine(total, curr);
		}
		return total.coords.length;
	}
	
	public static void main(String[] args) {
		// takes around 15 seconds on my computer, which is admittedly slow but honestly i cannot give less of a shit
		System.out.println(ans());
	}
}
