import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class D16P2 {
	private static class Pair {
		long l;
		int r;
		
		Pair(long left, int right) {
			l = left;
			r = right;
		}
		
		public String toString() {
			return l + ", " + r;
		}
	}
	
	public static long binToLong(String s) {
		if (s.length() > 62) {
			System.out.println("Very long value");
		}
		long ret = 0;
		long add = 1;
		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) == '1') {
				ret += add;
			}
			add *= 2;
		}
		return ret;
	}
	
	// returns pair, L is the value, R is the length of the first packet
	public static Pair packetValue(String s) {
		int t = (int) binToLong(s.substring(3, 6));
		if (t == 4) {
			// literal value
			String litVal = "";
			int ind = 6;
			while (s.charAt(ind) != '0') {
				litVal += s.substring(ind + 1, ind + 5);
				ind += 5;
			}
			litVal += s.substring(ind + 1, ind + 5);
			return new Pair(binToLong(litVal), ind + 5);
		} else {
			// operator
			ArrayList<Long> vals = new ArrayList<Long>();
			int soFar = 0;
			int id = s.charAt(6) - '0';
			if (id == 0) {
				// id type = bits
				int bits = (int) binToLong(s.substring(7, 22));
				while (soFar < bits) {
					Pair curr = packetValue(s.substring(22 + soFar));
					vals.add(curr.l);
					soFar += curr.r;
				}
				soFar += 22;
			} else {
				// id type = num packets
				int packets = (int) binToLong(s.substring(7, 18));
				for (int i = 0; i < packets; i++) {
					Pair curr = packetValue(s.substring(18 + soFar));
					vals.add(curr.l);
					soFar += curr.r;
				}
				soFar += 18;
			}
			// now combine all of the values
			long ret = 0;
			if (t == 0) {
				// sum packet
				for (long i : vals) {
					ret += i;
				}
			} else if (t == 1) {
				// product packet
				ret = 1;
				for (long i : vals) {
					ret *= i;
				}
			} else if (t == 2) {
				// minimum packet
				ret = Long.MAX_VALUE;
				for (long i : vals) {
					ret = Math.min(ret, i);
				}
			} else if (t == 3) {
				// maximum packet
				ret = 0;
				for (long i : vals) {
					ret = Math.max(ret, i);
				}
			} else if (t == 5) {
				// greater than packet
				ret = vals.get(0) > vals.get(1) ? 1 : 0;
			} else if (t == 6) {
				// less than packet
				ret = vals.get(0) < vals.get(1) ? 1 : 0;
			} else if (t == 7) {
				// equal to packet
				// funny, i had a bug earlier where it used == instead
				// of .equals, but that always gave 0 because the 
				// arraylist has Long instead of long
				ret = vals.get(0).equals(vals.get(1)) ? 1 : 0;
			} else {
				System.out.println("Very very bad!!!!");
			}
			return new Pair(ret, soFar);
		}
	}
	
	// hexadecimal string to binary string
	public static String hexToBin(String s) {
		HashMap<Character, String> digit = new HashMap<Character, String>();
		digit.put('0', "0000");
		digit.put('1', "0001");
		digit.put('2', "0010");
		digit.put('3', "0011");
		digit.put('4', "0100");
		digit.put('5', "0101");
		digit.put('6', "0110");
		digit.put('7', "0111");
		digit.put('8', "1000");
		digit.put('9', "1001");
		digit.put('A', "1010");
		digit.put('B', "1011");
		digit.put('C', "1100");
		digit.put('D', "1101");
		digit.put('E', "1110");
		digit.put('F', "1111");
		String ret = "";
		for (char c : s.toCharArray()) {
			ret += digit.get(c);
		}
		return ret;
	}
	
	public static long ans() {
		File file = new File("inputs/d-16.txt");

		String in = "";
		BufferedReader br;
		String st;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				// we do a bit of parsing
				in = st;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// first, convert it into binary
		String bin = hexToBin(in);
		// now parse
		return packetValue(bin).l;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
