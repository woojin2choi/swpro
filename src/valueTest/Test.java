package valueTest;

public class Test {

	public static void main(String[] args) {
		int temp = (int)Math.pow(2, (int)Math.sqrt(100000)+1);
		long temp1 = (long)Math.pow(2, (int)Math.sqrt(100000)+1);
		System.out.println(temp);
		System.out.println(temp1);
		//2147483647
		//2147483647
		System.out.println(Math.pow(2,  14));
	}
}
