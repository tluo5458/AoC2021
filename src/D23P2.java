import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class D23P2 {
	public static final int[] COSTS = {1, 10, 100, 1000};
	
	private static class Hole {
		int capacity;
		ArrayDeque<Integer> occupants;
		
		Hole(int c) {
			capacity = c;
			occupants = new ArrayDeque<Integer>();
		}
		
		Hole(Hole o) {
			capacity = o.capacity;
			occupants = new ArrayDeque<Integer>(o.occupants);
		}
		
		@Override
		public String toString() {
			String ret = "";
			for (int i : occupants) {
				ret += Integer.toString(i);
			}
			while (ret.length() < capacity) {
				ret = "." + ret;
			}
			return ret;
		}
	}
	
	private static class Board implements Comparable<Board> { 
		// hole[i] is surrounded by main[i + 1] and main[i + 2]
		int[] main;
		Hole[] holes;
		int capacity;
		int cost;
		
		Board(int c) {
			main = new int[7];
			holes = new Hole[4];
			for (int i = 0; i < 4; i++) {
				holes[i] = new Hole(c);
			}
			capacity = c;
			cost = 0;
		}
		
		Board(Board b) {
			main = new int[7];
			for (int i = 0; i < 7; i++) {
				main[i] = b.main[i];
			}
			holes = new Hole[4];
			for (int i = 0; i < 4; i++) {
				holes[i] = new Hole(b.holes[i]);
			}
			capacity = b.capacity;
			cost = b.cost;
		}

		@Override
		public int compareTo(Board o) {
			return cost - o.cost;
		}
		
		@Override
		public String toString() {
			String ret = "Cost " + cost + "\n";
			for (int i = 0; i < main.length; i++) {
				if (main[i] == 0) {
					ret += ".";
				} else {
					ret += Integer.toString(main[i]);
				}
				if (i != 0 && i != 5) {
					ret += " ";
				}
			}
			ret += "\n";
			for (int i = 0; i < capacity; i++) {
				ret += "  ";
				for (int j = 0; j < 4; j++) {
					ret += holes[j].toString().charAt(i) + " ";
				}
				ret += "\n";
			}
			return ret;
		}
	}
	
	public static Board moveFromHole(Board b, int hole, int mInd) {
		// first check if moving from hole is allowed
		if (mInd <= hole + 1) {
			// moving left
			for (int i = mInd; i <= hole + 1; i++) {
				if (b.main[i] != 0) {
					return null;
				}
			}
		} else {
			// moving
			for (int i = hole + 2; i <= mInd; i++) {
				if (b.main[i] != 0) {
					return null;
				}
			}
		}
		// check that the thing can actually move out of the hole
		if (b.holes[hole].occupants.size() > 0) {
			if (b.holes[hole].occupants.peek() == hole + 1) {
				// if the top element of the stack is correct
				// check that there is something else below it
				boolean good = false;
				for (Integer i : b.holes[hole].occupants) { 
					if (i != hole + 1) {
						good = true;
					}
				}
				if (!good) {
					return null;
				}
			}
		} else {
			// hole is empty
			return null;
		}
		// otherwise the move is valid
		Board ret = new Board(b);
		// remove the element from stack
		int i = ret.holes[hole].occupants.pop();
		ret.main[mInd] = i;
		// calculate number of moves taken
		int moves = b.capacity - ret.holes[hole].occupants.size();
		if (mInd <= hole + 1) {
			moves += 1 + 2 * (hole + 1 - mInd);
			if (mInd == 0) {
				moves--;
			}
		} else {
			moves += 1 + 2 * (mInd - hole - 2);
			if (mInd == 6) {
				moves--;
			}
		}
		ret.cost += moves * COSTS[i - 1];
		return ret;
	}
	
	public static Board moveIntoHole(Board b, int mInd, int hole) {
		// first check if hole has space
		if (b.holes[hole].occupants.size() == b.capacity) {
			return null;
		}
		// now check if the thing is moving into the right hole
		if (b.main[mInd] != hole + 1) {
			return null;
		}
		// now check that everything in the hole is the right thing
		for (int i : b.holes[hole].occupants) {
			if (i != hole + 1) {
				return null;
			}
		}
		// finally check that it isn't obstructed
		if (mInd <= hole + 1) {
			for (int i = mInd + 1; i <= hole + 1; i++) {
				if (b.main[i] != 0) {
					return null;
				}
			}
		} else {
			for (int i = hole + 2; i < mInd; i++) {
				if (b.main[i] != 0) {
					return null;
				}
			}
		}
		// now do the moving
		Board ret = new Board(b);
		int item = ret.main[mInd];
		ret.holes[hole].occupants.push(item);
		ret.main[mInd] = 0;
		// now calculate the cost
		int moves = b.capacity - ret.holes[hole].occupants.size() + 1;
		if (mInd <= hole + 1) {
			moves += 1 + 2 * (hole + 1 - mInd);
			if (mInd == 0) {
				moves--;
			}
		} else {
			moves += 1 + 2 * (mInd - hole - 2);
			if (mInd == 6) {
				moves--;
			}
		}
		ret.cost += moves * COSTS[item - 1];
		return ret;
	}
	
	private static boolean complete(Board b) {
		for (int i = 0; i < 7; i++) {
			if (b.main[i] != 0) {
				return false;
			}
		}
		for (int i = 0; i < 4; i++) {
			if (b.holes[i].occupants.size() != b.capacity) {
				return false;
			}
			for (int j : b.holes[i].occupants) {
				if (j != i + 1) {
					return false;
				}
			}
		}
		return true;
	}
		
	public static int ans() {
		File file = new File("inputs/d-23.txt");

		BufferedReader br;
		String st;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// we do a bit of parsing
				lines.add(st);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		lines.add(3, "  #D#B#A#C#");
		lines.add(3, "  #D#C#B#A#");
		Board init = new Board(4);
		String[] top = lines.get(2).split("#");
		for (int j = 5; j > 2; j--) {
			for (int i = 0; i < 4; i++) {
				init.holes[i].occupants.push(lines.get(j).split("#")[i + 1].charAt(0) - 'A' + 1);
			}
		}
		for (int i = 0; i < 4; i++) {
			init.holes[i].occupants.push(top[i + 3].charAt(0) - 'A' + 1);
		}
		// now dijkstra's to win
		PriorityQueue<Board> pq = new PriorityQueue<Board>();
		pq.add(init);
		while (!pq.isEmpty()) {
			Board b = pq.poll();
			if (complete(b)) {
				return b.cost;
			}
			// try all moves
			for (int i = 0; i < 7; i++) {
				if (b.main[i] != 0) {
					// try moving into holes
					Board curr = moveIntoHole(b, i, b.main[i] - 1);
					if (curr != null) {
						pq.add(curr);
					}
				} else {
					// try moving out of holes
					for (int hole = 0; hole < 4; hole++) {
						Board curr = moveFromHole(b, hole, i);
						if (curr != null) {
//							System.out.println("Move to " + i + " out of hole " + hole + ": \n" + b + curr);
							pq.add(curr);
						}
					}
				}
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
