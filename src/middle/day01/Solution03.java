package middle.day01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * [기출A-0013] 숫자 배치하기 
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVYMLusQFKyojUEF&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 퍼뮤테이션 알고리즘만 구현하면 되는데 이 알고리즘이 따로 있음, https://gorakgarak.tistory.com/522 참고
 * @hint 퍼뮤테이션 알고리즘까지 외우기 뭐하면,,, 그냥 백트래킹으로 다 구하면 된다.
 */
public class Solution03 {
	
	private static int[][] POINT;
	private static int[] ARRAY;
	private static int MAX_VAL;
	private static int T;
	private static int N;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(sb.readLine());
		
		for(int i=1; i<=T; i++) {
			StringTokenizer st = new StringTokenizer(sb.readLine());
			N = Integer.parseInt(st.nextToken());
			MAX_VAL = 0;
			ARRAY = new int[N+1];
			for(int j=1; j<=N; j++) {
				ARRAY[j] = j;
			}
			
			// 점수판 초기화
			POINT = new int[N+1][N+1];
			for(int j=1; j<=N; j++) {
				st = new StringTokenizer(sb.readLine());
				for(int k=1; k<=N; k++) {
					POINT[j][k] = Integer.parseInt(st.nextToken());
				}
			}
			
			permutation(1, N);
			
			System.out.println("#"+i+" "+MAX_VAL);
			
		}
	}
	
	
	private static void permutation(int start, int end){
	    int val=0;
		if (start == end){	// 종료조건
			// 점수판[위치][숫자]이므로 더해서 제일 큰 값을 찾자
	    	for(int i=1; i<=N; i++) {
	        	val += POINT[i][ARRAY[i]];
	        }
	        if(val > MAX_VAL) {
	        	MAX_VAL = val;
	        }
	        
	    } else {
	        for (int i = start; i <= end; ++i){
	            swap(start, i);
	            permutation(start + 1, end);
	            swap(start, i);
	        }
	    }
	}
	
	private static void swap(int a, int b) {
		int temp = ARRAY[a];
		ARRAY[a] = ARRAY[b];
		ARRAY[b] = temp;
	}

}


// 아래는 백트래킹으로 푸는 방법, 지나간 좌표에 visited를 찍고 최대 깊이 까지 간다음에 포인트를 출력한다
/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	
	static int[][] s;
	static int answer;
	static int[] visited;
	static int N;
	
	public static void main(String args[]) throws IOException {

		BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(sb.readLine());

		int T = Integer.parseInt(st.nextToken());

		for (int i = 0; i < T; i++) {
			st = new StringTokenizer(sb.readLine());
			answer = 0;
			N = Integer.parseInt(st.nextToken());
			s = new int[N+1][N+1];
			visited = new int[N+1];
			
			for(int j=1; j<=N; j++) {
				st = new StringTokenizer(sb.readLine());
				for(int k=1; k<=N; k++) {
					s[j][k] = Integer.parseInt(st.nextToken());
				}
			}
			
			
			visited[0] = 1;
			backtrack(0, s[0][0]);
			
			
			System.out.println("#"+(i+1)+" "+answer);
		}
	}
	
	public static void backtrack(int depth, int point) {
		if(depth == N) {	// 백트래킹 종료조건
			if(point > answer) {
				answer = point;
				return;
			}
		}
		
		for(int i=1; i<=N; i++) {
			if(visited[i] == 0) {
				visited[i] = 1;
				backtrack(depth+1, point+s[depth+1][i]);
				visited[i] = 0;	// 백트래킹은 dfs와 다르게 방문하고 난 다음에 visited를 초기화해야한다, 다음에 또 들어가려면
			}
		}
	}

}*/

