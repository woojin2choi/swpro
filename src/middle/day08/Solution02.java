package middle.day08;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 웜홀
 * 벨만포드
 */
public class Solution02 {
	
	private static int T;
	private static int N;
	private static int M;
	
	static int[] dist; // 최단거리 저장용 배열
	static ArrayList<Edge> edgeList; // 간선 리스트
	
	static class Edge {
		int s, e, c; // start, end ,cost

		public Edge(int s, int e, int c) {
			this.s = s;
			this.e = e;
			this.c = c;
		}
	}
	
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken()); // 정점의 개수
			M = Integer.parseInt(st.nextToken()); // 간선의 개수

			edgeList = new ArrayList<Edge>();

			dist = new int[N + 1];

			for (int i = 1; i <= N; i++) {
				dist[i] = Integer.MAX_VALUE;
			}

			int a = 0, b = 0, c = 0;

			for (int i = 1; i <= M; i++) {
				st = new StringTokenizer(br.readLine());
				a = Integer.parseInt(st.nextToken());
				b = Integer.parseInt(st.nextToken());
				c = Integer.parseInt(st.nextToken());

				edgeList.add(new Edge(a, b, c));
				edgeList.add(new Edge(b, a, c));
			}

			bellmanford(1);

			for (int i = 1; i <= N; i++) {
				System.out.println(dist[i] + " ");
			}
			
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	private static boolean bellmanford(int S) {

		dist[S] = 0;

		for (int k = 1; k <= N - 1; k++) {	// 정점의 개수 - 1만큼 반복

			for (int j = 0; j < edgeList.size(); j++) {
				Edge edge = edgeList.get(j);

				int s = edge.s;
				int e = edge.e;
				int cost = edge.c;

				if (dist[s] != Integer.MAX_VALUE && dist[e] > dist[s] + cost) {
					dist[e] = dist[s] + cost;
				}
			}

		}

		// 최단거리가 완성되어야 함

		// 음수 사이클 찾기
		for (int j = 0; j < edgeList.size(); j++) {
			Edge edge = edgeList.get(j);

			int s = edge.s;
			int e = edge.e;
			int cost = edge.c;

			if (dist[e] > dist[s] + cost) {
				return true;
			}
		}

		return false;
	}
}

/*
 * 기본적으로 다익스틀랑 같은 결과를 내는 알고리즘이지만, 간선에 음수가 있거나 음의 사이클 존재여부를 확인할 수 있을때 사용한다.
 * 벨만포드는 O(VE)로 다익스트라보다 느리니까 음수가 없거나 하던가, 정점갯수가 엄청많지 않은경우(다익스트라는 10만개도 가능하다) 사용한다.
 * 1. 출발 노드를 설정
 * 2. 최단거리 테이블을 초기화(dist배열)
 * 3. 다음의 과정을 N-1번 반복
 *    3-1. 전체 간선 E개를 하나씩 확인
 *    3-2. 각 간선을 거쳐 다른 노드로 가는 비용을 계산하여 최단거리 테이블을 갱신
 * 4. 음의 간선 순환을 체크하기 위해서 한번더 수행한다.
 * 
 *  for(int i=1; i<=N; i++){//노드갯수 -1개만큼 돌리고 1번 더 돌려서 음의 사이클이 있는지까지 검사할라고
 *  	for(int j=1; j<=E; j++){	//간선의 갯수만큼 돌린다. 즉 N*E가 된다.
 *  
 */


