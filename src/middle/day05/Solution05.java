package middle.day05;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [교육A-0005] 조합 
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVi99fqw3_mojUH_&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint nCk = nC(n-k), nCk = (n-1)C(k-1)+(n-1)Ck
 * @hint mod를 써서 DP로 푸는방법(+재귀로 푸는 방법도 넣어둠)
 */
public class Solution05 {
     
    private static int T;
    private static int n;
    private static int r;
    private static long[][] memo;
    private static long[][] nCr;
    
    private static final int M = 1000000007;	// mod값
 
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
         
        T = Integer.parseInt(br.readLine());
        bimomialDynamic(5000, 5000);
        for(int i=1; i<=T; i++) {
             
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            r = Integer.parseInt(st.nextToken());
            long temp;
             
            /*
            memo = new long[n+1][r+1];  
            temp = binomialRecursive(n, r);
            */
            temp = nCr[n][r];
            bw.append("#").append(String.valueOf(i)).append(" ").append(String.valueOf(temp%M));
            bw.newLine();
             
        }
        bw.flush();
        bw.close();
        br.close();
    }
     
    // nCr = n-1Cr-1 + n-1Cr 
    private static long binomialRecursive(int n, int r) {
        if(memo[n][r] != 0) {
            return memo[n][r];
        }
         
        if(r==0 || n==r) {
            memo[n][r] = 1;
            return memo[n][r];
        }
        memo[n][r] = binomialRecursive(n-1, r-1) + binomialRecursive(n-1, r);
        return memo[n][r];
    }
     
    // nCr = n-1Cr-1 + n-1Cr 
    private static long bimomialDynamic(int n, int r) {
         
        nCr = new long[n+1][r+1];
         
        nCr[0][1] = nCr[1][0] = 1;
         
        // r==0인경우와 n==r인 경우는 1로 업데이트 한다.
        for(int i=1; i<=n; i++) {
            for(int j=0; j<=i; j++) {    // 대각선까지만 구하면 됨
            	if(i==j || j==0) {
            		nCr[i][j] = 1;
            	} else {
            		nCr[i][j] = nCr[i-1][j-1]%M + nCr[i-1][j]%M;
            	}
            }
        }
        return nCr[n][r];
    }
}
