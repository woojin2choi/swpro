package middle.day02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;


/**
 * [교육P-0020] 달리기 
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVmF7-jAeXGojUFy&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint MergeSort를 내림차순으로 진행하면서 분해하고나서 다시 합병으로 정렬할때, 앞에 있는 추월할 수 있는 사람들의 수를 구해서 누적하는 방법
 * @hint 결과값이 int로 안나올수있으므로 answer를 long으로 선언해야함
 */
public class Solution03 {
	
	private static int T;
	private static int N;	//달리기선수
	private static long answer = 0;
	private static int[] array;
	private static int[] sortedArray;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine());
		
		for(int i=1; i<=T; i++) {
			 N = Integer.parseInt(br.readLine());
			 st = new StringTokenizer(br.readLine());
			 answer = 0;
			 array = new int[N];
			 sortedArray = new int[N];
			 
			 for(int j=0; j<N; j++) {
				 array[j] = Integer.parseInt(st.nextToken());
			 }
			 
			 mergeSort(array, 0, N-1);
			 
			 bw.append("#").append(String.valueOf(i)).append(" ").append(String.valueOf(answer));
			 bw.newLine();
		}

		bw.flush();
		bw.close();
		br.close();
	}
	
	private static void mergeSort(int[] array, int left, int right) {
		if(left<right) {
			int mid = (left+right)/2;
			mergeSort(array, left, mid);
			mergeSort(array, mid+1, right);
			merge(array, left, mid, right);
		}
	}
	
	private static void merge(int[] array, int left, int mid, int right) {
		// MergeSort의 핵심, while문의 조건
		int i=left;		//병합될 좌측 배열의 시작위치
		int j=mid+1;	//병합될 우측 배열의 시작위치
		int k=left;		//정렬의 결과를 저장하는 배열의 시작위치
		
		while(i<=mid && j<=right) {	//두 배열중 하나가 끝날때까지 돌자
			if(array[i]<array[j]) {	//내림차순으로 정렬해가면서 역전하는경우에는 값을 누적한다
				answer = answer+(mid-i+1); //왼쪽배열의 커서(i)에서부터 왼쪽배열의 끝(mid)까지의 길이이므로 mid-i+1이 왼쪽 배열의 위치로 찾아가는 숫자임 -> 그림그려봐야함
				sortedArray[k] = array[j];
				j++;
			} else {
				sortedArray[k] = array[i];
				i++;
			}
			k++;
		}
		
		// 한쪽 배열이 남은경우 마지막 남은 배열을 sotedArray에다가 일괄복사한다.
		if(i>mid) {
			while(j<=right) {
				sortedArray[k] = array[j];
				k++; 
				j++;
			}
		} else {
			while(i<=mid) {
				sortedArray[k] = array[i];
				k++; 
				i++;
			}
		}
		
		// 원본배열에다가 정렬된값을 저장한다
		for(int t=left; t<=right; t++) {
			array[t] = sortedArray[t];
		}
		
	}

}
