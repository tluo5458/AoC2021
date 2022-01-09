import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class D19P2 {
	// pretty much identical to P1, with the exception of the ending
	// this no longer combines the scanners; it traverses the final
	// tree for each element, doing all of the transformations on the
	// way to find the location of each scanner
	// and finally just brute force the maximum manhattan distance
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
		
		abstract Coord transform(Coord c);
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
		
		public Coord transform(Coord o) {
			int[] ret = new int[3];
			for (int i = 0; i < 3; i++) {
				ret[i] = coords[i] + o.coords[i];
			}
			return new Coord(ret);
		}
		
		public Scanner transform(Scanner s) {
			Coord[] ret = new Coord[s.coords.length];
			for (int i = 0; i < ret.length; i++) {
				ret[i] = transform(s.coords[i]);
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
		
		public Coord transform(Coord o) {
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
				ret[i] = transform(s.coords[i]);
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
		public Coord transform(Coord c) {
			Coord curr = c;
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
	
	// manhattan distance
	public static int manDist(Coord a, Coord b) {
		int ret = 0;
		for (int i = 0; i < 3; i++) {
			ret += Math.abs(a.coords[i] - b.coords[i]);
		}
		return ret;
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
		Coord[] origins = new Coord[scanners.size()];
		origins[0] = new Coord(new int[] {0, 0, 0});
		// now traverse back through this tree to get the locations
		// of all the other scanners
		// gotta make sure to do every single transform on the way
		for (int i = 1; i < scanners.size(); i++) {
			int currInd = i;
			Coord curr = origins[0];
			while (parent[currInd] != currInd) {
				curr = transitions[currInd][parent[currInd]].transform(curr);
				currInd = parent[currInd];
			}
			origins[i] = curr;
		}
		// finally manhattan distance it up
		int max = 0;
		for (int i = 0; i < origins.length; i++) {
			for (int j = 1; j < origins.length; j++) {
				max = Math.max(max, manDist(origins[i], origins[j]));
			}
		}
		return max;
	}
	
	public static void main(String[] args) {
		// takes around 15 seconds on my computer, which is admittedly slow but honestly i cannot give less of a shit
		System.out.println(ans());
	}
}
