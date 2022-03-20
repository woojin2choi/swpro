package middle.day07;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [기출A-0037] 핵심도시1
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AXAeBo1w7JyojUHL&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint dfs를 진행하면서 방문의 순서를 order라하고, dfs 진행 하위의 low값을 계산하여 작은지 큰지를 판별
 *       또는 dfs진행할때의 child값이 2보다 큰경우에도 단절점이 된다.
 */
public class Solution02 {
	
	private static int T;

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc<= T; tc++) {
			st = new StringTokenizer(br.readLine());
		}

	}
	
	/*
	static ArrayList<Integer>[] adjList; // 1 -> 2,3
	static int[] order; // DFS 정점 방문 순서 저장용
	static boolean[] isCutVertex; // 단절점 여부 저장용
	static int number; // 정점 방문 순서

	
	// * 7 7
	// * 1 4
	// * 4 5
	// * 5 1
	// * 1 6
	// * 6 7
	// * 2 7
	// * 7 3
	

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken()); // 정점 수
		int E = Integer.parseInt(st.nextToken()); // 간선 수

		adjList = new ArrayList[V + 1];
		order = new int[V + 1];
		isCutVertex = new boolean[V + 1];
		number = 1; // 정점 방문 순서는 1로 시작

		for (int i = 1; i <= V; i++)
			adjList[i] = new ArrayList();

		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			adjList[from].add(to);
			adjList[to].add(from);
		}

		dfs(1, true);

		int cnt = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= V; i++) {
			if (isCutVertex[i]) {
				sb.append(i).append(" ");
				cnt++;
			}
		}
		System.out.println(cnt + "\n" + sb);
	}

	// 단절점 탐색 : DFS Spanning Tree
	static int dfs(int here, boolean isRoot) {

		// DFS 탐색 순서 저장, low 의 초기값은 자기 자신의 order
		int min = order[here] = number++;

		int child = 0; // 자식 수 count

		for (int next : adjList[here]) {
			
			// * 만약 이미 DFS에서 탐색된 정점이라면
			// * 현재 정점의 방문순서와 탐색된 정점의 방문 순서 중
			// * min값(=low)을 찾는다.
			if (order[next] > 0) {
				min = Math.min(min, order[next]);
				continue;
			}

			child++;
			int low = dfs(next, false);

			
			// * 정점 A가 시작 정점(root)이 아닐 때
			// * A번 정점에서 자식 노드들이 정점 A를 거치지 않고
			// * 정점 A보다 빠른 방문번호를 가진 정점으로 갈 수 없다면 단절점이다.
			if (!isRoot && order[here] <= low)
				isCutVertex[here] = true;

			min = Math.min(min, low);
		}

		// * 정점 A가 시작 정점(root)일 때 자식 수가 2개 이상이면 단절점이다.
		if (isRoot)
			isCutVertex[here] = (child >= 2);

		return min;
	}
	*/

}

/*
 * 단절점은 dfs를 순회하면서 일단 order를 계산한다.
 * 
 *    2			6
 * 1     4  5  
 *    3			7
 *    
 * 이런경우에 dfs는 1>2>4>5>6>3 순서로 방문한다.
 * 1은 단절점이 아닌이유는 dfs기준으로 child수가 1이다 
 * 4는 단절점인 이유가 4의 child가 2이다 4에서 돌아왔다가 방문하지 않은 3을 방문했기때문에 child가 2인것이다.
 * dfs를 기준으로 자신보다 아래쪽으로 방문하는 노드들의 order값의 min값이 현재의 order값보다 작으면 단절점이 아니다 -> 이전의 노드로 갈 길이 있음을 뜻함
 * 
*/
