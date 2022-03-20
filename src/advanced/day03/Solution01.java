package advanced.day03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [기출P-0083] 셜록의 유적 탐사 
 * @author woojin2.choi
 *
 */
public class Solution01 {
	
	private static int T;
	private static int N;
	private static int M;
	private static int K;
	private static int[] A;
	private static long[] SumCount;
	

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			A = new int[N];		// 버튼정보
			SumCount = new long[M];	// 두개의 버튼을 눌렀을때 합%K를 인덱스로 하는 카운트 저장 배열
			
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<N; i++) {
				A[i] = Integer.parseInt(st.nextToken());
			}
			
			
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					int sum = (A[i]+A[j])%M;
					if(sum<0) {
						sum = sum+M;
					}
					SumCount[sum] = SumCount[sum] + 1;
				}
			}
			
			// 난 아직도 왜 이렇게 하는지 모르겠다...
			long ans = 0;
			for(int idx=0; idx<M; idx++) {
				if(idx<=K) {
					ans += SumCount[idx]*SumCount[K-idx];
				} else {
					ans += SumCount[idx]*SumCount[M+K-idx];
				}
				
			}
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(ans));
			bw.newLine();
		}
		
		bw.flush();
		bw.close();
		br.close();
	}

}
