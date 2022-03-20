package advanced.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [기출P-0008] 연방 
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVxCk7Mw2tmojUGq#
 * @author woojin2.choi
 * @hint union-find는 끊는것이 없다. 따라서 모든 간선정보랑 쿼리를 다 입력받고나서, 끊는것은 잇는것으로 역으로 생각하면 된다.
 *       1) 확인한다 2) 해지한다 3) 확인한다 를 
 *       3) 확인한다 2) 잇는다 1) 확인한다 순서로 유니온파인드를 사용하면 됩니다.
 */
public class Solution03 {
	
	private static int T;
	private static int N;
	private static int M;
	private static int Q;
	private static int[] parent;
	private static int[][] adjArray;
	private static int[][] querys;
	private static int[] ans;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			//부모배열을 초기화, 최초 부모는 자기자신을 가리키고 있어야한다.
			parent = new int[N+1];
			for(int i=1; i<=N; i++) {
				parent[i] = i;
			}
			
			//M개의 간선정보를 일단 저장
			adjArray = new int[M+1][3];
			
			//간선정보를 저장한다.
			for(int i=1; i<=M; i++) {
				st = new StringTokenizer(br.readLine());
				adjArray[i][0] = Integer.parseInt(st.nextToken());
				adjArray[i][1] = Integer.parseInt(st.nextToken());
				adjArray[i][2] = 1;	//이 간선은 일단 union할 간선이다, 0이면 union하지 않도록한다. 이 값은 Q에서 넣게된다
			}
			

			
			
			//질의정보 저장
			Q = Integer.parseInt(br.readLine());
			
			querys = new int[Q+1][3];
			for(int i=1; i<=Q; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b;
				int c;
				if(a==2) {	//연결되었는가?
					b = Integer.parseInt(st.nextToken());
					c = Integer.parseInt(st.nextToken());
					querys[i][0] = a;
					querys[i][1] = b;
					querys[i][2] = c;
				} else {	//끊어질곳, 반대로 말하면 나중에 이을곳
					b = Integer.parseInt(st.nextToken());
					querys[i][0] = a;
					querys[i][1] = b;
					adjArray[b][2] = 0;	//초기 union할때 해당 부분은 union하지 않도록 취함
				}
			}
			
			ans = new int[Q+1];
			
			for(int i=1; i<=M; i++) {
				if(adjArray[i][2] == 1) {	// 연결할 애들만 먼저 연결하고 쿼리를 돌면서 추가로 union하자
					union(adjArray[i][0], adjArray[i][1]);
				}
			}
			
			bw.append("#").append(String.valueOf(tc)).append(" ");
			// 쿼리를 역순으로 돌면서 값을 꺼꾸로 구하고, 끊는 명령이 나오면 union해준다
			for(int i=Q; i>=1; i--) {
				int a = querys[i][0];
				int b = querys[i][1];
				int c = querys[i][2];
				
				if(a==2) {
					ans[i] = find(b, c);
				} else {
					union(adjArray[b][0], adjArray[b][1]);
					ans[i] = -1;
				}
			}
			
			
			for(int i=1; i<=Q; i++) {
				if(ans[i] != -1) {
					bw.append(String.valueOf(ans[i]));
				}
			}
			bw.newLine();
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
	
	private static void union(int a, int b) {
		int x = getParent(a);
		int y = getParent(b);
		
		if(x<y) {
			parent[y] = parent[x];
		} else {
			parent[x] = parent[y];
		}
	}

	private static int getParent(int a) {
		if(parent[a] == a) {
			return a;
		}
		return parent[a] = getParent(parent[a]);	//경로압축
	}
	
	private static int find(int a, int b) {
		int res = 0;
		int x = getParent(a);
		int y = getParent(b);
		if(x==y) {
			res = 1;
		} else {
			res = 0;
		}
		return res;
	}
}
