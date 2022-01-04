import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class D03P2 {
	public static int ans() {
		File file = new File("inputs/d3.txt");
		
		ArrayList<String> oReads = new ArrayList<String>();
		ArrayList<String> cReads = new ArrayList<String>();
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// add each line to both arraylists
				oReads.add(st);
				cReads.add(st);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// filter each arraylist using bit criteria
		int currInd = 0;
		while (oReads.size() > 1) {
			// num 1's minus num 0's
			int diff = 0;
			for (String s : oReads) {
				if (s.charAt(currInd) == '1') {
					diff++;
				} else {
					diff--;
				}
			}
			// comp = the bit criterion
			char comp = diff >= 0 ? '1' : '0';
			for (int i = oReads.size() - 1; i >= 0; i--) {
				if (oReads.get(i).charAt(currInd) != comp) {
					oReads.remove(i);
				}
			}
			currInd++;
		}
		currInd = 0;
		while (cReads.size() > 1) {
			// num 1's minus num 0's
			int diff = 0;
			for (String s : cReads) {
				if (s.charAt(currInd) == '1') {
					diff++;
				} else {
					diff--;
				}
			}
			// comp = the bit criterion
			char comp = diff >= 0 ? '0' : '1';
			for (int i = cReads.size() - 1; i >= 0; i--) {
				if (cReads.get(i).charAt(currInd) != comp) {
					cReads.remove(i);
				}
			}
			currInd++;
		}
		String bestO = oReads.get(0);
		String bestC = cReads.get(0);
		
		// now convert the two generator ratings into ints
		int oGR = 0;
		int cGR = 0;
		int pow2 = 1;
		for (int i = bestO.length() - 1; i >= 0; i--) {
			if (bestO.charAt(i) == '1') {
				oGR += pow2;
			}
			if (bestC.charAt(i) == '1') {
				cGR += pow2;
			}
			pow2 *= 2;
		}
		System.out.println(bestO);
		System.out.println(bestC);
		return oGR * cGR;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
