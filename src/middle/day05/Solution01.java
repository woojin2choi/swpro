package middle.day05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * [연습A-0033] 제품의 일련번호 1 
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AWkomsyQWXWojUFD&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 제품의 일련번호를 트리구조의 백트래킹을 통해 구하는 방법, 알파벳의 개수만큼 배열을 만들고, visited배열에서 해당 문자를 사용했는지 체크해둔다.
 * @hint 백트래킹 특성상 전부 다 찾아보는것인데, 시간이 오래걸리기 때문에 갯수를 전부 세었으면 더이상 재귀를 타지 않도록 return하는 기저조건이 필수가 된다. 
 */
public class Solution01 {
	
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
