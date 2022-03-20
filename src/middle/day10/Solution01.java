package middle.day10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * [교육P-0028] LCS
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVmHHWGgkXKojUFy&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 두 문자에 대한 MN행렬을 만들어서 DP로 풀자,
 *       LCS는 배열에서 N과 M이 같으면 왼쪽대각선+1, 다르면 왼쪽이나 위쪽에서 큰값을 가져오자
 *       다 찾으면 LCS[N][M]의 값이 최대길이 이니까, 역으로 문자를 찾아가면 된다.
 *       스택을 만들어서 NM부터 찾아가되, N과 M이 같으면 대각선 왼쪽 위로 올라가면서 스택에 그 글자를 저장하고,
 *       다르면 왼쪽에서 왔는지 오른쪽에서 왔는지 보고 해당 위치로 이동한다, 0을 만날때까지 계속하면 여러개의 LCS중 한개를 찾을 수 있따
 */
public class Solution01 {
	
	private static int T;
	private static int[][] LCS;
	
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc<=T; tc++) {
			String A = br.readLine();
			String B = br.readLine();
			int N = A.length();
			int M = B.length();
			
			char[] sentA = new char[N+1];
			char[] sentB = new char[M+1];
			
			for(int i=1; i<=N; i++) {
				sentA[i] = A.charAt(i-1);
			}
			for(int i=1; i<=M; i++) {
				sentB[i] = B.charAt(i-1);
			}
			
			LCS = new int[N+1][M+1];
					
			// LCS 0번라인은 전부0이다
			for(int i=1; i<=N; i++) {
				for(int j=0; j<=M; j++) {
					if(j==0) {
						LCS[i][j] = 0;
					} else {
						// 비교하는 글자가 같은경우에는 왼쪽상단값 + 1하자
						if(sentA[i] == sentB[j]) {
							LCS[i][j] = LCS[i-1][j-1]+1;
						} else {	// 다른경우에는 왼쪽과 위쪽 둘 중 큰걸로 하자
							LCS[i][j] = Math.max(LCS[i][j-1], LCS[i-1][j]);
						}
					}
				}
			}
			
			int LCS_length = LCS[N][M];
			
			char[] res = new char[LCS_length];
					
			int n = N;
			int m = M;
			int c = LCS_length-1;
			while(c >= 0) {
				//같으면 좌측 위로 올라가고 현재 char를 stack에 넣는다
				if(sentA[n]==sentB[m]) {
					res[c] = sentA[n];
					n--;
					m--;
					c--;
				} else {	//다르면 왼쪽에서 온건지 위에서 온건지 찾아서 그리로 간다
					if(LCS[n][m-1] == LCS[n][m]) {	// 왼쪽에서 온경우
						m--;
					} else {						// 위에서 온경우
						n--;
					}
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
LCS 최장 공통 부분수열의 값을 구하기 위해서는, DP를 활용하여 푼다
1. 0을 맨 앞에 포함한 배열을 만든다 -> 두 문자열 모두 앞에 0번은 0을 넣는다 -> 왼쪽위를 참고하거나 위쪽, 왼쪽을 참고할수있기때문
2. N M 배열을 비교하면서 O(NM)탐색을 할껀데, 두 글자가 같으면 왼쪽위의 값을, 다르면 왼쪽값이랑 위쪽값이랑 둘중에 큰값을 넣고 O(NM)탐색을 한다.
3. LCS의 마지막 값이 최대로 같은 LCS의 길이값이 나온다
4. 그 길이 만큼 크기의 배열을 만들어서 LCS를 구한 테이블의 역으로 조사해가면서 LCS 문자열 한개를 만들어보자
5. N이고 M일때 두 글자가 같으면 왼쪽 위로 이동하고 해당 글자를 배열에 넣는다.
     두 글자가 다른경우에는 왼쪽에서 왔는지, 위쪽에서 왔는지 확인하고 그 위치로 포인터를 줄여준다.
     글자 길이만큼 배열에 넣으면 종료하면 된다.
     
예제 
1
ABCBDAB
BDCABA
위의 LCS 테이블은 아래와 같다.

	0	A	C	A	Y	K	P
0	0	0	0	0	0	0	0
C	0	0	1	1	1	1	1
A	0	1	1	2	2	2	2
P	0	1	1	2	2	2	3
C	0	1	2	2	2	2	3
A	0	1	2	3	3	3	3
K	0	1	2	3	3	4	4

*/