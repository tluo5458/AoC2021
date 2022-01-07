import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

public class D17P2 {
	public static int ans() {
		int ans = 0;
		
		File file = new File("inputs/d-17.txt");

		int xMin = 0;
		int xMax = 0;
		int yMin = 0;
		int yMax = 0;
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// we do a bit of parsing
				String[] words = st.split(" ");
				// yes i know i should use a regex
				// no i will not use a regex
				String[] xLine = words[2].split("\\.\\.");
				xMin = Integer.parseInt(xLine[0].substring(2));
				xMax = Integer.parseInt(xLine[1].substring(0, xLine[1].length() - 1));
				String[] yLine = words[3].split("\\.\\.");
				yMin = Integer.parseInt(yLine[0].substring(2));
				yMax = Integer.parseInt(yLine[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// idea is to iterate through the velocities for each direction
		// separately, and keep track of the number of moves it takes to 
		// get into the good zone
		// then do a dot product between the two
		HashMap<Integer, HashSet<Integer>> xVels = new HashMap<Integer, HashSet<Integer>>();
		HashMap<Integer, HashSet<Integer>> yVels = new HashMap<Integer, HashSet<Integer>>();
		int pMin = -1 * yMax;
		int pMax = -1 * yMin;
		// start with y to find max number of turns
		// max initial yVel downwards is yMin
		// corresponds to -1 * (yMin - 1) upwards
		// assume that yMin and yMax are negative
		// this is also horribly inefficient compared to using good
		// math but i'm far too lazy
		for (int yStart = 1; yStart <= pMax; yStart++) {
			int y = 0;
			int inc = yStart;
			while (y <= pMax) {
				if (y >= pMin) {
					int numTurns = inc - yStart;
					if (!yVels.containsKey(numTurns)) {
						yVels.put(numTurns, new HashSet<Integer>());
						xVels.put(numTurns, new HashSet<Integer>());
					}
					yVels.get(numTurns).add(yStart);
					// if initial y velocity goes up instead of down
					int altNumTurns = inc + yStart - 1;
					if (!yVels.containsKey(altNumTurns)) {
						yVels.put(altNumTurns, new HashSet<Integer>());
						xVels.put(altNumTurns, new HashSet<Integer>());
					}
					yVels.get(altNumTurns).add((yStart - 1) * -1);
				}
				y += inc;
				inc++;
			}
		}
		// now do stuff with x
		// first deal with triangular numbers
		int x = 1;
		int inc = 2;
		while (x <= xMax) {
			if (x >= xMin) {
				// then triangular number is in range so we have to
				// increment all of the ones >= inc - 1
				for (Integer i : xVels.keySet()) {
					if (i >= inc - 1) {
						xVels.get(i).add(inc - 1);
					}
				}
			}
			x += inc;
			inc++;
		}
		// now deal with nontriangular
		for (int xStart = 2; xStart <= xMax; xStart++) {
			x = 0;
			inc = xStart;
			while (x <= xMax) {
				if (x >= xMin) {
					int numTurns = inc - xStart;
					if (xVels.containsKey(numTurns)) {
						xVels.get(numTurns).add(inc - 1);
					}
				}
				x += inc;
				inc++;
			}
		}
		// finally iterate through and combine
		HashMap<Integer, HashSet<Integer>> valid = new HashMap<Integer, HashSet<Integer>>();
		for (int i : xVels.keySet()) {
			for (int xSpeed : xVels.get(i)) {
				for (int ySpeed : yVels.get(i)) {
					if (!valid.containsKey(xSpeed)) {
						valid.put(xSpeed, new HashSet<Integer>());
					}
					valid.get(xSpeed).add(ySpeed);
				}
			}
		}
		for (int i : valid.keySet()) {
			ans += valid.get(i).size();
		}
		return ans;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
