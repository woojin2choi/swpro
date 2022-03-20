package beginner.day01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution01 {

	static int[] primes;
	
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String testCase = br.readLine();
		
		getPrimes(100000);
		
		
		for(int t = 0; t<Integer.parseInt(testCase); t++) {
			int result = 0;
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			for(int i=a; i<=b; i++) {
				if(primes[i]==0) {
					if(isPalindrom(i+"")) {
						result++;
					}
				}
			}
			System.out.println("#"+(t+1)+" "+result);
			
		}
		
	}
	
	public static void getPrimes(int number) {
		primes = new int[number+1];
		primes[0] = 1;
		primes[1] = 1;
		
		for(int i=2; i<=Math.sqrt(number); i++) {
			if(primes[i] == 1) {
				
			} else {
				for(int j=i; j<=number; j++) {
					if(j==i) {
						
					} else {
						if(j%i==0) {
							primes[j] = 1;
						}
					}
				}
			}
		}
	}
	
	public static boolean isPalindrom(String test) {
		char[] temp = test.toCharArray();
		int left = 0;
		int right = temp.length-1;
		
		for(int i=0; i<temp.length/2; i++) {
			if(temp[left] != temp[right]) {
				return false;
			} else {
				left++;
				right--;
			}
		}
		
		return true;
	}
}
