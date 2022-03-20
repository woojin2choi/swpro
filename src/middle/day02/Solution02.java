package middle.day02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;


/**
 * [연습A-0026] 지은이가 지은 집 
 * http://182.193.11.65/common/satti/practiceProblem.do?problemId=AVkBFrMAY1uojUGs
 * @author woojin2.choi
 * @hint array를 정렬하여 투포인트 비교(맨앞, 맨뒤)해 가면서 두 수의 합이 크면 뒤에걸 한칸 줄이고, 두 수의 합이 작으면 앞에걸 한칸 올린다.
 */
public class Solution02 {
	
	private static int T;
	private static int X;	//구멍의 너비
	private static int N;	//재료의 개수
	private static int[] array;	//재료 배열
	private static int L1, L2;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		T = Integer.parseInt(br.readLine());
		
		for(int i=1; i<=T; i++) {
			X = Integer.parseInt(br.readLine());
			N = Integer.parseInt(br.readLine());
			
			//재료배열 초기화
			array = new int[N];
			//cm를 nano로 바꿈
			X = X*10000000;
			L1 = L2 = 0;
			
			for(int j=0; j<N; j++) {
				array[j] = Integer.parseInt(br.readLine());
			}
			
			//배열을 정렬한다
			Arrays.sort(array);
			
			int j=0;
			int k=array.length-1;
			boolean available = false;
			while(N!=0) {	//와 씨... N이 0인 테스트 케이스가 있음 ㄷㄷㄷ
				if(j==k) {	
					break;
				}
				if(array[j]+array[k] > X) {	//숫자가 크면 오른쪽을 줄이고
					k--;
				} else if(array[j]+array[k] < X) {	//숫자가 작으면 왼쪽을 올리고
					j++;
				} else {	//딱맞으면 종료하자
					L1 = array[j];
					L2 = array[k];
					available = true;
					break;
				}
			}
			
			if(available) {
				bw.append("#").append(String.valueOf(i)).append(" yes ").append(String.valueOf(L1)).append(" ").append(String.valueOf(L2));
				bw.newLine();
			} else {
				bw.append("#").append(String.valueOf(i)).append(" danger");
				bw.newLine();
			}
		}
		bw.flush();
		bw.close();
		br.close();
	}

}
