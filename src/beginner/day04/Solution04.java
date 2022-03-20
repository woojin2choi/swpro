package beginner.day04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution04 {

	static int N;
	static int F;
	static int[][] node;
	static int hours;
	static int result;
	
	public static void main(String args[]) throws IOException {

		BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(sb.readLine());

		int T = Integer.parseInt(st.nextToken());

		for (int i = 0; i < T; i++) {
			st = new StringTokenizer(sb.readLine());
			N = Integer.parseInt(st.nextToken());
			F = Integer.parseInt(st.nextToken());
			node = new int[N+2][2]; // N+1는 도착점 0은 출발점
			node[0][0] = F;	//처음 타이어수명
			result = Integer.MAX_VALUE;
			
			for(int j=1; j<=N; j++) {
				st = new StringTokenizer(sb.readLine());
				node[j][0] = Integer.parseInt(st.nextToken()); // 타이어 수명
				node[j][1] = Integer.parseInt(st.nextToken()); // 교체 시간
			}
			
			backtrack(node[0][0], 0, 0);
			
			System.out.println("#"+(i+1)+" "+result);
		}
	}
	
	public static void backtrack(int dist, int hours, int whereAmI) {
		if(whereAmI >= N+1) {
			result = hours;
			return;
		}
		if(result<hours) {
			return;
		}
		
		for(int i=whereAmI+1; i<=whereAmI+dist; i++) {
			if(i<=N+1) {
				backtrack(node[i][0], hours+node[i][1], i);
			}
		}
	}
}
