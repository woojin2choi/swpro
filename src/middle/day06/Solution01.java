package middle.day06;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [연습P-0023] 동맹의 동맹은 동맹 
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AViVPEuwaJmojUH_&_menuId=AVUU732mAAHBC0c9&_menuF=true#
 * @author woojin2.choi
 * @hint 유니온 파인드 알고리즘을 알고있는지의 문제임, getParent에서 경로압축을 해서 find할것(실제 질의 할때 두 숫자가 union은 되어있지만 부모배열의 값은 다를수있으므로)
 *
 */
public class Solution01 {
	
	private static int T;
	private static int N;
	private static int Q;
	
	private static int[] parent;
	
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int i = 1; i <= T; i++) {
			N = Integer.parseInt(br.readLine());
			Q = Integer.parseInt(br.readLine());
			
			parent = new int[N+1];
			for(int j=0; j<=N; j++) {
				parent[j] = j;
			}
			
			int q, a, b, res=0;
			for(int j=1; j<=Q; j++) {
				st = new StringTokenizer(br.readLine());
				q = Integer.parseInt(st.nextToken());
				a = Integer.parseInt(st.nextToken());
				b = Integer.parseInt(st.nextToken());
				if(q==0) {
					union(a, b);
				} else {
					res += find(a, b);
				}
			}
			bw.append("#").append(String.valueOf(i)).append(" ").append(String.valueOf(res));
			bw.newLine();
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	
	private static void union(int a, int b) {
		int x = getParent(a);
		int y = getParent(b);
		
		// 작은 무보로 업데이트
		if(x<y) {
			parent[y] = parent[x];
		} else {
			parent[x] = parent[y];
		}
		
	}
	
	// union직전에 부모를 가져오면서 경로도 압축한다
	private static int getParent(int t) {
		if(parent[t] == t) {
			return t;
		}
		// 경로압축
		return parent[t] = getParent(parent[t]);
	}
	
	private static int find(int a, int b) {
		int res=0;
		int x = getParent(a);	//찾을때도 경로압축
		int y = getParent(b);	//찾을때도 경로압축
		if(x==y) {
			res = 1;
		} else {
			res = 0;
		}
		return res;
	}
	
	
}

/*
 * union-find
 * 1. 부모배열을 생성하고 자신은 자기 자신을 부모로 설정한다.(초기화)
 * 2. union시에는 두 수의 부모를 비교하여 작은숫자(또는 큰숫자)인 부모로 부모배열에 업데이트 한다 -> 같은 부모가 된다.
 * 3. 부모를 가져오는 getParent 메소드 호출시에는 경로압축방법을 사용한다(재귀를 통해 부모를 탐색한 최종결과로 부모배열을 업데이트)
 * 4. find시에도 부모를 가져오는 방식은 getParent를 이용해서 경로합축 해서 가져온 뒤에 무언가 비교를 해야한다
*/
