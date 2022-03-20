package middle.day09;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [연습P-0022] LIS 2
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVhxH7BQpbuojUH_&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint DP문제 이지만, adhoc같은 문제이다,
 *       모든 경우의수로 구하게 되면 N^2의 수행시간이 소요되지만, lis라는 정렬된 임시배열에 넣으면서 길이의 최대값을 구해보는 방법으로 푸는 문제다
 *       https://shoark7.github.io/programming/algorithm/3-LIS-algorithms 참고
 */
public class Solution01 {
	
	private static int T;
	private static int N;	// 수열의 길이
	private static int[] arr;	// 입력받은 배열
	private static int[] lis;	// LIS의 최대 개수를 세기위한 배열(이 배열의 내용은 실제 LIS는 아님 단순이 LIS의 최장 길이만을 구하고 싶어서 만든 배열임)
	
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc<=T; tc++) {
			
			N = Integer.parseInt(br.readLine());
			arr = new int[N];
			lis = new int[N];
			
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			
			lis[0] = arr[0];	// 맨앞의 값은 그냥 넣어도됨
			int i=1;	// arr의 포인터(0번은 lis에 넣었으니까)
			int j=0;	// lis의 포인터
			
			while(i < N) {	//arr배열 다 돌면 끝나라
				if(lis[j] < arr[i]) {	// lis배열의 마지막값보다 크면 lis 맨뒤에 넣는다
					lis[j+1] = arr[i];
					j++;
					i++;
				} else if(lis[j] == arr[i]){	// 같으면 arr포인터만 올려주고 스킵
					i++;
				} else {						// 작으면 lis배열의 위치를 찾아와서 거기다 넣고 교체
					int position = binarySearch(0, j, arr[i]);
					lis[position] = arr[i];
					i++;
				}
			}
			
			int res = j+1;	// lis배열의 포인터+1값이 lis길이인데 이게 제일 긴 값이 최장길이인것이다
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(res));
			bw.newLine();
			
		}
		bw.flush();
		bw.close();
		br.close();  
	}
	
	private static int binarySearch(int left, int right, int target) {
		
		int mid = 0;
		
		while(left<right) {
			mid = (left+right)/2;	// 2분으로 탐색할껀데 타겟이 mid보다 오른쪽이면 left를 오른쪽으로 줄여주고 반대면 right를 왼쪽으로 줄여가자
			if(lis[mid] < target) {	// lis 배열은 정렬이 되어있기 때문에 lis[mid]로 값을 가져와서 그 값이 target보다 작으면 left가 mid로 올라가서 다시 탐색한다
				left = mid+1;
			} else {				// lis가 정렬된 상태에서 lis[mid]값을 가져와서 타겟이 왼쪽에 있으면 right값을 mid로 줄여주고 다시 탐색
				right = mid;
			}
		}
		
		return right;	// left가 right보다 커진거니까 right가 lis배열안에 들어가야할 위치가 된것이다
	}
	
	
}

