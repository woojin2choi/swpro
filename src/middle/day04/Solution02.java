package middle.day04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;


/**
 * [교육P-0005] 확장 유클리드 호제법
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AViormMQkJeojUH_&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 유클리드 호제법으로 GCD(a, b)를 구할 수 있다 -> 나머지로 이전의 제수를 계속 mod연산한다
 * @hint 확장된 유클리드 호제법을 통해서 as+bt=GCD(a, b)에 만족하는 s, t, gdc를 구할 수 있다. 이런문제는 안나올듯하고 gcd정도는 다른문제에 낑겨서 나올수도 있음
 */
public class Solution02 {
	
	private static int T;
	private static int x;
	private static int y;
	private static int z;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine());
		
		for(int i=1; i<=T; i++) {
			st = new StringTokenizer(br.readLine());
			
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			z = Integer.parseInt(st.nextToken());
			
			int[] res = extendedEuclid(x, y);
			if(z >= res[2] && z%res[2]==0) {
				if(z>res[2]) {
					res[0] = res[0]*(z/res[2]);
					res[1] = res[1]*(z/res[2]);
				}
				bw.append("#").append(String.valueOf(i)).append(" ").append(String.valueOf(res[0])).append(" ").append(String.valueOf(res[1]));
				bw.newLine();
			} else {
				bw.append("#").append(String.valueOf(i)).append(" ").append("-1");
				bw.newLine();
			}
			
			
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
	
	// [유클리드 호제법]
	// 큰수를 작은수로 mod연산
	// 나머지를 나눈수로 mod연산
	// 0이 나올때까지 하면 직전 mod연산자가 최대공약수임
	/* 1112와 695의 최대공약수(GCD)
	   1112 mod 695 = 417
       695  mod 417 = 278
       417  mod 278 = 139
       278  mod 139 = 0    => 139(GCD) */
	private static int euclid(int a, int b) {
		int maxVal = Math.max(a, b);
		int gcd;
		int res;
		int tmp;
		if(maxVal != a) {
			tmp=a; a=b; b=tmp;
		} 
		
		while(true) {
			res = a%b;
			if(res==0) {
				gcd = b;
				break;
			} else {
				a=b;
				b=res;
			}
		}
		return gcd;
	}
	
	/*
	[확장된 유클리드 알고리즘]
	as+bt=gcd(a,b)가 되는 정수 s,t가 있으며 구할수있다
	
	s와 t를 구하기 위해서는 초기값을 설정해주는데,
	s를 구하기 위해서는 s1 = 1, s2 = 0을 설정해 유클리드 알고리즘을 수행하고,
	t를 구하기 위해서는 t1 = 0, t2 = 1을 설정해 유클리드 알고리즘을 수행한다.
	그리고, s와 t의 계산에서는 r1 ÷ r2의 몫인 q를 사용한다.
	s = s1 - q x s2; 
	t = t1 - q x t2;
	로 계산해 나간다.
	*/
	private static int[] extendedEuclid(int a, int b) {
		int r1 = a, r2 = b;
		int s1 = 1, s2 = 0;
		int t1 = 0, t2 = 1; 
		int r, s, t, q, gcd; 
		
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
		
		s = s1;
		t = t1;
		gcd = r1;
		
		return new int[] {s, t, gcd};
	}
}
