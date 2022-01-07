import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class D16P1 {
	private static class Pair {
		int l;
		int r;
		
		Pair(int left, int right) {
			l = left;
			r = right;
		}
	}
	
	public static int binToInt(String s) {
		int ret = 0;
		int add = 1;
		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) == '1') {
				ret += add;
			}
			add *= 2;
		}
		return ret;
	}
	
	// returns pair, L is the sum, R is the length of the first packet
	public static Pair packetSum(String s) {
		int v = binToInt(s.substring(0, 3));
		int t = binToInt(s.substring(3, 6));
		if (t == 4) {
			// literal value
			int ind = 6;
			while (s.charAt(ind) != '0') {
				ind += 5;
			}
			return new Pair(v, ind + 5);
		} else {
			// operator
			int ret = v;
			int id = s.charAt(6) - '0';
			if (id == 0) {
				// id type = bits
				int bits = binToInt(s.substring(7, 22));
				int soFar = 0;
				while (soFar < bits) {
					Pair curr = packetSum(s.substring(22 + soFar));
					ret += curr.l;
					soFar += curr.r;
				}
				return new Pair(ret, bits + 22);
			} else {
				// id type = num packets
				int packets = binToInt(s.substring(7, 18));
				int soFar = 0;
				for (int i = 0; i < packets; i++) {
					Pair curr = packetSum(s.substring(18 + soFar));
					ret += curr.l;
					soFar += curr.r;
				}
				return new Pair(ret, soFar + 18);
			}
		}
	}
	
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
	
	public static int ans() {
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
		return packetSum(bin).l;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
