package middle.day08;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * [연습P-0011] K번째 최단 경로
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVdFc4WQIqOojUEZ&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 다익스트라, 큐에서 N번째로 뽑히는것은 곧 N번째로 짧은 길이이다(우선순위 큐에서 정렬해서 뽑기때문에)
 *       답안은 long타입으로 해야 1개 틀린거까지 맞을수있다.
 *       큐에서 뽑혔을때 K번째 이상으로 뽑히는 경우에는 큐에다가 연결된 노드들은 넣는것을 삼가해라 -> 속도가 느려진다.
 *       그리고 종착지 노드 N이 K번 나와도 종료해라, 그래야 속도를 더 줄일수있다.
 */
public class Solution04 {
	
	private static int T;
	private static int N;	//정점의갯수 10만개니까 다익스트라로 풀자
	private static int M;	//간선의갯수
	private static int K;	//몇번째 최단경로인가?
	private static ArrayList<Node>[] adjList;
	private static int[] visited;
	
	private static class Node{
		public int node;	// 양방향 시작과 끝정점
		public long cost;	// cost
		
		Node(int node, long cost){
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
			K = Integer.parseInt(st.nextToken());
			
			adjList = new ArrayList[N+1];
			for(int i=1; i<=N; i++) {
				adjList[i] = new ArrayList<>();
			}
			
			
			for(int i=1; i<=M; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int z = Integer.parseInt(st.nextToken());
				adjList[x].add(new Node(y, z));	//양방향
				adjList[y].add(new Node(x, z));	//양방향
			}
			
			long res = dijkstra(1, N);
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(res));
			bw.newLine();
			
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	
	private static long dijkstra(int S, int D) {
		long ans = -1;
		visited = new int[N+1];
		
		PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return (int)o1.cost - (int)o2.cost;
			}
		});
		
		pq.offer(new Node(S, 0));
		
		while(!pq.isEmpty()) {
			// 다익스트라는 프라이어티큐에서 꺼내는순간 최소거리가 확정되는것을 이용하면, K번째로 짧은 경로를 구할 수 있다
			Node current = pq.poll();
			int currentNode = current.node;
			visited[currentNode]++;
			
			if(visited[currentNode] > K)
                continue;
			
			if(currentNode == N && visited[currentNode]==K) {
				ans = current.cost;
				break;
			}
			
			for(int i=0; i<adjList[currentNode].size(); i++) {
				int nextNode = adjList[currentNode].get(i).node;
				int nextCost = (int)adjList[currentNode].get(i).cost;
				
				if(visited[nextNode] < K) {
					pq.offer(new Node(nextNode, current.cost+nextCost));
				}
			}
		}
		return ans;
	}
}

