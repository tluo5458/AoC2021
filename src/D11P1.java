import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class D11P1 {
	public static int ans() {
		int ans = 0;
		
		File file = new File("inputs/d-11.txt");

		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// we do a bit of parsing
				lines.add(st);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// turn it into a grid
		int[][] octopi = new int[lines.size()][lines.get(0).length()];
		for (int i = 0; i < octopi.length; i++) {
			for (int j = 0; j < octopi[0].length; j++) {
				octopi[i][j] = lines.get(i).charAt(j) - 48;
			}
		}
		
		// now iterate
		for (int day = 0; day < 100; day++) {
			// increment energy
			for (int i = 0; i < octopi.length; i++) {
				for (int j = 0; j < octopi[0].length; j++) {
					octopi[i][j]++;
				}
			}
			// array to keep track of whether or not already spread
			boolean[][] spread = new boolean[octopi.length][octopi[0].length];
			boolean pending = false;
			do {
				pending = false;
				for (int i = 0; i < octopi.length; i++) {
					for (int j = 0; j < octopi[0].length; j++) {
						if (octopi[i][j] > 9 && !spread[i][j]) {
							pending = true;
							// now spread
							spread[i][j] = true;
							ans++;
							for (int xDiff = -1; xDiff <= 1; xDiff++) {
								for (int yDiff = -1; yDiff <= 1; yDiff++) {
									if (xDiff != 0 || yDiff != 0) {
										try {
											octopi[i + xDiff][j + yDiff]++;
										} catch (IndexOutOfBoundsException e) {}
									}
								}
							}
						}
					}
				}
			} while (pending);
			for (int i = 0; i < octopi.length; i++) {
				for (int j = 0; j < octopi[0].length; j++) {
					if (spread[i][j]) {
						octopi[i][j] = 0;
					}
				}
			}
		}
		return ans;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
