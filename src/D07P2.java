import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class D07P2 {
	public static int ans() {
		int ans = 0;
		
		File file = new File("inputs/d7.txt");

		// here, the same shifting argument as last time applies
		// when you shift by 1, the total cost decreases by the old sum 
		// of distances to the crabs on one side, while increasing by 
		// the new sum of the distances to the crabs on the other side
		
		// if the fuel cost = distance squared, then the optimal meetup
		// point would be the average but it's not in this case :(
		
		// it actually ends up being somewhere close to the average, but 
		// slightly skewed towards the median, which is rather annoying
		// wish i could easily calculate it using closed form
		// but derivatives become annoying with absolute values
		int[] crabs = null;
		int num = 0;
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				String[] parse = st.split(",");
				num = parse.length;
				crabs = new int[num];
				for (int i = 0; i < num; i++) {
					crabs[i] = Integer.parseInt(parse[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// create a hashmap of the # of crabs on each coordinate
		HashMap<Integer, Integer> coords = new HashMap<Integer, Integer>();
		for (int i : crabs) {
			if (coords.containsKey(i)) {
				coords.put(i, coords.get(i) + 1);
			} else {
				coords.put(i, 1);
			}
		}
		
		// fortunately we can use a double pointer method, where we
		// increment the current cost of the crabs behind us while moving
		// two pointers from either side
		// we move the pointer with the lower cost of increment
		
		// these are the pointers, min and max coords of the crabs
		int left = Integer.MAX_VALUE;
		int right = Integer.MIN_VALUE;
		for (int i : crabs) {
			left = Math.min(left, i);
			right = Math.max(right, i);
		}
		// these count how many crabs are behind each pointer
		int lCount = coords.get(left);
		int rCount = coords.get(right);
		// these count the cost of moving each pointer
		int lCost = coords.get(left);
		int rCost = coords.get(right);
		// ans will keep track of the total cost
		while (left != right) {
			if (lCost <= rCost) {
				left++;
				ans += lCost;
				lCount += coords.containsKey(left) ? coords.get(left) : 0;
				lCost += lCount;
			} else {
				right--;
				ans += rCost;
				rCount += coords.containsKey(right) ? coords.get(right) : 0;
				rCost += rCount;
			}
		}
		
		return ans;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
