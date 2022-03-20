package middle.day08;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * [연습P-0012] 파티 참석하기
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVdKhCFQdAaojUEZ&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 다익스트라 알고리즘을 알고있는가? 기본으로 다익스트라는 시작점에서 모든 노드까지의 최단거리이다 1:N
 *       X에 모인다는것? -> 역방향으로 간선을 돌리고, 다익스트라를 통해서 X에 모일때의 최단거리를 노드별로 구할수있다.
 *       X에서 다시 기숙사로 간다는것? -> 정방향의 다익스트라를 통해서 N개의 노드로부터 X까지의 최단거리를 구하는것과 같다.
 */
public class Solution01 {
	
	private static int T;
	private static int N;	//학생수 -> V
	private static int M;	//길의수 -> E
	private static int X;	//파티를 여는 기숙사 학생의 번호 -> 도착점이라는 소리
	private static List<Node>[] adjList;
	private static List<Node>[] adjListB;
	private static int[] dist;
	
	private static class Node{
		public int node;
		public int cost;
		
		Node(int node, int cost){
			this.node = node;
			this.cost = cost;
		}
	}
	
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc<=T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());
			
			
			adjList = new ArrayList[N+1];
			adjListB = new ArrayList[N+1];
			for (int i=1; i<=N; i++) {
				adjList[i] = new ArrayList<Node>();
				adjListB[i] = new ArrayList<Node>();
			}
			
			
			for(int i=1; i<=M; i++) {
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());
				int e = Integer.parseInt(st.nextToken());
				int t = Integer.parseInt(st.nextToken());
				adjList[s].add(new Node(e, t));
				adjListB[e].add(new Node(s, t));
			}
			
			long res = 0;
			
			int[] a = dijkstra(X, adjListB);
			int[] b = dijkstra(X, adjList);
			for(int i=1; i<=N; i++) {
				a[i] += b[i];
			}
			Arrays.sort(a);
			res = a[a.length-1];
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(res));
			bw.newLine();
			
		}
		bw.flush();
		bw.close();
		br.close();  
	}
	
	private static int[] dijkstra(int S, List<Node>[] adjList) {
		dist = new int[N+1];
		// 최초는 무한대로 초기화한다 
		for(int i=1; i<=N; i++) {
			dist[i] = Integer.MAX_VALUE;
		}
		dist[S] = 0; 	//출발점은 0으로 시작한다.
		
		PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o1.cost - o2.cost;
			}
		});
		pq.add(new Node(S, dist[S]));
		
		while(!pq.isEmpty()) {
			// 큐에서 뽑히는 순간 cost가 최단거리이다, 우선순위큐에서 이미 cost로 정렬했으니까
			Node current = pq.poll();
			int currentNode = current.node;
			
			if(current.cost > dist[current.node]) {	//dist[current.node]는 작은값을 계속 갱신하는값이니까 현재코스트가 더 크면 볼필요도 없음
				continue;
			}
			
			// 도착점에 왔을때 그만하고 싶은경우
			//if (currentNode == destinationNode) {
			//	break;
			//}
			
			for(int i=0; i<adjList[currentNode].size(); i++) {	//나와 연결된 애들을 볼껀데
				int nextNode = adjList[currentNode].get(i).node;	//현재 노드와 연결된 노드들을 방문할예정
				int nextCost = adjList[currentNode].get(i).cost;	//현재 노드와 연결된 노드와의 간선비용
				
				if(dist[nextNode] > dist[currentNode]+nextCost) {	//더 단거리인 경우에 값을 갱신한다.
					dist[nextNode] = dist[currentNode] + nextCost;
					pq.offer(new Node(nextNode, dist[nextNode]));	//더 단거리가 갱신되었으니 큐에 넣고 돌린다
				}
			}
		}
		return dist;
	}
}



/*
 * 1. 출발 노드를 설정
 * 2. 최단거리 테이블을 초기화(dist배열)
 * 3. 방문하지 않은 노드 중에서 최단거리가 가장 짧은 노드를 선택
 * 4. 해당 노드를 거쳐 가른 노드로 가는 비용을 계산하여 최단거리 테이블을 갱신
 * 5. 3~4번 반복
 * 
 * 최초 출발점의 D[S]=0으로 세팅한다 -> 최소 힙(우선순위 큐)에 출발점 S를 삽입
 * 방문하지 않은 정점 중에서 D[K]가 최소인 정점 I 선택 -> 최소 힙(우선순위 큐)에서 맨 위에있는 정점 I를 꺼냄
 * D[j] = D[i] + W로 갱신 -> 최소힙(우선순위 큐)에 정점 J 삽입
 * 우선순위 큐에서 뽑는다는것은 그 정점은 최단거리가 확정이 되었단ㄴ것이다, 이후에 우선순위 큐에서 나오더라도 최단거리가 아니라서 무시하면 된다.
 */