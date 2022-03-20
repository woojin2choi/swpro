package middle.day08;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [교육P-0006] 가장 먼 두 도시
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AViorrEgkKmojUH_&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 플로이드 워셜 알고리즘을 알고있는가?
 */
public class Solution03 {
	
	private static int T;
	private static int N;
	private static long[][] dist;
	
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc<=T; tc++) {
			
			N = Integer.parseInt(br.readLine());
			dist = new long[N+1][N+1];
			
			for(int i=1; i<=N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=1; j<=N; j++) {
					dist[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			for(int k=1; k<=N; k++) {
				for(int i=1; i<=N; i++) {
					for(int j=1; j<=N; j++) {
						dist[i][j] = Math.min(dist[i][j], dist[i][k]+dist[k][j]);
					}
				}
			}
			
			long res = 0;
			for(int i=1; i<=N; i++) {
				for(int j=1; j<=N; j++) {
					res = Math.max(res, dist[i][j]);
				}
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
