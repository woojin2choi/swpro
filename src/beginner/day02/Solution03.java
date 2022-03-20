package beginner.day02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 기출A-0023 대표선수 뽑기
 * URL : http://182.193.11.65/common/practice/problem/view.do?problemId=AWPxb1pAmlaojUGz&_menuId=AVUU732mAAHBC0c9&_menuF=true#
 * @author woojin2.choi
 *
 */
public class Solution03 {

	static boolean visited[];
	static int bad[];
	static int[] recommend;
	static int max;
	static int N;
	static int M;
	static int K;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<T; i++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(br.readLine());
			
			recommend = new int[N+1];
			bad = new int[N+1];
			max = 0;
			for(int j=1; j<=N; j++) {
				recommend[j] = Integer.parseInt(st.nextToken());
			}
			st = new StringTokenizer(br.readLine());
			while(st.hasMoreTokens()) {
				bad[Integer.parseInt(st.nextToken())] = 1;
			}
			
			for(int j=1; j<=N; j++) {
				if(K==0 && bad[j]==1) {
					continue;
				}
				visited = new boolean[N+1];
				dfs(j, 1, bad[j]);
			}
			
			System.out.println("#"+(i+1)+" "+max);
			
		}
	}
	
	public static void dfs(int node, int depth, int badPlayerCnt) {
		
		visited[node] = true;
		
		if(depth>max) {
			max = depth;
		}
		
		int next = recommend[node];
		if(visited[next]==false) {
			if(badPlayerCnt+bad[next]<=K) {
				dfs(next, depth+1, badPlayerCnt+bad[next]);
			}
		}
	}
	
}
