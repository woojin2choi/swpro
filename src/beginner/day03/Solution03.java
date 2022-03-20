package beginner.day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution03 {

	static int[] visited;
	static ArrayList<Integer>[] al;
	static StringBuilder sb1;
	static StringBuilder sb2;
	static Queue<Integer> q;
	static int V;
	static int E;
	static int S;
	
	public static void main(String[] args) throws IOException {

		BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(sb.readLine());
		
		int T = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<T; i++) {
			
			st = new StringTokenizer(sb.readLine());
			V = Integer.parseInt(st.nextToken());	// 정점의 갯수
			E = Integer.parseInt(st.nextToken());	// 간선의 갯수
			S = Integer.parseInt(st.nextToken());	// 시작노드
			
			
			al = new ArrayList[V+1];
			for (int j = 0; j<V+1; j++) {
                al[j] = new ArrayList<>();
            }
			
			for(int j=0; j<E; j++) {
				st = new StringTokenizer(sb.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				al[from].add(to);
				al[to].add(from);
			}
			
			for(int j=0; j<V; j++) {
				al[j].sort(null);		// 오름차순으로 정렬	
			}
			q = new ArrayDeque<>();
			
			
			visited = new int[V+1];
			sb1 = new StringBuilder();
			sb1.append("#"+(i+1));
			dfs(S);
			System.out.println(sb1);
			
			visited = new int[V+1];
			sb2 = new StringBuilder();
			bfs(S);
			System.out.println(sb2.substring(1, sb2.length()));
			
			
		}

	}
	
	static void dfs(int node) {
		visited[node] = 1;
		sb1.append(" "+node);
		for(int next : al[node]) {
			if(visited[next]==0) {
				dfs(next);
			}
		}
	}
	
	static void bfs(int node) {
		visited[node]=1;
		q.offer(node);
		int cnt=0;
		while(!q.isEmpty()) {
			if(cnt == V) break;
			int now = q.poll();
			
			cnt++;
			sb2.append(" "+now);
			for(int next : al[now]) {
				if(visited[next]==0) {
					q.offer(next);
					visited[next]=1;
				}
			}
		}
	}

}
