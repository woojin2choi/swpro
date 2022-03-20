package beginner.day02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution04 {
	
	static int[] catType;
	static ArrayList<Integer>[] edge;
	static boolean[] visited;
	static int max;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<T; i++) {
			st = new StringTokenizer(br.readLine());
			
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			catType = new int[N+1];
			edge = new ArrayList[N+1];
			max=0;
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=N; j++) {
				catType[j] = Integer.parseInt(st.nextToken());
			}
			for (int j = 0; j < N+1; j++) {
				edge[j] = new ArrayList<>();
            }
			
			for(int j=0; j<M; j++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				if((catType[a]==0 && catType[b]==1) || (catType[a]==1 && catType[b]==0)) {
					
				} else {
					edge[a].add(b);
					edge[b].add(a);
				}
			}
			
			for(int j=1; j<=N; j++) {
				visited = new boolean[N+1];
				dfs(j, 1);
				int cnt=0;
				for(int k=0; k<visited.length; k++) {
					if(visited[k]==true) {
						cnt++;
					}
				} 
				if(cnt>max)
					max = cnt;
			}
			
			System.out.println("#"+(i+1)+" "+max);
		}
	}
	
	public static void dfs(int node, int depth) {
		visited[node] = true;
	
		for(int next : edge[node]) {
			if(visited[next] == false) {
				dfs(next, depth+1);
			}
		}
	}

}
