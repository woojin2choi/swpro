package middle.day09;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [교육A-0012] 보석 털이
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVmHH4Ngkb-ojUFy&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 여기서부터가 진짜 DP인가봄, 점화식을 세우라고함
 *       최대 값어치로 들어갈 수 있는 값을 저장하는 2차원 배열을 D[N][M]로 N은 보석의 번호이고 M은 보석의 무게이다 -> 왜지?
 *       보석의 번호별로 늘어나면서 최대값어치를 저장할껀데, i번째 보석을 담는경우 vs i번째 보석은 안담는경우 -> 승자의 값을 D[N][M]에 넣자(DP의 기본은 이번이 최대값이면 이전도 최대였을것이다) 
 */
public class Solution02 {
	
	private static int T;
	private static int N;
	private static int M;
	private static long[][] arr;	// (1 ≤ C[i] ≤ 10^9, 1 ≤ W[i] ≤ 10,000)
	private static long[][] DP;		// C의 값이 굉장히 커지니까 long으로 가자
	
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc<=T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());	// N개의 보석
			M = Integer.parseInt(st.nextToken());	// 최대가용무게 M
			
			arr = new long[N+1][2];
			
			for(int i=1; i<=N; i++) {
				st = new StringTokenizer(br.readLine());
				arr[i][0] = Integer.parseInt(st.nextToken());	// C[i]
				arr[i][1] = Integer.parseInt(st.nextToken());	// W[i]
			}
			
			DP = new long[N+1][M+1];	// 누적 값어치 최대값을 구해갈 DP배열이다
			
			calcDP();
			
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(DP[N][M]));
			bw.newLine();
			
		}
		bw.flush();
		bw.close();
		br.close();  
	}
	
	private static void calcDP() {
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=M; j++) {	// 현재 선택된 i보석의 무게만큼 빠진상태에서 (i-1까지 계산된 최대값+i의 보석가치)의 값이 i번째의 보석을 선택안한경우값보다 크면 업데이트한다.  
				if(j-arr[i][1] >=0) {	
					// 현재 보석의 무게가 가방의 크기보다 작은경우에는  -> j-arr[i][1] >=1
					// 현재보석 넣을자리가 비워진 공간의 최대값어치 값과 현재보석의 값어치값을 더한것이, -> DP[i-1][j-W[i]]+C[i]
					// 현재보석을 선택하지 않은것보다 크면 최대값을 갱신해준다 -> DP[i][j] = Math.max(DP[i-1][j], DP[i-1][j-W[i]]+C[i])
					DP[i][j] = Math.max(DP[i-1][j], DP[i-1][j-(int)arr[i][1]]+arr[i][0]);
				} else {
					DP[i][j] = DP[i-1][j];	
				}
			}
		}
	}
	
}

