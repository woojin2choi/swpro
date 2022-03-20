package advanced.day02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * [기출P-0086] [2021년 01월 17일] 휴가계획  
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AXcTOzQ2MK_BC0Gg
 * @author woojin2.choi
 * @hint 휴가일이 K일이니까, 0~K까지 플젝총합과 K+1~M까지의 플젝총합이 제일 큰것을 찾는다
 *       정방향DP와 역방향DP를 따로 구해서 각 쌍의 합이 제일 큰 값이 정답이 된다.
 */
public class Solution01 {
	
	private static int T;
	private static int N;
	private static int M;
	private static int K;
	
	private static Pjt[] P;	//프로젝트 정보
	private static long[] fD;
	private static long[] bD;
	
	private static class Pjt{
		int s;	//플젝시작
		int e;	//플젝종료
		int c;	//플젝수익
		Pjt(int s, int e, int c){
			this.s = s;
			this.e = e;
			this.c = c;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());	//프로젝트의 개수
			M = Integer.parseInt(st.nextToken());	//일하는기간
			K = Integer.parseInt(st.nextToken());	//휴가기간
			
			P = new Pjt[N+1];
			
			
			fD = new long[M+2];
			bD = new long[M+2];
			
			//프로젝트 정보 입력받기
			for(int i=1; i<=N; i++) {
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());
				int e = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				P[i] = new Pjt(s, e, c);
			}
			
			long ans = 0;
			
			Arrays.sort(P, 1, P.length, new Comparator<Pjt>() {
				@Override
				public int compare(Pjt o1, Pjt o2) {
					return o1.e - o2.e;
				}
			});
			for(int i=1, j=1; i<=M; i++) {
				fD[i] = fD[i-1];
				for(; j<=N && P[j].e==i; j++) {	//플젝이 i일에 끝날때 플젝이 끝나는 애들을 보고 최대값을 갱신한다.
					fD[i] = Math.max(fD[i], fD[P[j].s-1]+P[j].c);
				}
			}
			fD[M+1] = fD[M];
			
			bD[M+1] = 0;
			Arrays.sort(P, 1, P.length, new Comparator<Pjt>() {
				@Override
				public int compare(Pjt o1, Pjt o2) {
					return o2.s - o1.s;
				}
			});
			for(int i=M, j=1; i>0; i--) {
				bD[i] = bD[i+1];
				for(; j<=N && P[j].s==i; j++) {
					bD[i] = Math.max(bD[i], bD[P[j].e+1]+P[j].c);
				}
			}
			
			bD[0] = bD[1];
			
			for(int i=1; i<=M-K+1; i++) {
				ans = Math.max(ans, fD[i-1]+bD[i+K]);
			}
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(ans));
			bw.newLine();
		}
		bw.flush();
		bw.close();
		br.close();  
		
	}
	
	
	
}
