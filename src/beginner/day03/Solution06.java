package beginner.day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution06 {
	
	static int N;
	static int M;
	static int K;
	static ArrayList<Integer>[] al;
	static int[] visited;
	static int[] dok;
	
	public static void main(String args[]) throws IOException{
		
		BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(sb.readLine());
		
		int T = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<T; i++) {
			st = new StringTokenizer(sb.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			al = new ArrayList[N+1];
			for(int j=0; j<=N; j++) {
				al[j] = new ArrayList<>();
			}
			visited = new int[N+1];
			
			st = new StringTokenizer(sb.readLine());
			dok = new int[K];
			for(int j=0; j<K; j++) {
				dok[j] = Integer.parseInt(st.nextToken());
			}
			
			for(int j=0; j<M; j++) {
				st = new StringTokenizer(sb.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				al[from].add(to);
				al[to].add(from);
			}
			
			for(int j=0; j<=N; j++) {
				al[j].sort(null);
			}
			
			bfs(dok);
			
			int section=0;
			int days=0;
			for(int j=1; j<visited.length; j++) {
				if(days<visited[j]) {
					days = visited[j];
					section = j;
				}
			}
			System.out.println("#"+(i+1)+" "+section+" "+(days-1));
		}
		
	}
	
	public static void bfs(int[] nodes) {
		Queue<Integer> q = new ArrayDeque<>();
		for(int node : nodes) {
			q.offer(node);
			visited[node] = 1;
		}
		
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
