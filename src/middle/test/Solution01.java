package middle.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 모의 테스트 
 * 시간 제한 : 10개의 Test Input 입력시 C/C++ 1초 / Java 1초
 * 메모리 제한 : Stack : 1 Mbytes  /  Total : 256 Mbytes
 * 
 * [문제]
 * 소들은 피크닉을 갈 예정이다! 각 존의 K마리의 소들은 N개의 목초지중 하나의 목초지에서 풀을 뜯고 있다. 
 * 이 목초지들을 목초지1 … 목초지 N이라고 명명하자. 그 목초지들은 M개의 단방향 길로 연결되어 있다. (어떠한 길도 출발지와 도착지가 같지 않다.)
 * 소들은 그들의 피크닉동안 같은 목초지에서 모이기를 원한다. 하지만 몇마리의 소들은 모든 목초지에 도달할 수 없을 가능성이 있다.(단방향 도로이기 때문에) 
 * 소들을 도와 얼마나 많은 목초지에서 모든 소들이 모일 수 있는지 계산해주자.
 * 
 * [입력]
 * 첫 번째 줄에 테스트 케이스의 갯수 T가 주어진다. 이어서 T개 케이스의 입력값이 주어진다. 
 * 각 테스트 케이스의 첫번째 줄은 세개의 정수가 공백으로 구분되어 입력되어진다. K(1 <= K <= 100),N(1 <= N <= 1,000),M(1 <= M <= 10,000)
 * 케이스의 두 번째 줄터 K+1번째 줄까지는 K마리의 소들의 처음 풀을 뜯고있는 위치가 각 줄에 하나씩 주어진다.
 * K+2번째 줄부터 M+K+1번째 줄까지는 단방향 도로의 정보 시작점S 와 끝점E가 입력으로 주어진다.
 * 
 * [출력]
 * 각각의 테스트 케이스에 대하여 #x(x는 테스트 케이스 번호를 의미)를 출력하고 공백을 하나 둔 다음 모든 소가 모일 수 있는 가능한 목초지의 수를 출력한다.
 * 
 * [입출력 예]
 * (입력)
 * 1
 * 2 4 4
 * 2
 * 3
 * 1 2
 * 1 4
 * 2 3
 * 3 4
 * 
 * (출력)
 * #1 2
 * 
 * [힌트]
 * 모든 소들은 목초지 3,4에서 모일 수 있다.
 */
public class Solution01 {
	
	private static int T;
	private static int K;
	private static int N;
	private static int M;
	private static int[] cows;	// 소들이 있는 목장의 위치와 마리수
	private static int[] visited;
	private static int[] cnts;
	
	private static int[][] dir;	// 간선

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());
		
		for(int i=0; i<T; i++) {
			st = new StringTokenizer(br.readLine());
			K = Integer.parseInt(st.nextToken());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			// 소배열
			cows = new int[K+1];
			// 목초지 방문횟수
			cnts = new int[N+1];
			// 간선연결정보
			dir = new int[N+1][N+1];
			
			// 소가 있는 목초지 정보 입력 cows[1]=3 이면 1번소가 3번 목초지에 있다
			int cp=1;
			for(int j=0; j<K; j++) {
				cows[cp] = Integer.parseInt(br.readLine());
				cp++;
			}
			
			for(int j=0; j<M; j++) {
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());
				int e = Integer.parseInt(st.nextToken());
				dir[s][e] = 1;
			}
			
			
			// 모든 소를 돌면서 visited에 ++한다
			for(int j=1; j<cows.length; j++) {
				visited = new int[N+1];
				bfs(cows[j]);
			}
			
			int res = getMaxCnt();
			
			bw.append("#").append(String.valueOf(i+1)).append(" ").append(String.valueOf(res));
			bw.newLine();
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
	
	private static void bfs(int node) {
		Deque<Integer> queue = new ArrayDeque<>();
		queue.add(node);
		visited[node] = 1;
		cnts[node]++;
		
		while(!queue.isEmpty()) {
			int now = queue.poll();
			 
			for(int i=1; i<=N; i++) {
				if(visited[i] == 0 && dir[now][i] == 1) {
					queue.add(i);
					visited[i] = 1;
					cnts[i]++;
				}
			}
		}
	}
	
	private static int getMaxCnt() {
		
		Arrays.sort(cnts);
		int res=0;
		int maxval = cnts[cnts.length-1];
		for(int i=cnts.length-1; i>=1; i--) {
			if(cnts[i] == maxval) {
				res++;
			} else {
				break;
			}
		}
		
		return res;
	}

}
