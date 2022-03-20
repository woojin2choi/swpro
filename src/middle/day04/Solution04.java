package middle.day04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [교육P-0015] 파이합
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVi99NOQ38qojUH_&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 정수론의 오일러 파이 함수를 알고있는가? 그리고 그걸 구현 할 수 있는가? 의 문제인데 이런문제는 안나올듯..
 */
public class Solution04 {
	
	private static int T;
	private static int L;
	private static int R;

	private static int[] arrPhi;
	private static long[] sumPhi;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine());
		
		initPrimeList(1000000);
		
		for(int i=1; i<=T; i++) {
			st = new StringTokenizer(br.readLine());
			
			L = Integer.parseInt(st.nextToken());
			R = Integer.parseInt(st.nextToken());
			
			long res = sumPhi[R]-sumPhi[L-1];
			
			bw.append("#").append(String.valueOf(i)).append(" ").append(String.valueOf(res));
			bw.newLine();
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
	
	private static void initPrimeList(int N) {
		    
        // 에라토스테네스의 체를 이용해 소수 판별(= 합성수 여부)
        boolean[] isCompositeNumber = new boolean[N+1];
        // 합성수도 소수도 아니지만  true
        isCompositeNumber[1] = true;
        for(int i=2; i*i<=N; i++){
             // 에라토스테넷스의 체
             if(!isCompositeNumber[i]){
                   // 배수들을 제외 처리.
                   for(int j=2; i*j<=N; j++){  
                       isCompositeNumber[i*j] = true;
                   }
             }
        }

			
		/*
		 * 오일러 함수의 배열을 구하고, 오일러 함수를 통해 Phi Sum을 다이나믹으로 구할수있따.
		 * S[i] = S[i-1] + A[i]를 만족한다. -> 외우세요
		 * S[L..R] = S[R] - S[L-1]도 만족한다. -> 외우세요
		 * 
		[오일러 함수의 특징]
		a가 p의 배수이다 => p^k와 a가 서로소가 아니다.
    	p^k이하의 p의 배수가 아닌 수는 p^k-p^(k-1)개 존재, 이것은 ϕ(p^k).
        → ϕ(p^k) = (p-1) × p^(k-1) = p^k - p^(k-1)
		ex) ϕ(9) = ϕ(3*3) = (3-1) * 3 = {1, 2, 4, 5, 7, 8} = 6개
		기본적으로 ϕ(1) = 1임
		*/
		
        // 오일러 함수 결과 구하기
		arrPhi = new int[N+1];
        for(int i=1; i<=N; i++) {
        	arrPhi[i] = i;
        }
         
        for(int i=2; i<=N; i++) {
            // 소수이면
            if(!isCompositeNumber[i]){
                // 배수들에 대한 처리
                for(int j=1; i*j<=N; j++){
                	arrPhi[i*j] = (i-1) * (arrPhi[i*j] / i);
                }               
            }
        }
         
        sumPhi = new long[N+1];
        sumPhi[0] = 0; 
        sumPhi[1] = 1;
        for(int i=2; i<=N; i++) {
        	sumPhi[i] = sumPhi[i-1] + arrPhi[i];
        }

		
	}
	
	
}
