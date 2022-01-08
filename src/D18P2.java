import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class D18P2 {
	// class representing a snailfish number - it is structured
	// as a binary tree
	// if val is -1, then it is not a leaf, otherwise it is a leaf
	// non-leaves must have 2 children
	private static class SnailNum {
		SnailNum left;
		SnailNum right;
		SnailNum parent;
		boolean isLeft;
		int val;
		
		// parse a string as a snailfish number
		SnailNum(String s) {
			parent = null;
			isLeft = true;
			if (s.charAt(0) != '[') {
				val = Integer.parseInt(s);
				left = null;
				right = null;
			} else {
				int diff = 0;
				for (int i = 1; i < s.length(); i++) {
					if (s.charAt(i) == '[') {
						diff++;
					} else if (s.charAt(i) == ']') {
						diff--;
					} else if (diff == 0 && s.charAt(i) == ',') {
						val = -1;
						left = new SnailNum(s.substring(1, i));
						left.parent = this;
						left.isLeft = true;
						right = new SnailNum(s.substring(i + 1, s.length() - 1));
						right.parent = this;
						right.isLeft = false;
					}
				}
			}
		}
		
		// create a snailfish number with two given children
		SnailNum(SnailNum a, SnailNum b) {
			left = a;
			a.parent = this;
			a.isLeft = true;
			right = b;
			b.parent = this;
			b.isLeft = false;
			val = -1;
			parent = null;
			isLeft = true;
		}
		
		// yes
		public String toString() {
			if (val != -1) {
				return Integer.toString(val);
			}
			return "[" + left.toString() + "," + right.toString() + "]";
		}
		
		// reduce by first trying to explode reduce, then split reduce
		// returns whether or not anything was reduced
		public boolean reduce() {
			if (explodeReduce(0)) {
				return true;
			}
			if (splitReduce()) {
				return true;
			}
			return false;
		}
		
		// first check if current node is a leaf, whether or not to split
		// otherwise we have two children, so call split reduce on left
		// then right
		// returns whether or not there was a split
		public boolean splitReduce() {
			if (val != -1) {
				if (val > 9) {
					// split
					int l = val / 2;
					int r = val - l;
					val = -1;
					left = new SnailNum(Integer.toString(l));
					right = new SnailNum(Integer.toString(r));
					left.parent = this;
					right.parent = this;
					left.isLeft = true;
					right.isLeft = false;
					return true;
				}
				return false;
			}
			if (left.splitReduce()) {
				return true;
			}
			if (right.splitReduce()) {
				return true;
			}
			return false;
		}
		
		// do an in order traversal down the tree to explode
		// use parent and isLeft to traverse backwards to find adjacent
		// regular numbers
		public boolean explodeReduce(int currDepth) {
			if (val != -1) {
				return false;
			}
			// in order traversal
			if (left.explodeReduce(currDepth + 1)) {
				return true;
			}
			if (currDepth >= 4) {
				if (val == -1) {
					// explode
					int lVal = left.val;
					int rVal = right.val;
					if (lVal == -1 || rVal == -1) {
						System.out.println("Very bad");
					}
					// go left
					SnailNum curr = this;
					while (curr.isLeft && curr.parent != null) {
						curr = curr.parent;
					}
					if (curr.parent != null) {
						// traverse back down to the right
						curr = curr.parent.left;
						while (curr.val == -1) {
							curr = curr.right;
						}
						curr.val += lVal;
					}
					// go right
					curr = this;
					while (!curr.isLeft && curr.parent != null) {
						curr = curr.parent;
					}
					if (curr.parent != null) {
						// traverse back down to the left
						curr = curr.parent.right;
						while (curr.val == -1) {
							curr = curr.left;
						}
						curr.val += rVal;
					}
					val = 0;
					left = null;
					right = null;
					return true;
				}
			}
			if (right.explodeReduce(currDepth + 1)) {
				return true;
			}
			return false;
		}
		
		// recursively calculate magnitude
		public int magnitude() {
			if (val != -1) {
				return val;
			}
			return left.magnitude() * 3 + right.magnitude() * 2;
		}
	}

	// add two snailfish numbers by just combining then reducing
	public static SnailNum add(SnailNum a, SnailNum b) {
		if (a == null) {
			return b;
		}
		SnailNum temp = new SnailNum(a, b);
		while (temp.reduce()) {
			continue;
		}
		return temp;
	}
	
	public static int ans() {
		File file = new File("inputs/d-18.txt");

		BufferedReader br;
		String st;
		// store STRINGS because add actually alters the underlying
		// SnailNums and cloning is annoying because SnailNums
		// also store their parents :XD:
		ArrayList<String> nums = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				nums.add(st);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int maxMag = 0;
		for (int i = 0; i < nums.size(); i++) {
			for (int j = 0; j < nums.size(); j++) {
				SnailNum a = new SnailNum(nums.get(i));
				SnailNum b = new SnailNum(nums.get(j));
				maxMag = Math.max(maxMag, add(a, b).magnitude());
			}
		}
		return maxMag;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
