package advanced.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * ½ÃÇè
 * @author woojin2.choi
 */
public class Solution04 {
	
	private static int T;
	

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			st = new StringTokenizer(br.readLine());
			
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(""));
			bw.newLine();
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
	

}
