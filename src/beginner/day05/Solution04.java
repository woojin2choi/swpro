package beginner.day05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Solution04 {

	static int N;
	static int K;
	static ArrayList<block> blocks;
	static int[] result;
	
	public static class block implements Comparable<block>{
		int point;
		int num;
		
		block(int point, int num){
			this.point = point;
			this.num = num;
		}

		@Override
		public int compareTo(block o) {
			return o.point-this.point;
		}
	}
	
	public static void main(String args[]) throws IOException {
		BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(sb.readLine());

		int T = Integer.parseInt(st.nextToken());

		for (int i = 0; i < T; i++) {
			st = new StringTokenizer(sb.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			blocks = new ArrayList<>();
			result = new int[K+1];
			
			st = new StringTokenizer(sb.readLine());
			for(int j=0; j<N; j++) {
				blocks.add(new block(Integer.parseInt(st.nextToken()), 0));
			}
			st = new StringTokenizer(sb.readLine());
			for(int j=0; j<N; j++) {
				blocks.get(j).num = Integer.parseInt(st.nextToken());
			}
			
			Collections.sort(blocks);
			
			for(block b : blocks) {
				for(int j=b.num; j>=1; j--) {
					if(result[j] != 0) {
						continue;
					} else {
						result[j] = b.point;
						break;
					}
				}
			}
			
			
			int sum = 0;
			for(int j=1;j<result.length;j++) {
				sum += result[j];
			}
			StringBuilder sbr = new StringBuilder();
			sbr.append("#"+(i+1)+" "+sum);
			sbr.append("\n");
			System.out.println(sbr);
		}
	}
	
}
