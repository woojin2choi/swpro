package middle.day10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [교육P-0019] Matrix Chain Multiplication
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVi9-viw4LyojUH_&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 메모이제이션을 이용한 재귀호출방식으로 풀면 좀 쉽고, DP배열로 for문을 이용하려면 DP배열을 잘 쌓아야한다.
 *       메모이제이션은 DP[1,4] = DP[1,1]*DP[2,4] or DP[1,2]*DP[3,4] or DP[1,3]*DP[4,4] 이런식으로 가는데, 각 재귀점에서 캐싱을 이용해서
 *       재귀연산을 줄여준다
 */
public class Solution03 {
	
	private static int T;
	private static int n;		//행렬의 개수
	private static long[] P;	// 행렬곱의 순서저장하는 배열
	private static long[][] memo;	// 메모이제이션을 위한 배열
	
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc<=T; tc++) {
			n = Integer.parseInt(br.readLine());
			
			P = new long[n+2];
			memo = new long[n+1][n+1];
			
			st = new StringTokenizer(br.readLine());
			for(int i=1; i<=n+1; i++) {
				P[i] = Integer.parseInt(st.nextToken());
			}
			
			long res = getMCM(1, n);
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(res));
			bw.newLine();
			
		}
		bw.flush();
		bw.close();
		br.close();  
	}
	
	private static long getMCM(int s, int e) {
		if(s==e) {
			return 0;
		}
		
		if(memo[s][e] != 0) {
			return memo[s][e];
		}
		
		long res=Integer.MAX_VALUE;
		for(int k=s; k<e; k++) {
			long t = getMCM(s, k) + getMCM(k+1, e) + P[s]*P[k+1]*P[e+1];
			res = Math.min(t, res);
		}
		memo[s][e] = res;
		return res;
	}
}

/*
재귀호출의 재귀식을 구해봐야한다.
A1 = 10*100
A2 = 100*5
A3 = 5*50
A4 = 50*20
이렇게 가정하면
P배열은
P = {0,10,100,5,50,20}으로 두자

D[1,4] = MIN((D[1,1]과 D[2,4]) or (D[1,2]과 D[3,4]) or (D[1,3]과 D[4,4]))
D[1,1] + D[2,4] = D[1,1] + D[2,4] + P1*P2*P5 -> Ps*pk+1*pe
D[1,2] + D[3,4] = D[1,2] + D[3,4] + P1*P3*P5
D[1,3] + D[4,4] = D[1,3] + D[4,4] + P1*P4*P5
위의 식을 유도할 수 있으므로
K값이 증가함에 따라 재귀로 호출 후, 가장 작은 값을 리턴한다.
그런데 모든 재귀를 전부타게되면 계산량이 많아진다.
여기서 메모이제이션을 넣는다.

1. 기저조건 MCM(int s, int e)에서
   s == e 인경우에는 0값을 리턴하면 된다.
2. 메모이제이션
   memo[s][e]의 값이 있다면 그걸쓰자, 더이상 재귀를 타지말자
3. 메모한 값이 없으면 처음 구하는것이니까 for문 타면서 k값에 따라 자르면서 최소값을 구해보자

*/
