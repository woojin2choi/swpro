package beginner.day05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution07 {
	
	static int N;	//돌의갯수
	static Stone[] stones;
	static int[] visited;
	
	static class Stone{
		int h;	//높이
		int j;	//연료소모량
		
		Stone(int h, int j){
			this.h = h;
			this.j = j;
		}
	}
	
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int T = Integer.parseInt(st.nextToken());

		for (int i = 0; i < T; i++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			stones = new Stone[N+1];
			
			for(int j=1; j<=N; j++) {
				st = new StringTokenizer(br.readLine());
				stones[j] = new Stone(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			}
			
			
			
			System.out.println("#"+(i+1)+" ");
		}
	}
	

}
