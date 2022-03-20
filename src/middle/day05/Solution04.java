package middle.day05;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [교육A-0005] 조합 
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVi99fqw3_mojUH_&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint nCk = nC(n-k), nCk = (n-1)C(k-1)+(n-1)Ck
 * @hint (a+b)^k을 전개했을때 a^(n-k)b^k의 항의 계수와도 같다 -> 이항계수(a를 n-k번 b를 k번 고른것)
 * @hint 확장된 유클리드 알고리즘을 통해서 두 수가 서로소인 임의의 수 p에 대한 1000000007의 역원을 구해서....
 * @hint mod를 써서 DP로 풀어도 됨 -> Solution05에 구현
 * @hint mod는 덧셈, 곱셈에서만 가능 
 * @hint 곱셈의 mod연산 합동을 만족시키기 위해서는 nCr의 n!/(k!(n-k)!)을 수식을 변형하여 (k!(n-k)!)의 역원에다가 mod를 가능하도록 한 뒤에 값을 출력하는것이다.
 */
public class Solution04 {
	
	private static int T;
	private static int n;
	private static int k;
	
	private static long nf=1; //n!
	private static long p=1;
	
	private static final int M = 1000000007;	// mod값

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine());
		
		for(int i=1; i<=T; i++) {
			
			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			k = Integer.parseInt(st.nextToken());
			
			// 초기화
			nf = 1;
			p = 1;
			/*
			 * 확장된 유클리드 알고리즘을 이용한 nCr의 모듈러 연산값을 구하는 방법
			 * 1.   nCr = n!/(k!(n-k)!) 이다
			 * 2.   양변에 모듈러 연산을 하고싶은데, 나눗셈에 대해서는 mod연산이 통하지 않는다
			 * 3.   nCr = n!*(k!(n-k)!)^-1 이렇게 곱셈에는 mod연산이 통한다.
			 * 4.   n!%M * (k!(n-k)!)^1%M 해주고 음수면 한번 더하면 된다.
			 * 5.   n!에 대해서는 n--하면서 mod를 계속해주면 된다.
			 * 6.   식이 복잡하니까 K!(n-k)!를 p로 두자
			 * 7.   우리가 구하고 싶은 식은 (n! * (k!(n-k)!)^-1) % 1000000007이다
			 * 7-1. 위의 식은 모듈러식이므로 모듈러연산의 역원을 구해야한다(모듈러인버스) -> p*x = 1(mod M)
			 * 7-2. 베주항등식? 으로 풀면 px+My=gcd(p,M) -> px%M + 0*y = 1 (p와 M은 서로소)
			 * 7-3. 즉 확장유클리드의 형태가 되었으니까 p와 M의 최대공약수가 1인 x와 y를 구하는것과 같은데, y는 구해도 의미가 없고, x를 구하면 되는것이다. 
			 * 8.   p값은 n과 k값이 주어지니까 n-- 또는 (n-k)-- 로 반복문을 돌면서 모듈러 연산을 하면서 누적하면 된다.
			 */
			
			// n! 하면서 mod처리하여 mod값을 계속 계산한다.
			for(int j=n; j>=1; j--) {
				nf *= j;
				nf %= M;
			}
			
			// p값을 구하는데 k에 mod하여 구하고 그리고나서 (n-k)!에도 mod한다.
			for(int j=k; j>=1; j--) {	// k!
				p *= j;
				p %= M;
			}
			for(int j=(n-k); j>=1; j--) {	//(n-k)!
				p *= j;
				p %= M;
			}
			
			// p를 위에서 구했으니, 이제는 마지막인 p의 역원에 모듈러?
			long[] temp = extendedEuclid(p, M);	//ㄷㄷㄷ
			
			// 마지막으로 n!과 역원에 대한 곱셈의 모듈러연산을 한번더하면 모듈러가 동작한다.
			long res = (nf*temp[0])%M;
			
			if(res<0) {	// 음수가 나오면 mod를 한번 더해주자
				res += M;
			}
			
			bw.append("#").append(String.valueOf(i)).append(" ").append(String.valueOf(res));
			bw.newLine();
			
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	
	//[확장된 유클리드 알고리즘] 
	private static long[] extendedEuclid(long a, long b) {
		long r1 = a, r2 = b;
		long s1 = 1, s2 = 0;
		long t1 = 0, t2 = 1; 
		long r, s, t, q, gcd; 
		
		while (r2>0) { 
			q = r1 / r2; 
			
			// gcd 계산
			r = r1 % r2; 
			r1 = r2; 
			r2 = r; 
			
			// s 계산 
			s = s1 - q * s2; 
			s1 = s2;
			s2 = s; 
			
			// t 계산
			t = t1 - q * t2; 
			t1 = t2;
			t2 = t; 
		}
		
		gcd = r1;
		s = s1;
		t = t1;
		
		return new long[] {s, t, gcd};
	}
}
