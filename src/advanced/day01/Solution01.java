package advanced.day01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [기출P-0091] [2021년 02월 07일] 디자이너의 고민
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AXd-v_D2hB7BC0Gg&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint DP문제이고 타일링 DP에서 2개의 숫자를 반드시 포함해야하는 2개의 차원을 확장하여 점화식을 세운다
 */
public class Solution01 {
	
	private static int T;
	private static int N;
	private static int K;
	private static int[] A;
	private static long[][] DP;
	private static int p1,p2;
	
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc<=T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			DP = new long[N+1][4];
			DP[0][0] = 1;
			
			// A배열 초기화
			A = new int[K+1];
			st = new StringTokenizer(br.readLine());
			for(int i=1; i<=K; i++) {
				A[i] = Integer.parseInt(st.nextToken());
			}
			
			st = new StringTokenizer(br.readLine());
			p1 = A[Integer.parseInt(st.nextToken())];	// 반드시 포함되어야하는 수 1
			p2 = A[Integer.parseInt(st.nextToken())];	// 반드시 포함되어야하는 수 2
			
			for(int i=1; i<=N; i++) {
				for(int j=1; j<=K; j++) {
					if(i-A[j] >=0 && i-A[j]<=N) {
						if(A[j] != p1 && A[j] != p2) {
							DP[i][0] = (DP[i][0] + DP[i-A[j]][0])%1000000007;
							DP[i][1] = (DP[i][1] + DP[i-A[j]][1])%1000000007;
							DP[i][2] = (DP[i][2] + DP[i-A[j]][2])%1000000007;
							DP[i][3] = (DP[i][3] + DP[i-A[j]][3])%1000000007;
						}
						if (A[j] == p1) {
							DP[i][1] = (DP[i][1] + DP[i-A[j]][0])%1000000007;
							DP[i][1] = (DP[i][1] + DP[i-A[j]][1])%1000000007;
							DP[i][3] = (DP[i][3] + DP[i-A[j]][2])%1000000007;
							DP[i][3] = (DP[i][3] + DP[i-A[j]][3])%1000000007;
						}
						if (A[j] == p2) {
							DP[i][2] = (DP[i][2] + DP[i-A[j]][0])%1000000007;
							DP[i][2] = (DP[i][2] + DP[i-A[j]][2])%1000000007;
							DP[i][3] = (DP[i][3] + DP[i-A[j]][1])%1000000007;
							DP[i][3] = (DP[i][3] + DP[i-A[j]][3])%1000000007;
						}
						
					}
				}
			}
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(DP[N][3]));
			bw.newLine();
			
		}
		bw.flush();
		bw.close();
		br.close();  
	}
	
}

/*
 * 타일링 DP의 기본적인 점화식을 세워보자.
 * DP[i+A[k]] = DP[i]+1 맞나 모르겠음
 * 아무튼 이 기본적인 DP에서 2개의 타일을 반드시 포함해야 하는 것을 구해야하므로, 차원을 더 확장시켜서 점화식을 생각해보자
 * DP[i][][]의 점화식을 세우면 될것이다. 
 */
