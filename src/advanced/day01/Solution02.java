package advanced.day01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * [기출P-0089] [2021년 01월 30일] 위대한 항로 
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AXda99VGYnXBC0Gg
 * @author woojin2.choi
 * @hint 다익스트라를 이용해서 최단거리를 구한다. 섬에서 제공받는 식량이 음수처럼 활용되므로 역방향으로 변환하여 풀면 될 것이다.
 */
public class Solution02 {
	
	private static int T;
	private static int N;	//노드개수
	private static int M;	//간선개수
	private static int K;	//친구들섬나라개수
	private static ArrayList<Node>[] adjList;
	private static int[] food;
	private static int[] dist;
	
	private static class Node{
		int node;
		int cost;
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
			K = Integer.parseInt(st.nextToken());
			
			adjList = new ArrayList[N+1];
			for(int i=1; i<=N; i++) {
				adjList[i] = new ArrayList<Node>();
			}
			
			for(int i=1; i<=M; i++) {
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());
				int e = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				adjList[e].add(new Node(s, c));	//역방향 다익스트라를 위해서 start와 e를 바꿔서 넣는다
			}
			
			food = new int[N+1];
			for(int i=1; i<=K; i++) {
				st = new StringTokenizer(br.readLine());
				int island = Integer.parseInt(st.nextToken());
				int supply = Integer.parseInt(st.nextToken());
				food[island] = supply;
			}
			
			dist = new int[N+1];
			for(int i=1; i<=N; i++) {
				dist[i] = Integer.MAX_VALUE;
			}
			
			int res = dijkstra(N, 1);
			
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(res));
			bw.newLine();
			
		}
		bw.flush();
		bw.close();
		br.close();  
	}
	
	private static int dijkstra(int start, int end) {
		
		PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o1.cost - o2.cost;
			}
		});
		
		pq.add(new Node(N, 0));	//N번섬에서 시작하는 역방향이고 최초 cost는 0부터 시작한다
		dist[start] = 0;

		while(!pq.isEmpty()) {
			Node current = pq.poll();
			int currentNode = current.node;
			
			if (dist[currentNode] < current.cost) continue;
			
			for(int i=0; i<adjList[currentNode].size(); i++) {
				int nextNode = adjList[currentNode].get(i).node;
				int nextCost = adjList[currentNode].get(i).cost;
				
				//역으로 오면서 식빵이 있으면 다 먹는다, 대신 먹었는데 음수면 0으로세팅하고 다음으로 넘어갈 비용을 더해서 비교한다
				//두번째샘플에서 6-4-3-1로 가는게 덜 빠른이유가 4->1(10)->3 이면 최종으로 3이 드는데
				//6-4-2-1로 진행하면 4->1(5)->2 라서 최종으로 2가 드므로
				//cost값을 2에서 올때랑 3에서 올때를 비교하면 2<3이므로 2가 계속 남아있어야한다.
				int cost = Math.max(dist[currentNode]-food[currentNode], 0)+nextCost;
				
				if(cost < dist[nextNode]) {
					dist[nextNode] = cost;
					pq.add(new Node(nextNode, cost));
				}
				
			}
			
		}
		
		return dist[1];	
	}
}
