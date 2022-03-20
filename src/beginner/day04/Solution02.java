package beginner.day04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution02 {
	
	static int[] visited;
	static char[] S;
	static int N;
	static String scheck = "";
	static String echeck = "";
	static int cnt;
	static int start;
	static int end;
	
	public static void main(String args[]) throws IOException {

		BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(sb.readLine());

		int T = Integer.parseInt(st.nextToken());

		for (int i = 0; i < T; i++) {
			st = new StringTokenizer(sb.readLine());
			N = Integer.parseInt(st.nextToken());
			visited = new int[N+1];
			scheck = "";
			echeck = "";
			
			S = new char[N+1];
			for(int j=1; j<=N; j++) {
				S[j] = (char)(96+j);
			}
			scheck = st.nextToken();
			echeck = st.nextToken();
			
			if(scheck.compareTo(echeck)>0) {
				String temp = "";
				temp = scheck;
				scheck = echeck;
				echeck = temp;
			}
			
			cnt = 0;
			start = 0;
			end = 0;
			
			backtrack(0, "");
			int result=0;
			if(end>start) {
				result = end-start-1;
			} else {
				result = start-end-1;
			}
			
			System.out.println("#"+(i+1)+" "+result);
			
		}
	}
	
	public static void backtrack(int depth, String check) {
		
		// 여기만 가지치기를 해도 된다고??
		if(end>0) {
			return;
		}
		
		if(depth == N) {
			cnt++;
			if(check.equals(scheck)) {
				start = cnt;
			}
			if(check.equals(echeck)) {
				end = cnt;
			}
			return;
		}
		
		for(int i=1; i<=N; i++) {
			if(visited[i]==0) {
				visited[i] = 1;
				backtrack(depth+1, check+S[i]);
				visited[i] = 0;
			}
		}
		
	}
	

}
