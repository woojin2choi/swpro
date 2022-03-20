package middle.day10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [교육A-0010] 가장 큰 정사각형
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVmHHkRQkYOojUFy&_menuId=AVUU732mAAHBC0c9&_menuF=true#
 * @author woojin2.choi
 * @hint 정사각형의 크기를 누적하는 방법은 좌측위부터 위 왼쪽의 값들이 모두 1인경우에는 현재 값에다가 2를 넣으면 2짜리 정사각형이 나오는걸 이용하자
 *       1과 0은 뒤집어서 입력해보자
 */
public class Solution02 {
	
	private static int T;
	private static int N;
	private static int M;
	private static int[][] mat;
	
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc<=T; tc++) {
			
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			mat = new int[N][M];
			
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<M; j++) {
					int t = Integer.parseInt(st.nextToken());
					mat[i][j] = (t==1) ? 0 : 1; 
				}
			}
			
			int max = 0;
			
			// 좌측상단부터 시작해서 위 왼쪽의 값 중에서 최소값+1을 현재에다가 업데이트, 단 현재가 값이 1인경우만
			for(int i=1; i<N; i++) {
				for(int j=1; j<M; j++) {
					if(mat[i][j] == 1) {
						int a = mat[i-1][j-1];
						int b = mat[i-1][j];
						int c = mat[i][j-1];
						int min = Math.min(Math.min(a, b), c)+1;	// 좌측상단 왼쪽 위 셋중에 젤 작은 숫자 +1로 변경
						mat[i][j] = min;
						if(min > max) {
							max = min;
						}
					}
				}
			}
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(max));
			bw.newLine();
			
		}
		bw.flush();
		bw.close();
		br.close();  
	}
}

/*
 * 1. 정사각형의 넓이를 구하기 위해서는 M*N의 시간에 끝내는 방법을 찾아야한다.
 * 2. 배열을 돌면서 뭔가 이전값을 가지고 누적하는 방법을 찾아야한다.
 * 3. 1,1부터 시작하여 좌측상단, 위, 왼쪽의 값 중에서 제일 작은 숫자 + 1의 값을 현재의 i,j에 넣는다, 단 현재 i,j의 값이 1인경우에만 한다.
 *    (사각형의 크기는 좌측위부터 1씩 증가시키면 크기가 누적되는것을 이용하는것이다)
 * 4. 배열의 크기만큼 돌면서 max값을 출력하면 된다. 근데 이걸 어떻게 생각함? 게다가 문제는 Ad문제임
 */
