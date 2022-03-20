package middle.day01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * [기출A-0009] 정예 병사
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVX76xcAAJKojUEF&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint Array를 정렬하기 위해서 Comparable를 구현할 수 있는가? 정렬을 자신이 생각한대로 구현할 수 있어야한다
 */
public class Solution02 {

	static class sol implements Comparable<Object>{
		int att;
		int def;
		
		public sol(int att, int def){
			this.att = att;
			this.def = def;
		}

		@Override
		public int compareTo(Object o) {
			return this.att - ((sol)o).att;
		}
	}
	
	static int N;
	static int minVal;
	static int result;
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(sb.readLine());

		int T = Integer.parseInt(st.nextToken());

		for (int i = 0; i < T; i++) {
			st = new StringTokenizer(sb.readLine());
			N = Integer.parseInt(st.nextToken());
			ArrayList<sol> al = new ArrayList<>();
			
			
			for(int j=0; j<N; j++) {
				st = new StringTokenizer(sb.readLine());
				al.add(new sol(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
			}
			
			Collections.sort(al);
			
			result = 1;
			minVal = al.get(0).def;
			
			for(int j=1; j<al.size(); j++) {
				if(al.get(j).def < minVal) {
					result++;
					minVal = al.get(j).def;
				}
			}
			
			System.out.println("#"+(i+1)+" "+result);
		}

	}

}
