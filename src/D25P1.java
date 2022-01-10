import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class D25P1 {
	public static int ans() {
		File file = new File("inputs/d-25.txt");

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
		char[][] cucumber = new char[lines.size()][lines.get(0).length()];
		for (int i = 0; i < cucumber.length; i++) {
			for (int j = 0; j < cucumber[0].length; j++) {
				cucumber[i][j] = lines.get(i).charAt(j);
			}
		}
		int changed = 0;
		int time = 0;
		while (true) {
			changed = 0;
			char[][] next = new char[cucumber.length][cucumber[0].length];
			// first go through to find east facing
			for (int i = 0; i < cucumber.length; i++) {
				for (int j = 0; j < cucumber[0].length; j++) {
					if (cucumber[i][j] == '>') {
						if (cucumber[i][(j + 1) % cucumber[0].length] == '.') {
							next[i][(j + 1) % cucumber[0].length] = '>';
							changed++;
						} else {
							next[i][j] = '>';
						}
					}
				}
			}
			// copy the rest
			for (int i = 0; i < cucumber.length; i++) {
				for (int j = 0; j < cucumber[0].length; j++) {
					if (cucumber[i][j] == 'v') {
						next[i][j] = 'v';
					}
				}
			}
			for (int i = 0; i < cucumber.length; i++) {
				for (int j = 0; j < cucumber[0].length; j++) {
					if (next[i][j] != 'v' && next[i][j] != '>') {
						next[i][j] = '.';
					}
				}
			}
			
			cucumber = next;
			char[][] next2 = new char[cucumber.length][cucumber[0].length];
			
			// then go through south facing
			for (int i = 0; i < cucumber.length; i++) {
				for (int j = 0; j < cucumber[0].length; j++) {
					if (cucumber[i][j] == 'v') {
						if (cucumber[(i + 1) % cucumber.length][j] == '.') {
							next2[(i + 1) % cucumber.length][j] = 'v';
							changed++;
						} else {
							next2[i][j] = 'v';
						}
					}
				}
			}
			// copy the rest
			for (int i = 0; i < cucumber.length; i++) {
				for (int j = 0; j < cucumber[0].length; j++) {
					if (cucumber[i][j] == '>') {
						next2[i][j] = '>';
					}
				}
			}
			for (int i = 0; i < next2.length; i++) {
				for (int j = 0; j < next2[0].length; j++) {
					if (next2[i][j] != '>' && next2[i][j] != 'v') {
						next2[i][j] = '.';
					}
				}
			}
			time++;
			cucumber = next2;
			if (changed == 0) {
				return time;
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
