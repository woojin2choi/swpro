package middle.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 모의 테스트2
 * 시간 제한 : 16 개의 Test Input 입력시 C/C++ 1.5초 / Java 2초
 * 메모리 제한 : Stack : 1 Mbytes  /  Total : 256 Mbytes
 * 
 * [문제]
 * N개의 도시가 있고, M개의 도로가 있다. 하나의 도로는 두 도시 사이를 연결하며, 임의의 두 도시를 연결하는 도로가 여러 개 있을 수 있다. 
 * 도시에는 1부터 N까지 번호가 순서대로 매겨져있고, 도로마다 지나가는데 걸리는 시간이 주어진다.
 * 도시의 도로 정보가 주어졌을 때, 1번 도시에서 N번 도시로 가는 최소 시간을 프로그램을 작성하시오
 * 
 * [입력] 
 * 입력 파일에는 여러 테스트 케이스가 포함될 수 있다. 
 * 파일의 첫째 줄에 케이스의 개수 T가 주어지고, 이후 차례로 T개 테스트 케이스가 주어진다. (1 ≤ T ≤ 20) 
 * 각 케이스의 첫 줄에 도시의 수 N과 도로의 수 M이 공백으로 구분되어 주어진다. (1 ≤ N ≤ 100,000, 1 ≤ M ≤ 300,000)
 * 그리고 다음 M개의 줄에 각 도로에 대한 정보를 나타내는 세 정수가 공백으로 구분되어 주어진다. 
 * "a b c"라고 주어졌을 때 a번 도시와 b번 도시를 연결하는 도로가 존재하며, 그 도로를 지나는데 걸리는 시간이 c라는 것을 의미한다. 
 * (1 ≤ a, b ≤ N, 1 ≤ c ≤ 10,000, a ≠ b)
 * 
 * [출력] 
 * 각 테스트 케이스의 답을 순서대로 표준출력으로 출력하며, 각 케이스마다 줄의 시작에 “#x”를 출력하여야 한다. 
 * 이때 x는 케이스의 번호이다. 같은 줄에, 1번 도시에서 N번 도시로 가는 최소 시간을 출력한다. 
 * 만약 1번 도시에서 N번 도시로 갈 수 없다면 -1을 출력한다.
 * 
 * [입출력 예]
 * (입력)
 * 2     ← 2 test cases in total
 * 3 3   ← 1st case
 * 1 2 3
 * 2 3 1
 * 1 3 2
 * 6 9   ← 2nd case
 * 1 2 2
 * 1 3 4
 * 2 3 1
 * 2 5 2
 * 2 4 4
 * 3 5 3
 * 4 5 3
 * 4 6 2
 * 5 6 2
 * 
 * (출력)
 * #1 2
 * #2 6
 * @author woojin2.choi
 * @hint 걍 다익스트라
 */
public class Solution02 {
	
	private static int T;
	private static int N;	//도시의 수
	private static int M;	//도로의 수
	private static int K=1;	//도시에는 한번만 도착하기
	private static ArrayList<Node>[] adjList;	//인접리스트(양방향)
	private static int[] visited;	//노드방문횟수, 1번만 방문하면 최단거리이다
	
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
			
			adjList = new ArrayList[N+1];	//인접리스트 생성
			for(int i=1; i<=N; i++) {
				adjList[i] = new ArrayList<>();
			}
			
			for(int i=1; i<=M; i++) {
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());
				int e = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				adjList[s].add(new Node(e, c));
				adjList[e].add(new Node(s, c));
			}	//인접리스트 세팅
			
			visited = new int[N+1];
			
			int res = dijkstra(1);
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(res));
			bw.newLine();
			
		}
		bw.flush();
		bw.close();
		br.close();  
	}
	
	private static int dijkstra(int s) {
		
		PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o1.cost - o2.cost;	// 간선의 cost로 정렬하면서 유지하도록 한다
			}
		});
		
		pq.add(new Node(s, 0));
		
		while(!pq.isEmpty()) {
			
			Node current = pq.poll();	//N번째 꺼내는순간 N번째 최소다
			int currentNode = current.node;
			
			visited[currentNode]++;
			
			if(visited[currentNode] > K) {	//1번 이상 뽑힌건 그냥 패쓰
				continue;
			}
			if(visited[currentNode] == K && currentNode == N) {	// 구하고싶은 N이 K번 뽑히면 출력
				return current.cost;
			}
			
			for(int i=0; i<adjList[currentNode].size(); i++) {
				int nextNode = adjList[currentNode].get(i).node;
				int nextCost = adjList[currentNode].get(i).cost;
				if(visited[nextNode] < K) {
					pq.add(new Node(nextNode, current.cost + nextCost));
				}
			}
		}
		return -1;
	}
	
}

