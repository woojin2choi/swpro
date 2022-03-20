package middle.day06;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [연습A-0002] 키순서
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVW5D7ywA9qojUHl&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint dfs를 정방향(1)과 역방향(-1)로 진행하면서 정방향+역방향 값이 N-1인경우가 자신의 키 위치를 알수있는 노드임
 */
public class Solution04 {
	
	private static int T;
	private static int N;
	private static int M;
	private static int[][] adjMat;
	private static boolean[] visited;
	private static int man;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int i = 1; i <= T; i++) {
			N = Integer.parseInt(br.readLine());
			M = Integer.parseInt(br.readLine());
			
			// 인접행렬 초기화(N이 500까지니까 행렬도 괜찮음)
			adjMat = new int[N+1][N+1];
			
			for(int j=1; j<=M; j++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				adjMat[a][b] = -1;	// 키 작은순서 a->b -1로 해두면 dfs하면서 -1인것들만 찾으면 된다.
				adjMat[b][a] = 1;	// 키 큰순서 b->a 1로 해두면 dfs하면서 1인것들만 찾으면됨
			}
			
			int res=0;
			
			for(int j=1; j<=N; j++) {
				visited = new boolean[N+1];
				
				man = 0;
				
				dfs(i, 1); //나를 포함한 키작은 사람 카운트
				dfs(i, -1); //나를 포함한 키큰 사람 카운트
				
				if(man-1 == N) {
					res++;	// 나를 제외하고 키큰사람 키작은 사람 을 모두 man에 넣었는데 N-1과 같으면 나의 키가 몇번째인지는 알수있따
				}
			}
			
			bw.append("#").append(String.valueOf(i)).append(" ").append(String.valueOf(res));
			bw.newLine();
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	private static void dfs(int node, int direction) {
		visited[node] = true;	//방문했따고 체크하자
		
		man++;	//사람수 증가 -> 나도 포함됨
		
		// 나와 연결된 다음 노드 탐색
		for(int i=1; i<=N; i++) {
			if(visited[i]==false && adjMat[node][i] == direction) {
				dfs(i, direction);
			}
		}
	}
}

/*
DFS를 정방향 역방향으로 돌려서 나보다 작은사람수+나보다 큰사람수 -1 이면 내키를 아는사람임
이걸 어떻게 생각해냄? 
*/