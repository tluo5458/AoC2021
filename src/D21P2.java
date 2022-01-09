import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class D21P2 {
	// quite fundamentally different solution - this one uses recursion
	public static long[] winners(int[] scores, int[] pos, int turn) {
		long[] ret = new long[2];
		if (scores[0] >= 21) {
			ret[0] = 1;
			return ret;
		}
		if (scores[1] >= 21) {
			ret[1] = 1;
			return ret;
		}
		int curr = turn % 2;
		// simulate the three dice outcomes
		// first, we can easily find the prob distribution
		// probs[i] = # of universes where i + 3 is rolled
		int[] probs = {1, 3, 6, 7, 6, 3, 1};
		for (int i = 0; i < probs.length; i++) {
			int[] nextScore = scores.clone();
			int[] nextPos = pos.clone();
			nextPos[curr] = nextPos[curr] + (i + 3);
			nextPos[curr] = (nextPos[curr] - 1) % 10 + 1;
			nextScore[curr] += nextPos[curr];
			long[] sim = winners(nextScore, nextPos, turn + 1);
			ret[0] += sim[0] * probs[i];
			ret[1] += sim[1] * probs[i];
		}
		return ret;
	}
	
	public static long ans() {
		File file = new File("inputs/d-21.txt");

		BufferedReader br;
		int[] players = new int[2];
		int[] scores = new int[2];
		int count = 0;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// we do a bit of parsing
				players[count] = Integer.parseInt(st.split(" ")[4]);
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long[] fin = winners(scores, players, 0);
		return Math.max(fin[0], fin[1]);
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
