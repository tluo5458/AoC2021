public class D17P1 {
	public static int ans() {
		// the answer is just 114 * 115 / 2 lol
		// notice that there exists a triangular number between 207 and
		// 263, so we can make it so that y is high enough that x speed
		// eventually reaches 0
		// then, notice that whatever y speed we choose, there will be
		// a time where the probe hits y = 0, and then the next step, 
		// the probe will move (initial y velocity + 1) downwards
		// so thus the best choice is (x velocity which corresponds to
		// a triangular number between 207 and 263), (y velocity = 114)
		// then max y height is 1 + 2 + ... + 114 = 114 * 115 / 2 = 6555.
		return 6555;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
