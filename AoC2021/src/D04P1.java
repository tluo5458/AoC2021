import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class D04P1 {
	private static class BingoBoard {
		int[][] board;
		boolean[][] hit;
		HashMap<Integer, Integer> coords;
		
		BingoBoard() {
			board = new int[5][5];
			hit = new boolean[5][5];
			coords = new HashMap<Integer, Integer>();
		}
		
		// num gets drawn, method returns if there's a bingo or not
		private boolean hit(int num) {
			// look for the number
			// hashmaps to the rescue
			if (coords.containsKey(num)) {
				int i = coords.get(num) / 5;
				int j = coords.get(num) % 5;
				// mark the spot as hit
				hit[i][j] = true;
				// check the row of that spot
				boolean rowGood = true;
				for (int j2 = 0; j2 < 5; j2++) {
					rowGood = rowGood && hit[i][j2];
				}
				if (rowGood) {
					return rowGood;
				}
				// check the column of that spot if necessary
				boolean colGood = true;
				for (int i2 = 0; i2 < 5; i2++) {
					colGood = colGood && hit[i2][j];
				}
				if (colGood) {
					return colGood;
				}
				// otherwise no bingo
				return false;
			}
			// if not found, no bingo
			return false;
		}
		
		@Override
		public String toString() {
			String ret = "";
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					ret += String.format("%1$2s", board[i][j]) + (hit[i][j] ? "H" : ".") + " ";
				}
				ret += "\n";
			}
			return ret;
		}
	}
	
	public static int ans() {
		File file = new File("inputs/d4.txt");

		ArrayList<BingoBoard> boards = new ArrayList<BingoBoard>();
		BingoBoard curr = null;
		BufferedReader br;
		int line = 0;
		int[] callouts = null;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// a shit load of file parsing
				if (line == 0) {
					String[] parse = st.split(",");
					callouts = new int[parse.length];
					for (int i = 0; i < callouts.length; i++) {
						callouts[i] = Integer.parseInt(parse[i]);
					}
				} else {
					if (line % 6 == 2) {
						curr = new BingoBoard();
					}
					if (line % 6 != 1) {
						String[] row = st.trim().split("\\s+");
						int rowNum = (line - 2) % 5;
						for (int i = 0; i < 5; i++) {
							curr.board[rowNum][i] = Integer.parseInt(row[i]);
							curr.coords.put(Integer.parseInt(row[i]), 5 * rowNum + i);
						}
						if (line % 6 == 0) {
							boards.add(curr);
						}
					}
				}
				line++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// now that that's out of the way, we play bingo
		BingoBoard winner = null;
		int last = -1;
		for (int i : callouts) {
			for (BingoBoard board : boards) {
				if (board.hit(i)) {
					winner = board;
					last = i;
					break;
				}
			}
			if (winner != null) {
				break;
			}
		}
		// print out the winner cuz i wanna see!!!!
		System.out.println(winner);
		
		// now calculate the score of the winner
		int score = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (!winner.hit[i][j]) {
					score += winner.board[i][j];
				}
			}
		}
		return score * last;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
