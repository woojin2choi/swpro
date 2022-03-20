package beginner.day05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution06 {
	
	static int N;	// 컴퓨터개수
	static int M;	// 통신라인개수
	static ArrayList<Integer>[] al;
	static int cnt;
	
	static int[] visited;	
	static int[] result;
	
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int T = Integer.parseInt(st.nextToken());

		for (int i = 0; i < T; i++) {
			st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			al = new ArrayList[N+1];
			for(int j=0; j<N+1; j++) {
				al[j] = new ArrayList<>();
			}
			
			result = new int[N+1];
			
			for(int j=0; j<M; j++) {
				st = new StringTokenizer(br.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				al[from].add(to);
			}
			
			int res = 0; 
			// 1번 노드부터 마지막 노드까지 한번씩해보기
			for(int j=1; j<=N; j++) {
				visited = new int[N+1];
				cnt = 0;
				dfs(j);
				res = Math.max(cnt, res);
			}
			
			System.out.println("#"+(i+1)+" "+res);
		}
	}
	
	public static void dfs(int node) {
		
		visited[node] = 1;
		cnt++;
		
		for(int i=0; i<al[node].size(); i++) {
			if(visited[al[node].get(i)] == 0) {
				dfs(al[node].get(i));
			}
		}
	}
}
