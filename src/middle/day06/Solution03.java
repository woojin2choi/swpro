package middle.day06;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * [교육P-0007] 군사 도로망
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVioru3AkLGojUH_&_menuId=AVUU732mAAHBC0c9&_menuF=true#
 * @author woojin2.choi
 * @hint kruskal 알고리즘을 이용한다, 기본적으로 크루스칼 알고리즘은 union-find를 기반으로 동작한다
 */
public class Solution03 {
	
	private static int T;
	private static int N;
	private static int M;
	private static int K;

	private static List<Edge> edges;
	private static int[] parent;
	
	private static class Edge{
		public int start;
		public int end;
		public int cost;
		
		Edge(int start, int end, int cost){
			this.start = start;
			this.end = end;
			this.cost = cost;
		}
	}
	
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int i = 1; i <= T; i++) {
			
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			// 간선리스트 생성
			edges = new ArrayList<>();	//
			
			
			// union-find를 위한 부모배열 초기화(자기자신)
			parent = new int[N+1];
			for(int j=0; j<=N; j++) {
				parent[j] = j;
			}
			
			long temp = 0;
			for(int j=0; j<M; j++) {
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());	// start
				int e = Integer.parseInt(st.nextToken());	// end
				int c = Integer.parseInt(st.nextToken());	// cost
				edges.add(new Edge(s, e, c*-1));	//이미 건설된 애들은 음수로 초기화 -> 정렬하게되면 -가 큰애들이 먼저 union이 되므로 -1 이런애들이 뒤로 밀린다 -> 적은비용철거
				temp += c;
			}
			
			for(int j=0; j<K; j++) {
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());	// start
				int e = Integer.parseInt(st.nextToken());	// end
				int c = Integer.parseInt(st.nextToken());	// cost
				edges.add(new Edge(s, e, c));
			}
			
			//음수포함해서 정렬하면, find로 찾았을때 true인경우에는 파괴되어야할 애들이다
			Collections.sort(edges, new Comparator<Edge>() {
				@Override
				public int compare(Edge o1, Edge o2) {
					return o1.cost-o2.cost;
				}
			});
			
			
			long res = 0;
			int cnt=0;
			for(int j=0; j<edges.size(); j++) {
				if(cnt==N-1) {
					break;
				}
				if(find(edges.get(j).start, edges.get(j).end)) {
					
				} else {
					union(edges.get(j).start, edges.get(j).end);
					res += edges.get(j).cost;
					cnt++;
				}
			}
			
			res = temp+res;
			
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
		
		if(x<y) {
			parent[y] = parent[x];
		} else {
			parent[x] = parent[y];
		}
		
	}
	
	private static boolean find(int a, int b) {
		int x = getParent(a);
		int y = getParent(b);
		return x==y;
	}
	
	// 부모를 가져오는데, 부모 가져오면서 부모의 부모를 찾아 업데이트 해둔다
	private static int getParent(int x) {
		if(parent[x]==x) {
			return x;
		}
		return parent[x] = getParent(parent[x]);
	}
}





/*
 * 최소신장트리 -> 모든 정점을 트리식으로(싸이클이없는) 연결할때, 최소의 간선 cost로 구해보는것
 * 예를들어, 모든 컴퓨터 네트워크를 만들고싶은데, 랜선의 비용이 있고, 이걸 최소비용(요게핵심)으로 하고싶다
 * 이럴때 MST를 사용하면 됨(미니멈 스패닝 트리)
 * cost순으로 간선의 리스트를 정렬하고나서 union-find를 하되, 간선을 N-1개를 선택하고 끝내면 된다.
 * 미니멈 스패닝 트리를 만드는 알고리즘이 크루스칼 알고리즘이다 
*/
