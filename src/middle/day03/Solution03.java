package middle.day03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [기출A-0030] 어떤 트리
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AWm4ATkwhTiojUFD&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 2진트리를 만들고(왼쪽부터차오르는) 맨 위의 값을 빼고 마지막값을 순차적으로 뎁스를 올려가면서 비교하여 변환한다. 
 */
public class Solution03 {
	
	private static int[] MAX_HEAP;	// Max Heap 완전 이진트리의 자료구조 선언
	private static int N;
	private static long LAST_INDEX;
	private static long LAST_DEPTH;
	
	
	public static void main(String args[]) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int T = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<T; i++) {
			
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			
			// 걍 문제에서 말한 최대로 선언
			MAX_HEAP = new int[1000001];
			
			st = new StringTokenizer(br.readLine());
			
			for(int j=1; j<=N; j++) {
				MAX_HEAP[j] = Integer.parseInt(st.nextToken());
			}
			
			getMaxHeap();
			
			bw.append("#").append(String.valueOf(i+1)).append(" ").append(String.valueOf(LAST_DEPTH)).append(" ").append(String.valueOf(LAST_INDEX));
			bw.newLine();
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	public static int getMaxHeap() {
		
		int retVal = MAX_HEAP[1];	// 맥스힙 최상단 값을 추출
		
		MAX_HEAP[1] = MAX_HEAP[N];	// 마지막 값을 맥스힙에 최상위에 올려놓고 비교하면서 내려가자
		MAX_HEAP[N] = 0;			// 마지막 값을 초기화
		
		int i=1;
		int depth=1;
		while(true) {
			int childMax = Math.max(MAX_HEAP[i*2], MAX_HEAP[i*2+1]);	// 현재 노드의 자식노드 두개중에 큰값
			// 둘 중 한개라도 위보다 크면 
			if(childMax > MAX_HEAP[i]) {
				if(childMax == MAX_HEAP[i*2]) {	// 왼쪽이 더 크면 왼쪽이랑 스위치 
					swap(i, i*2);
					i = i*2;
				} else {						// 오른쪽이 더 크면 오른쪽이랑 스위치
					swap(i, i*2+1);
					i = i*2+1;
				}
			} else {
				break;
			}
			depth++;
		}
		LAST_INDEX = i;			// 최상위에서부터 내려오다가 정지한 노드 i
		LAST_DEPTH = depth-1;	// 그리고 그 뎁스, 마지막에 ++이 되어버렸으니까 1빼주자
		
		for(int k=0; k<LAST_DEPTH; k++) {
			if(k==0) {
				LAST_INDEX = LAST_INDEX - 1; 
			} else {
				LAST_INDEX = LAST_INDEX - (long)Math.pow(2, k);	// 인덱스에서 뎁스한칸 내려올때마다 2의 제곱만큼 빼주면 왼쪽부터 몇번째인지 계산할수잇음
			}
		}
		return retVal;
	}
	
	public static void swap(int a, int b) {
		int temp = MAX_HEAP[a];
		MAX_HEAP[a] = MAX_HEAP[b];
		MAX_HEAP[b] = temp;
	}

}
