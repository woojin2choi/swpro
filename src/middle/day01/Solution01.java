package middle.day01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * [연습A-0028] 나누기 
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVhMrR7QJyOojUH_&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 구현문제, 2차원배열의 시작위치와 사이즈를 가지고 잘라지는 4구역을 재귀로 들어가면됨.
 *
 */
public class Solution01 {
	
	private static int[][] PAPER;
	private static int cnt0 = 0;
	private static int cnt1 = 0;

	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int i=1; i<=T; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			
			// 종이 생성
			PAPER = new int[N][N];
			// 초기화
			cnt0=0;
			cnt1=0;
			// 종이 초기화
			for(int j=0; j<N; j++) {
				st = new StringTokenizer(br.readLine());
				for(int k=0; k<N; k++) {
					PAPER[j][k] = Integer.parseInt(st.nextToken());
				}
			}
			
			// 탐색
			getPaperCount(PAPER, 0, 0, N);
			System.out.println("#"+i+" "+cnt0+" "+cnt1);
			
		}
	}
	
	private static void getPaperCount(int[][] paper, int start_x, int start_y, int size) {
		if(size==0) {
			return;
		}
		
		int num = getValue(paper, start_x, start_y, size);

		if(num != -1) {		// paper가 한 숫자로 되어있거나, 사이즈가 1이면 그 숫자 카운트 올리고 종료
			if(num == 0) {
				cnt0++;
			} else {
				cnt1++;
			}
			return;
		} else {			// 아니면 나누고 재귀
			getPaperCount(paper, start_x, start_y, size/2);	//왼쪽위
			getPaperCount(paper, start_x, start_y+size/2, size/2);	//오른쪽위
			getPaperCount(paper, start_x+size/2, start_y, size/2);	//왼쪽아래
			getPaperCount(paper, start_x+size/2, start_y+size/2, size/2);	//오른쪽아래
		}
	}
	
	// 한개의 숫자로 되어있으면 그 숫자를, 아니면 -1을 리턴
	private static int getValue(int[][] paper, int start_x, int start_y, int size) {
		int startVal = paper[start_x][start_y];
		boolean check = true;
		
		for(int i=start_x; i<start_x+size; i++) {
			for(int j=start_y; j<start_y+size; j++) {
				if(paper[i][j]==startVal) {
					check = true;
				} else {
					check = false;
					break;
				}
			}
			if(!check) {
				break;
			}
		}
		
		if(!check) {
			return -1;
		} else {
			return startVal;
		}
	}

}









