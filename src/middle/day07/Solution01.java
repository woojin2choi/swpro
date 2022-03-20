package middle.day07;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * [교육P-0026] 최저 공통 조상
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVmF8smgebiojUFy&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint LCA알고리즘을 알고있는가?, shfit연산을 이해하는가?
 */
public class Solution01 {
	
	private static int T;
	private static int N;	//정점의 갯수
	private static int Q;	//질의의 수
	private static int[] depth;	//bfs용 visited배열
	private static ArrayList<Integer>[] adjList;
	private static int[][] parent;	//최저공통조상 LCA를 위한 parent[k][v] 배열, k는 2의 지수임
	private static final int MAX_K = 17;	//2^16 65536이고 2^17이 13....이니까 MAX_K값을 17로 잡고 LCA돌면서 "내려오자" 
	
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int i = 1; i <= T; i++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());	// N이 10만개가 최대이므로 1렬로 된 트리가 worst케이스인데 이러면 Max K값이 17을 잡으면 됨
			Q = Integer.parseInt(st.nextToken());
			
			adjList = new ArrayList[N + 1];
			for (int j=0; j<=N; j++) {
				adjList[j] = new ArrayList<>();
			}

			parent = new int[MAX_K+1][N+1]; 
			depth = new int[N+1];
			
			// 1. 먼저 입력을 받는다
			st = new StringTokenizer(br.readLine());
			for (int j=1; j<N; j++) {
				int a = Integer.parseInt(st.nextToken());
				adjList[a].add(j+1);	//부모 a에 연결된 정점들(j+1)들을 add하여 인접리스트를 만든다
			}
			
			
			// 2. bfs를 돌면서 depth를 visited에다가 구하고, 바로 윗 부모값을 parent[0][v]에 입력해서 dp를 수행할 수 있는 환경을 만들자
			//    원래는 bfs를 통해서 parent[0][v]를 구해야하는데, 여기서는 주어졌다, 그래도 bfs에서 만들자
			bfs(1, 0);	// 시작점1, depth0
			
			// 3. parent[k][v]값을 구한다, 점화식은 p[k][v] = p[k-1][p[k-1][v]]임
			aces_find();

			
			// 4. LCA로직을 태운다, MAX_K부터 시작해서 아래로 내려오면서 다를때까지 진행하다가 달라지는 순간 node값 x, y를 위로 올려주는 작업을 하면 됨
			//    그러면 마지막으로 올라간것의 부모가 최소공통조상이 된다.
			bw.append("#").append(String.valueOf(i));
			for(int j = 1; j <= Q; j++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				bw.append(" ").append(String.valueOf(lca(a, b)));
			}
			bw.newLine();
			
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	// bfs를 돌때, 뎁스를 저장하고, parent의 최초값도 세팅해준다
	private static void bfs(int node, int d) {
		ArrayDeque<Integer> q = new ArrayDeque<>();
		q.add(node);
		depth[node] = d;
		parent[0][node] = 0;	//root의 부모는 0
		
		while(!q.isEmpty()) {
			int now = q.poll();
			
			for(int i=0; i<adjList[now].size(); i++) {
				int next = adjList[now].get(i);
				if(depth[next] == 0) {
					q.add(next);
					depth[next] = depth[now]+1;
					parent[0][next] = now;
				}
			}
			
		}
	}
	
	// 2^n 번째 조상들을 다 저장해둔다.
		static void aces_find() {
			
			// 2^0 = 1
			// K = 0 은 BFS(DFS) 를 통해 다 저장해두었음
			for (int K = 1; K < MAX_K; K++) {
				for (int V = 1; V <= N; V++) {
					// K = 2, V = 17 parent[2][17] = parent[1][11]
					
					// 17의 2번 위 부모 = 17의 부모의 부모다
					// K = 1, V = 17 
					// parent[1][17] = parent[0][parent[0][17]] = parent[0][14]
					parent[K][V] = parent[K - 1][parent[K - 1][V]];
				}
			}
		}

		// x 와 y 의 공통 조상 찾기
		static int lca(int x, int y) {
			// 3  5
			// 7  4
			
			// 루트 기준에서 더 아래있는 정점을 y, 더 높이있는 정점을 x 로 맞춘다.
			// depth 가 더 작다는건 루트에 더 가깝다는 뜻
			// depth 가 큰 정점을 y 로 만든다.
			if (depth[x] > depth[y]) {
				int temp = x;
				x = y;
				y = temp;
			}
			
			// depth
			// x        3
			// y        14

			
			// "y 의 depth" 가 "x 의 depth" 와 같아질 때까지 y 를 끌어올림 = 높이 맞추기
			for (int i = MAX_K; i >= 0; i--) {
				
				// depth 차이가 11이면
				// 2^3 = 8 
				// 2^1 = 2
				// 2^0 = 1
				
				// 1 << i 값은 아래와 같다.
				//                K             K            K               K       K       K       K
				// [i, 1 << i] = [17, 131072], [16, 65536], [15, 32768] ... [3, 8], [2, 4], [1, 2], [0, 1]
				
				// 1    이진수 = 1
				// 10   이진수 = 2
				// 100  이진수 = 4
				
				// (1 << i) = 2 의 i 승 구하기
				// MAX_K = 17
				// depth 차이 11
				
				//         11                 8
				if (depth[y] - depth[x] >= (1 << i)) { // Math.pow(2, i)
					y = parent[i][y]; // 2^k
					// depth 차이 11
					// 8만큼 올렸으니까
					// depth 차이 3
				}
			}
			
			// x, y의 depth가 동일해진다.

			
			// 동일한 높이까지 끌어올렸을 때, x 와 y 가 같다면 둘의 조상이 같다는 뜻이니
			// 그것이 최저 공통 조상이다.
			if (x == y)
				return x;  // LCA

			
			// 1) x 와 y 가 같지 않다면, 루트에서부터 처음으로 조상이 같지 않은 지점을 만날 때까지 탐색
			// 2) 같은 않은 지점으로 이동 
			// 3) 1 ~ 2단계 반복
			//    (처음으로 달라진 위치에서 그들의 부모 중 공통 조상을 다시 찾음)

			// 처음으로 달라진 위치를 찾았다면
			// 거기로 이동해서 그 두 정점에 대한 최소공통조상을 찾는 문제가 된다. 
			for (int i = MAX_K; i >= 0; i--) {
				if (parent[i][x] != parent[i][y]) {
					x = parent[i][x];
					y = parent[i][y];
				}
			}

			return parent[0][x];
		}
	
}


