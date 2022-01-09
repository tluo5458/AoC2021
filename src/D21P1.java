import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class D21P1 {
	// overly general solution out of anticipation for part 2
	// turns out i'm bad at predicting part 2
	private static int max(int[] l) {
		int max = 0;
		for (int i : l) {
			max = Math.max(max, i);
		}
		return max;
	}
	
	private static int min(int[] l) {
		int min = l[0];
		for (int i : l) {
			min = Math.min(min, i);
		}
		return min;
	}
	
	public static int ans() {
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
		int die = 1;
		int turn = 0;
		while (max(scores) < 1000) {
			int play = turn % players.length;
			for (int i = 0; i < 3; i++) {
				players[play] = (players[play] + die) % 10;
				if (players[play] == 0) {
					players[play] = 10;
				}
				die = (die % 100) + 1;
			}
			scores[play] += players[play];
			turn++;
		}
		return 3 * turn * min(scores);
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
