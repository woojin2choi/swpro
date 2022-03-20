package beginner.day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution05 {
	
	static int N;
	static int M;
	static int S;
	static ArrayList<Integer>[] al;
	static int[] visited;
	
	public static void main(String args[]) throws IOException{
		
		BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(sb.readLine());
		
		int T = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<T; i++) {
			st = new StringTokenizer(sb.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			S = Integer.parseInt(st.nextToken());
			
			al = new ArrayList[N+1];
			for(int j=0; j<=N; j++) {
				al[j] = new ArrayList<>();
			}
			visited = new int[N+1];
			
			for(int j=0; j<M; j++) {
				st = new StringTokenizer(sb.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				al[from].add(to);
				al[to].add(from);
			}
			
			bfs(S);
			
			int result=0;
			for(int j=1; j<visited.length; j++) {
				if(visited[j]==0) {
					result = -1;
					break;
				}
				if(result<visited[j]) {
					result = visited[j];
				}
			}
			System.out.println("#"+(i+1)+" "+result);
		}
		
	}
	
	public static void bfs(int node) {
		Queue<Integer> q = new ArrayDeque<>();
		
		q.offer(node);
		visited[node] = 0;
		
		while(!q.isEmpty()) {
			int now = q.poll();
			for(int next : al[now]) {
				if(visited[next]==0) {
					q.offer(next);
					visited[next]=visited[now]+1;
				}
			}
		}
	}

}
