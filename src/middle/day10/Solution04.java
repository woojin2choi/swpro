package middle.day10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [기출P-0064] 군인숨기기
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AWsmc1Yg2uCojUFU&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 *
 */
public class Solution04 {
	
	private static int T;
	
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc<=T; tc++) {
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf("res"));
			bw.newLine();
			
		}
		bw.flush();
		bw.close();
		br.close();  
	}
	
	
}

