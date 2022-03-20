package beginner.day04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 깊이 우선탐색과 거의 비슷
 * void backtrack(int parameter){
 * 	if(end_condition){
 *   check();
 *   retrun;
 *  }
 *  esle {
 *   while(possible){
 *    if(condition){
 *     backtrack(new_parameter);
 *    }
 *   }
 *  }
 * }
 * @author woojin2.choi
 *
 */
public class Solution01 {
	
	static int[][] s;
	static int answer;
	static int[] visited;
	static int N;
	
	public static void main(String args[]) throws IOException {

		BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(sb.readLine());

		int T = Integer.parseInt(st.nextToken());

		for (int i = 0; i < T; i++) {
			st = new StringTokenizer(sb.readLine());
			answer = 0;
			N = Integer.parseInt(st.nextToken());
			s = new int[N+1][N+1];
			visited = new int[N+1];
			
			for(int j=1; j<=N; j++) {
				st = new StringTokenizer(sb.readLine());
				for(int k=1; k<=N; k++) {
					s[j][k] = Integer.parseInt(st.nextToken());
				}
			}
			
			
			visited[0] = 1;
			backtrack(0, s[0][0]);
			
			
			System.out.println("#"+(i+1)+" "+answer);
		}
	}
	
	public static void backtrack(int depth, int point) {
		if(depth == N) {
			if(point > answer) {
				answer = point;
				return;
			}
		}
		
		for(int i=1; i<=N; i++) {
			if(visited[i] == 0) {
				visited[i] = 1;
				backtrack(depth+1, point+s[depth+1][i]);
				visited[i] = 0;
			}
		}
	}

}
