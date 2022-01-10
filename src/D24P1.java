// all of my work was done by hand and written here
public class D24P1 {
/*
 * 0 0 0 0
 * inp w1
 * w1 1 w1+4 (w1 + 4)
 * inp w2
 * w2 1 w2+11 26(w1+4)+(w2+11)
 * inp w3
 * w3 1 w3+5 26^2(w1+4)+26(w2+11)+(w3+5)
 * inp w4
 * w4 1 w4+11 26^3(w1+4)+26^2(w2+11)+26(w3+5)+(w4+11)
 * inp w5
 * w5 1 w5+14 26^4(w1+4)+26^3(w2+11)+26^2(w3+5)+26(w4+11)+(w5+14)
 * inp w6
 * w6 w5+4 w5+14 26^3(w1+4)+26^2(w2+11)+26^1(w3+5)+(w4+11)
 * eql x w
 * BRANCH 1: w6 = w5 + 4
 * w6 0 0 26^3(w1+4)+26^2(w2+11)+26^1(w3+5)+(w4+11)
 * inp w7
 * w7 1 w7+11 26^4(w1+4)+26^3(w2+11)+26^2(w3+5)+26(w4+11)+(w7+11)
 * inp w8
 * w8 w7+2 w7+11 26^3(w1+4)+26^2(w2+11)+26(w3+5)+(w4+11)
 * BRANCH 1.1: w8 = w7 + 2
 * w8 0 0 26^3(w1+4)+26^2(w2+11)+26(w3+5)+(w4+11)
 * inp w9
 * w9 w4+8 0 26^2(w1+4)+26(w2+11)+(w3+5)
 * BRANCH 1.1.1: w9 = w4 + 8
 * w9 0 0 26^2(w1+4)+26(w2+11)+(w3+5)
 * inp w10
 * w10 1 w10+5 26^3(w1+4)+26^2(w2+11)+26(w3+5)+(w10+5)
 * inp w11
 * w11 w10 w10+5 26^2(w1+4)+26(w2+11)+(w3+5)
 * BRANCH 1.1.1.1: w11 = w10
 * w11 0 0 26^2(w1+4)+26(w2+11)+(w3+5)
 * inp w12
 * w12 w3-5 0 26(w1+4)+(w2+11)
 * BRANCH 1.1.1.1.1: w12 = w3 - 5
 * w12 0 0 26(w1+4)+(w2+11)
 * inp w13
 * w13 w2+7 0 (w1+4)
 * BRANCH 1.1.1.1.1.1: w13 = w2 + 7
 * w13 0 0 (w1+4)
 * inp w14
 * w14 w1-1 0 0
 * BRANCH 1.1.1.1.1.1.1: w14 = w1 - 1
 * w14 0 0 0
 * IT MUST TAKE THIS BRANCH 
 * because z must accumulate at least 7 digits
 * and the 1 branch = lose a digit, while the 2 branch = not lose a digit
 * so you want to lose all 7 digits to end with 0
 * THEREFORE:
 * the constraints are:
 * w1 - 1 = w14
 * w2 + 7 = w13
 * w3 - 5 = w12
 * w4 + 8 = w9
 * w5 + 4 = w6
 * w7 + 2 = w8
 * w10 = w11
 * 
 * clearly, the largest 14 digit number that satisfies this is\
 * 92915979999498,
 * which ends up being the correct answer.
 * 
 * these later branches are useless and were just there to keep track
 * while i was working
 * BRANCH 1.1.1.1.2: w12 != w3 - 5
 * 
 * BRANCH 1.1.1.2: w11 != w10
 * 
 * BRANCH 1.1.2: w9 != w4 + 8
 * 
 * BRANCH 1.2: w8 != w7 + 2
 * 
 * BRANCH 2: w6 != w5 + 4
 */
	public static long ans() {
		return 92915979999498L;
	}
	
	public static void main(String[] args) {
		System.out.println(ans());
	}
}
