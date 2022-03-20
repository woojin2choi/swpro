package beginner.day04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Solution03 {
	
	static int N;
	static int K;
	static int[] S;
	static HashSet<String> set;
	static int[] visited;
	
	public static void main(String args[]) throws IOException {

		BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(sb.readLine());

		int T = Integer.parseInt(st.nextToken());

		for (int i = 0; i < T; i++) {
			st = new StringTokenizer(sb.readLine());
			N = Integer.parseInt(st.nextToken());
			st = new StringTokenizer(sb.readLine());
			K = Integer.parseInt(st.nextToken());
			S = new int[N+1];
			set = new HashSet<>();
			visited = new int[N+1];
			
			for(int j=1; j<=N; j++) {
				st = new StringTokenizer(sb.readLine());
				S[j] = Integer.parseInt(st.nextToken());
			}
			
			backtrack(0, "");
			
			System.out.println("#"+(i+1)+" "+set.size());
		}
	}
	
	public static void backtrack(int depth, String numCards) {
		
		if(depth == K) {
			set.add(numCards);	//해쉬셋에서는 중복을 제거할 수 있다!! 이거 졸라 크다
			return;
		}
		
		for(int i=1; i<=N; i++) {
			if(visited[i] == 0) {
				visited[i] = 1;
				backtrack(depth+1, numCards+S[i]);
				visited[i] = 0;
			}
		}
	}

}
