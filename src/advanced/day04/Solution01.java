package advanced.day04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
//나중에 다시 풀어보자
/**
 * [기출P-0074] 0 to 99 
 * @author woojin2.choi
 * @hint 조합, 파스칼의 삼각형을 이용해서 값이 몇번 더해졌는지를 곱해버리면서 계산하면 되는데 난 못품
 */
public class Solution01 {
 
    public static void main(String[] args) throws NumberFormatException, IOException {
 
        int pascal[][] = new int[1001][1001];
 
        // O(N^2) // 파스칼 미리 깔기
        for (int i = 2; i < 1000; i++) {
            pascal[i + 1][0] = 1;
            for (int j = 1; j < i - 1; j++) {
                pascal[i + 1][j] = (pascal[i][j - 1] + pascal[i][j]) % 10;
            }
            pascal[i + 1][i - 1] = 1;
        }
 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
 
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine().trim());
            int N = Integer.parseInt(st.nextToken());
            int Q = Integer.parseInt(st.nextToken());
 
            int[] A = new int[1000];
            int[] B = new int[1000];
 
            /// 카드배열
            st = new StringTokenizer(br.readLine().trim());
            for (int i = 0; st.hasMoreTokens();) {
                A[i++] = Integer.parseInt(st.nextToken());
            }
            st = new StringTokenizer(br.readLine().trim());
            for (int i = 0; st.hasMoreTokens();) {
                B[i++] = Integer.parseInt(st.nextToken());
            }
 
            int AA10 = 0;
            int BB10 = 0;
            int AA1 = 0;
            int BB1 = 0;
 
            // 현재 카드로 현재 값 구하기
            for (int i = 0; i < N - 1; i++) {
                AA10 += A[i] * pascal[N][i];
                BB10 += B[i] * pascal[N][i];
                AA1 += A[i + 1] * pascal[N][i];
                BB1 += B[i + 1] * pascal[N][i];
                 
                // 계속 10으로 나머지 나누기
                AA10 %= 10;
                BB10 %= 10;
                AA1 %= 10;
                BB1 %= 10;
            }
 
            int AResult = 0;
            int BResult = 0;
 
            // Q 반복하면서 처리하기 -> O(Qx1000) -> O(Qx1) 로 줄이는게 패스를 결정
            for (int i = 0; i < Q; i++) {
 
                st = new StringTokenizer(br.readLine().trim());
                int turn = Integer.parseInt(st.nextToken());
                 
                // 1번일때 카드교체
                if (turn == 1) {
                    int s1 = Integer.parseInt(st.nextToken()) - 1;
                    int s2 = Integer.parseInt(st.nextToken()) - 1;
 
                    int AGap10 = s1 < N - 1 ? (B[s2] - A[s1]) * pascal[N][s1] : 0; // s1 < N - 1 의미 : 변경한 카드가 마지막 카드면 차이 연산 안함
                    int BGap10 = s2 < N - 1 ? (A[s1] - B[s2]) * pascal[N][s2] : 0;
 
                    int AGap1 = s1 > 0 ? (B[s2] - A[s1]) * pascal[N][s1-1] : 0; // s1 < N - 1 의미 : 변경한 카드가 첫번째 카드면 차이 연산 안함
                    int BGap1 = s2 > 0 ? (A[s1] - B[s2]) * pascal[N][s2-1] : 0;
 
                    // A의 10의 점수, B의 10의 점수 계산
                    AA10 = (AA10 + (AGap10 + 1000)) % 10; // pascal을 곱하고 음수처리를 하기때문에 막연히 큰 10의 배수를 더함
                    BB10 = (BB10 + (BGap10 + 1000)) % 10; // pascal을 곱하고 음수처리를 하기때문에 막연히 큰 10의 배수를 더함
 
                    // A의 1의 점수, B의 1의 점수 계산
                    AA1 = (AA1 + (AGap1 + 1000)) % 10; // pascal을 곱하고 음수처리를 하기때문에 막연히 큰 10의 배수를 더함
                    BB1 = (BB1 + (BGap1 + 1000)) % 10; // pascal을 곱하고 음수처리를 하기때문에 막연히 큰 10의 배수를 더함
 
                    // 카드 교체
                    int temp = B[s2];
                    B[s2] = A[s1];
                    A[s1] = temp;
                     
                } else if (turn == 2) {
                    // 2번일때 A 혼자 카드교체
                    int s1 = Integer.parseInt(st.nextToken()) - 1;
                    int s2 = Integer.parseInt(st.nextToken()) - 1;
 
                    int AGap10_1 = s1 < N - 1 ? (A[s2] - A[s1]) * pascal[N][s1] : 0;
                    int AGap10_2 = s2 < N - 1 ? (A[s1] - A[s2]) * pascal[N][s2] : 0;
 
                    int AGap1_1 = s1 > 0 ? (A[s2] - A[s1]) * pascal[N][s1-1] : 0;
                    int AGap1_2 = s2 > 0 ? (A[s1] - A[s2]) * pascal[N][s2-1] : 0;
 
                    AA10 = (AA10 + (AGap10_1 + AGap10_2 + 1000)) % 10; // pascal을 곱하고 음수처리를 하기때문에 막연히 큰 10의 배수를 더함
                    AA1 = (AA1 + (AGap1_1 + AGap1_2 + 1000)) % 10; // pascal을 곱하고 음수처리를 하기때문에 막연히 큰 10의 배수를 더함
 
                    int temp = A[s2];
                    A[s2] = A[s1];
                    A[s1] = temp;
                     
                } else if (turn == 3) {
                    // 3번일때 B 혼자 카드교체
                    int s1 = Integer.parseInt(st.nextToken()) - 1;
                    int s2 = Integer.parseInt(st.nextToken()) - 1;
 
                    int BGap10_1 = s1 < N - 1 ? (B[s2] - B[s1]) * pascal[N][s1] : 0;
                    int BGap10_2 = s2 < N - 1 ? (B[s1] - B[s2]) * pascal[N][s2] : 0;
 
                    int BGap1_1 = s1 > 0 ? (B[s2] - B[s1]) * pascal[N][s1-1] : 0;
                    int BGap1_2 = s2 > 0 ? (B[s1] - B[s2]) * pascal[N][s2-1] : 0;
 
                    BB10 = (BB10 + (BGap10_1 + BGap10_2 + 1000)) % 10;
                    BB1 = (BB1 + (BGap1_1 + BGap1_2 + 1000)) % 10;
 
                    int temp = B[s2];
                    B[s2] = B[s1];
                    B[s1] = temp;
                } else if (turn == 4) {
                    // 4번일때 현재 카드점수 더하기
                    int ACur = AA10 * 10 + AA1;
                    int BCur = BB10 * 10 + BB1;
                    AResult += ACur;
                    BResult += BCur;
                }
            }
            System.out.println("#"+tc+" " + AResult + " " + BResult);
        }
    }
 
}
