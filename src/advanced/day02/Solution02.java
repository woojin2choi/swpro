package advanced.day02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [기출P-0085] [2021년 01월 16일] 외환 관리 
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AXcTN4PmL3_BC0Gg
 * @author woojin2.choi
 * @hint 플로이드 워셜 알고리즘을 알고있는가?
 */
public class Solution02 {
	
	private static int T;
	private static int N;	//통화종류수
	private static int M;	//교환비용정보개수->간선배열
	private static int D;	//업무일수
	private static int K;	//협약비용->특정노드를 얘로 바꿀수도있고 안바꿀수도있음
	private static long[][][] dist;
	
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc<=T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			D = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			dist = new long[N+1][N+1][2];	//기본 플루이드워셜에서 한개의 차원을 추가한다 -> K를 사용할것인가 안할것인가 두가지 방법
			
			for(int i=1; i<=N; i++) {
				for(int j=1; j<=N; j++) {
					dist[i][j][0] = Integer.MAX_VALUE;
					dist[i][j][1] = Integer.MAX_VALUE;
				}
			}
			
			for(int i=1; i<=M; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int z = Integer.parseInt(st.nextToken());
				dist[x][y][0] = z;
				dist[y][x][0] = z;
				dist[x][y][1] = K;
				dist[y][x][1] = K;
			}
			
			for(int k=1; k<=N; k++) {
				for(int i=1; i<=N; i++) {
					for(int j=1; j<=N; j++) {
						//K를 사용하지 않고 최단거리
						dist[i][j][0] = Math.min(dist[i][j][0], dist[i][k][0]+dist[k][j][0]);
						//K를 사용할때의 최단거리 K를 사용해서 중간k까지 간경우랑 K를 중간k이후로 사용한것중에서 최소값을 찾는것임
						dist[i][j][1] = Math.min(Math.min(dist[i][j][1], dist[i][k][1]+dist[k][j][0]), dist[i][k][0]+dist[k][j][1]);
					}
				}
			}
			
			long res = 0;
			for(int i=1; i<=D; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				res += Math.min(dist[x][y][0], dist[x][y][1]);
			}
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(res));
			bw.newLine();
			
		}
		bw.flush();
		bw.close();
		br.close();
	}
}

/*
 * 플로이드 워셜 무어 알고리즘은 N개의 출발지에서 N개의 도착지까지 모든 최단경로를 다 찾는 알고리즘이고, 점화식 3중포문으로 푼다
 * 어떤 두 정점 사이의 최단경로는 어떤 경유지 K를 지나거나 지나지 않거나이다.
 * 음수간선이 있어도 가능하다, 그리고 D[i][j]의 대각선 i==j인경우가 0이 아니라 값이 생기면 그건 싸이클이 있다는 뜻이다
 * A - K - B 로 거처간다고 가정하면 3중포문을 아래처럼 돌린다.
 * for K 거쳐가는 K
 * 	for i 출발하는 i
 * 	  for j 도착하는 j
 * 	    D[ij] = min(D[ij], D[ik]+D[kj])
 */
