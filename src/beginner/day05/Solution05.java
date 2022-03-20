package beginner.day05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution05 {
	
	static int N;
	static int M;
	static int[][] map;
	static int[][] res;
	static int[] cache;
	
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int T = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<T; i++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			map = new int[N][M];
			res = new int[N][M];
			cache = new int[3];
			
			for(int j=0; j<N; j++) {
				st = new StringTokenizer(br.readLine());
				for(int k=0; k<M; k++) {
					map[j][k] = Integer.parseInt(st.nextToken());
				}
			}
			
			
			for(int j=0; j<N; j++) {
				for(int k=0; k<M; k++) {
					if(j==0 && k==0) {
						res[j][k] = map[j][k];
					}
					if(j==0 && k!=0) {
						res[j][k] = res[j][k-1]+map[j][k];
					}
					if(k==0 && j!=0) {
						res[j][k] = res[j-1][k]+map[j][k];
					}
					if(j!=0 && k!=0) {
						res[j][k] = Math.min(res[j][k-1], res[j-1][k])+map[j][k];
					}
				}
			}
			
			System.out.println("#"+(i+1)+" "+res[N-1][M-1]);
				
		}
		
	}
	
	
}
