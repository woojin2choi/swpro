package advanced.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * [교육P-0009] 최단 경로
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVior5LwkNmojUH_&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 그냥 평범한 다익스트라
 */
public class Solution01 {
	
	private static int T;
	private static int N;
	private static int M;
	
	private static ArrayList<Node>[] adjList;
	private static int visited[];
	
	private static class Node{
		int node;
		int cost;
		Node(int node, int cost){
			this.node = node;
			this.cost = cost;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());	//도시의 수
			M = Integer.parseInt(st.nextToken());	//도로의 수
			
			//간선리스트를 생성한다, 노드의 갯수만큼 생성해서 adjList[s].get(i)는 s에서 시작하는 간선들이 i개 있다, 그때 각 노드에서 코스트가 있음
			adjList = new ArrayList[N+1];
			for(int i=1; i<=N; i++) {
				adjList[i] = new ArrayList<>();
			}
			
			//M개의 간선이 입력된다
			for(int i=1; i<=M; i++) {
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());	//시작
				int e = Integer.parseInt(st.nextToken());	//도착
				int c = Integer.parseInt(st.nextToken());	//간선cost
				adjList[s].add(new Node(e, c));
				adjList[e].add(new Node(s, c));	//양방향 그래프임
			}
			
			visited = new int[N+1];
			
			int res = dijkstra(1, N);	// 1번에서 N으로 가는 최소비용은 얼마인가
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(res));
			bw.newLine();
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
	
	private static int dijkstra(int S, int D) {
		int ans=-1;	//갈수없으면 -1로 출력한다
		
		PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o1.cost - o2.cost;	//비용이 적은순서로 min heap을 구성
			}
		});
		
		//최초 시작점인 S와 최초 코스트인 0으로 세팅한 다음에 dijkstra를 진행한다
		pq.add(new Node(S, 0));
		visited[S]=1;
		
		while(!pq.isEmpty()) {
			Node current = pq.poll();
			int currentNode = current.node;
			int currentCost = current.cost;
			visited[currentNode]=1;	//뽑는순간 방문한것임
			
			if(visited[currentNode] > 1)	//한번이상 들어온것은 최소값이 아니므로 패스한다
                continue;
			
			if(currentNode == D && visited[currentNode]==1) {
				ans = current.cost;
				break;
			}
			
			for(int i=0; i<adjList[currentNode].size(); i++) {
				int nextNode = adjList[currentNode].get(i).node;
				int nextCost = adjList[currentNode].get(i).cost;
				
				if(visited[nextNode] == 0) {	//방문하지 않은 곳이면 큐에삽입한다.
					pq.add(new Node(nextNode, currentCost+nextCost));
				}
			}
			
		}
		
		return ans;
	}

}
