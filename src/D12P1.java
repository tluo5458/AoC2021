import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

public class D12P1 {
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
			HashSet<String> visited = new HashSet<String>();
			if (small.contains(start)) {
				visited.add(start);
			}
			return pathHelper(start, end, visited);
		}
		
		// recurse to win
		private int pathHelper(String start, String end, HashSet<String> smallVisited) {
			int tot = 0;
			if (start.equals(end)) {
				return 1;
			}
			// iterate through adjacent nodes
			for (String s : edges.get(start)) {
				if (smallVisited.contains(s)) {
					continue;
				}
				HashSet<String> temp = new HashSet<String>();
				for (String v : smallVisited) {
					temp.add(v);
				}
				if (small.contains(s)) {
					temp.add(s);
				}
				tot += pathHelper(s, end, temp);
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
