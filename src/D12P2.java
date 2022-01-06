import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

public class D12P2 {
	private static class Graph {
		HashSet<String> small;
		HashMap<String, HashSet<String>> edges;
		
		Graph() {
			small = new HashSet<String>();
			edges = new HashMap<String, HashSet<String>>();
		}
		
		public void addEdge(String a, String b) {
			if (!edges.containsKey(a)) {
				edges.put(a, new HashSet<String>());
				if (a.charAt(0) != Character.toUpperCase(a.charAt(0))) {
					small.add(a);
				}
			}
			if (!edges.containsKey(b)) {
				edges.put(b, new HashSet<String>());
				if (b.charAt(0) != Character.toUpperCase(b.charAt(0))) {
					small.add(b);
				}
			}
			edges.get(a).add(b);
			edges.get(b).add(a);
		}
		
		public int numPaths(String start, String end) {
			HashSet<String> vOnce = new HashSet<String>();
			HashSet<String> vTwice = new HashSet<String>();
			if (small.contains(start)) {
				vOnce.add(start);
				vTwice.add(start);
			}
			return pathHelper(start, end, vOnce, vTwice);
		}
		
		// recurse to win
		private int pathHelper(String start, String end, HashSet<String> vOnce, HashSet<String> vTwice) {
			int tot = 0;
			// base
			if (start.equals(end)) {
				return 1;
			}
			// iterate through adjacent nodes
			for (String s : edges.get(start)) {
				// exit if node is small and already has been visited
				// twice, or if another small node has been visited twice
				// and this node has been visited before
				if (vTwice.contains(s)) {
					continue;
				}
				if (vTwice.size() > 1) {
					if (vOnce.contains(s)) {
						continue;
					}
				}
				// copy the hashsets to avoid references issues
				HashSet<String> temp1 = new HashSet<String>();
				HashSet<String> temp2 = new HashSet<String>();
				for (String v : vOnce) {
					temp1.add(v);
				}
				for (String v : vTwice) {
					temp2.add(v);
				}
				// update hashsets as necessary
				if (small.contains(s)) {
					if (temp1.contains(s)) {
						temp2.add(s);
					}
					temp1.add(s);
				}
				// recurse
				tot += pathHelper(s, end, temp1, temp2);
			}
			return tot;
		}
	}
	
	public static int ans() {
		File file = new File("inputs/d-12.txt");

		Graph g = new Graph();
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// parse the lines
				String[] edge = st.split("-");
				g.addEdge(edge[0], edge[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return g.numPaths("start", "end");
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
