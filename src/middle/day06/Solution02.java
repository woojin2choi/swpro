package middle.day06;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * [교육P-0008] 임계 경로
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVior0UgkM6ojUH_&_menuId=AVUU732mAAHBC0c9&_menuF=true#
 * @author woojin2.choi
 * @hint DAG그래프(위상그래프)의 위상정렬하는 방법을 알고있는가?, 그리고 indegree가 0이 되기 전에 다음번node까지 가는 cost와 현재까지 온 cost의 합한게
 *       이미 to노드에 있는 코스트보다 크면 업데이트 하는것이다, 누적해서 아직 방문하지 않은 노드인 to노드를 업데이트하면서 cost값을 계속 가지고 while문을 돌아간다.
 */
public class Solution02 {
	
	private static int T;
	private static int N;
	private static int M;
	
	private static ArrayList<TopoEdge>[] adj;
	private static int[] indegrees;

	private static class TopoEdge{
		public int end;
		public int cost;
		
		TopoEdge(int end, int cost){
			this.end = end;
			this.cost = cost;
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int i = 1; i <= T; i++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			// 간선의 정보를 저장하는 리스트 생성
			adj = new ArrayList[M+1];
			for(int j=0; j<adj.length; j++) {
				adj[j] = new ArrayList<>();
			}
			
			indegrees = new int[N+1];
			
			int s, e, c;
			// 간선의 정보를 저장하자
			for(int j=1; j<=M; j++) {
				st = new StringTokenizer(br.readLine());
				s = Integer.parseInt(st.nextToken());
				e = Integer.parseInt(st.nextToken());
				c = Integer.parseInt(st.nextToken());
				adj[s].add(new TopoEdge(e, c));
				indegrees[e]++;	//indegree값을 1씩 더해서 초기화시 계간값을 가지고 있자
			}
			
			long res=0;
			
			res = topologicalSort();
			
			bw.append("#").append(String.valueOf(i)).append(" ").append(String.valueOf(res));
			bw.newLine();
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	private static long topologicalSort() {	//위상정렬
		
		Deque<Integer> queue = new ArrayDeque<>();
		ArrayList<Integer> ordered = new ArrayList<>();
		int[] cost = new int[N+1];	//정점별로 인디그리가0일때 cost를 업데이트 하는 배열
		
		for(int i=1; i<=N; i++) {
			if(indegrees[i]==0) {
				queue.add(i);
			}
		}
		
		int now;
		while(!queue.isEmpty()) {
			now = queue.poll();
			
			// 위상정렬의 노드 방문순서를 저장한다
			ordered.add(now);
			
			for(int i=0; i<adj[now].size(); i++) {
				
				int to = adj[now].get(i).end;	//현재와 연결된 다음정점
				cost[to] = Math.max(cost[to], cost[now]+adj[now].get(i).cost);	//다음번 노드에다가 현재값을 더했는데 기존것보다 큰것을 넣어준다 -> ㄷㄷㄷㄷ
				indegrees[to]--;				//연결된 다음정점의 indegree값을 줄이고, 0이되면 큐에넣고 탐색한다
				if(indegrees[to] == 0) {
					queue.add(to);
				}
			}
		}
	
		return cost[N];
	}
	
}

/*
어떤일을 진행함에 있어서 여러갈래의 일들이 하나로 모여서 순서대로 일하는 방식을 찾는 정렬법임
스타크래프트의 빌드같은것, 공장의 조립공정 같이 어떤 갈래의 길이 끝나더라도 다른갈래의 길이 끝날때까지 기다렸다가 다 되면 진행하는..
순회방식은 최초에 indegree가 0인 모든 정점을 큐에 넣고, 
하니씩 뺀뒤에for문 돌면서  연결된 노드들의 indegree값을 1씩 줄여나간다, indegree값이 0인것들만 큐에 넣고 다시 돌린다.
*/