/*
 * 최소공통조상 LCA
 * 1. BFS를 통해서 모든 정점들의 depth를 구한다 -> 뎁스배열에 저장해둔다
 * 2. 두 정점의 depth를 맞춘다 -> 부모정점으로 올가단다는 말이니까 이는 BFS탐색하면서 depth를 저장할때 부모정보까지도 parent배열에 저장해둔다
 *    트리가 1자이면 페어런트 찾는데 최악의 조건이 된다 1자트리가 10만개 노드면 10만번 페어런트를 찾기때문에 느리다
 *    그래서 parent배열을 1차원 배열의 바로 위 조상만 저장하지 않고 parnet[k][v]로 2차원 배열로 2^k번째 조상 정점번호를 저장한다.
 *    ex) parent[1][4] -> 4번노드의 2^1번째 조상, parent[16][7] 7번 노드의 2^4번째 조상 
 *    따라서 parent[k][v]는 점화식으로 표현 가능하다 
 *    parent[k][v] = parent[k-1][parent[k-1][v]] -> 2^(k+1) = 2^k+2^k == 2의 k+1승번째 부모는 2의k승번째 부모의 2의 k승번째 부모랑 같다
 *    2중포문을 통해서 점화식의 데이터를 dp로 쭉 계산한다
 *    이후 두 정점의 depth를 맞추는 용도로 parent배열을 통해 2^k승씩 올라가서 빠르게 동일 depth로 맞춘다
 *    높이가 동일하게 맞춰졌으면 LCA를 2^k승씩 비교하면서 올라가면서 찾자
 *    MAXK는 노드의 갯수를 초과하는 최소 2의배승 값이다 -> Math.pow로 하면 시간초과 나니까 1 << i 이렇게 2^i 승을 구해야한다
 *    depth차이가 11이라고 할때
 *    if(depth[y] - depth[x] >= (1<<k){ 1<<i가 줄어들면서 (i는 --) 	처음 만족하는 경우는 i가 3인경우에 처음 만족하니까 
 *    	y = depth[k][y] depth차이가 11이니까 8만큼 올리는 작업니다 -> 뎁스차이 k가 3이다
 *    }
 *    뎁스만 맞춰도 같으면 LCA이고 
 *    대부분은 같지 않을것이다,
 *    이러면 루트에서 처음으로 조상이 같이 않은 지점을 만날땨ㅐ까지 탐색하고
 *    처음으로 같이 않은 위치가 나오면 그들의 부모 중 공통 조상을 다시 찾는다
 *    
 *    int i=MAXK; i>=0; i--하면서 for문을 도는데,
 *    	if(parent[i][x] != parent[i][y])	//달라지는 첫 위치로 x와 y를 위로 올려준다
 *    		x = parent[i][x]
 *    		y = parent[i][y]
 *    	}
 *    이렇게 포문이 돌면은 계속 올리다보면 최소공통조상이 구해지는것이다
*/
