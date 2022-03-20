package middle.day07;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * [기출P-0051] 조상이 키컸으면 
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AWPsyDwQgTqojUGz&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint LCA를 사용하되, bfs를 이용하여 depth를 구하는 동시에, 조상의 키가 자손의 키보다 크면 계속 max height값을 업데이트 하고있으면
 *       최저공통조상을 찾음과 동시에 그 노드의 max height값이 가장 키가 큰 조상의 키이다.
 *       LCA 파라미터가 여러개이면, 두개씩 연속으로 여러개 돌려야한다. -> 여러개중의 depth가 가장높고 낮은애 둘만 가지고 하니 답이 아니었음
 */
public class Solution03 {
	
	private static int T;
	private static int N;	//정점의 갯수
	private static int Q;	//질의의 수
	private static int[] depth;	//bfs용 visited배열
	private static int[] ki;
	private static ArrayList<Integer>[] adjList;
	private static int[][] parent;	//최저공통조상 LCA를 위한 parent[k][v] 배열, k는 2의 지수임
	private static final int MAX_K = 14;	//N이 10000명이니까 14면 된다. 2의 14는 16384 
	
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
			ki = new int[N+1];
			
			// 1. 먼저 입력을 받는다
			for (int j=1; j<=N; j++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken()); 
				int b = Integer.parseInt(st.nextToken());
				adjList[a].add(j);	// j의 부모는 a이고
				ki[j] = b;			// j의 키는 b이다
			}
			
			
			// 2. bfs를 돌면서 depth를 visited에다가 구하고, 바로 윗 부모값을 parent[0][v]에 입력해서 dp를 수행할 수 있는 환경을 만들자
			bfs(1, 0);	// 시작점1, depth0
			
			// 3. parent[k][v]값을 구한다, 점화식은 p[k][v] = p[k-1][p[k-1][v]]임
			aces_find();

			bw.append("#").append(String.valueOf(i));
			for(int j=1; j<=Q; j++) {
				st = new StringTokenizer(br.readLine());
				int K = Integer.parseInt(st.nextToken());
				long r=0;
				if(K==1) {
					int temp = Integer.parseInt(st.nextToken());
					r = ki[temp];
				} else if(K==2) {
					int x = Integer.parseInt(st.nextToken());
					int y = Integer.parseInt(st.nextToken());
					r = ki[lca(x, y)];
				} else {
					int temp[] = new int[K];
					for(int k=0; k<K; k++) {
						temp[k] = Integer.parseInt(st.nextToken());
					}
					int x = temp[0];
					//최소공통조상의 결과값과 다음번 k사람의 공통조상과 계쏙 구한다
					int res=0;
					for(int k=1; k<K; k++) {
						if(k==1) {
							res = lca(x, temp[k]);
						} else {
							res = lca(res, temp[k]);
						}
					}
					r = ki[res];
				}
				bw.append(" ").append(String.valueOf(r));
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
		ki[node] = ki[node];
		
		while(!q.isEmpty()) {
			int now = q.poll();
			
			for(int i=0; i<adjList[now].size(); i++) {
				int next = adjList[now].get(i);
				if(depth[next] == 0) {
					q.add(next);
					depth[next] = depth[now]+1;
					parent[0][next] = now;
					ki[next] = Math.max(ki[now], ki[next]);
				}
			}
			
		}
	}
	
	// 2^n 번째 조상들을 다 저장해둔다.
	private static void aces_find() {
		for (int K = 1; K < MAX_K; K++) {
			for (int V = 1; V <= N; V++) {
				parent[K][V] = parent[K - 1][parent[K - 1][V]];
			}
		}
	}

	// x 와 y 의 공통 조상 찾기
	private static int lca(int x, int y) {
		// 큰수를 뒤로
		if (depth[x] > depth[y]) {
			int temp = x;
			x = y;
			y = temp;
		}
		
		// "y 의 depth" 가 "x 의 depth" 와 같아질 때까지 y 를 끌어올림 = 높이 맞추기
		for (int i = MAX_K; i >= 0; i--) {
			if (depth[y] - depth[x] >= (1 << i)) { // Math.pow(2, i)는 시간초과난다
				y = parent[i][y]; // 끌어올림
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
