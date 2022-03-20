package middle.day03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [연습P-0019] 구간합 
 * http://182.193.11.65/common/satti/practiceProblem.do?problemId=AVgFI19QAViojUEZ
 * @author woojin2.choi
 * @hint 세그먼트 트리를 구하는 문제, 재귀로 코드를 짤수도있고 bottom up 방식으로 코드를 구현할수도있는데, 이건 재귀로했음
 */
public class Solution02 {
	
	static int[] arr;
	static int[] segtree;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int T = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<T; i++) {
			st = new StringTokenizer(br.readLine());
			
			int N = Integer.parseInt(st.nextToken());
			//int segLength = ((int)Math.pow((int)(Math.sqrt(N))+3, 2))*2;
			int segLength = 300000;	//최대 배열의 크기를 구할수가없어서 걍 크게했음... 
			
			arr = new int[N+1];
			// 배열 초기화
			for(int j=1; j<=N; j++) {
				arr[j] = j;
			}
						
			segtree = new int[segLength+1];	// 가장가까운 제곱수의 두배
			
			init(1, N, 1);
			
			st = new StringTokenizer(br.readLine());
			int Q = Integer.parseInt(st.nextToken());
			long result=0;
			
			for(int k=0; k<Q; k++) {
				st = new StringTokenizer(br.readLine());
				int C = Integer.parseInt(st.nextToken());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				if(C==0) {
					int dif=0;
					if(arr[a] > b) {
						dif = (arr[a]-b)*-1;
					} else {
						dif = (b-arr[a]);
					}
					arr[a] = arr[a]+dif;
					update(1, N, 1, a, dif);
				} else {
					result += sum(1, N, 1, a, b);
				}
				
			}
			
			if(result<0) {
				result = (result%1000000007)+1000000007;
			} else {
				result = result%1000000007;
			}

			bw.append("#").append(String.valueOf(i+1)).append(" ").append(String.valueOf(result));
			bw.newLine();
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	public static int init(int start, int end, int node) {
		if(start == end) {
			return segtree[node] = arr[start];
		}
		
		int mid = (start+end)/2;
		
		//부분합 세그먼트 트리를 구하는거므로 재귀적으로 두부분으로 나눈다음에 합을 세그먼트 트리로한다.
		return segtree[node] = init(start, mid, node*2) + init(mid+1, end, node*2+1);
	}
	
	//left,right는 부분 합을 구하고자 하는 범위
	public static long sum(int start, int end, int node, int left, int right) {
		
		//세그먼트 트리 범위 밖에 있는경우
		if(left > end || right < start) {
			return 0;
		}
		
		//start,end로 부분이 좁혀져서 들어오는 세그먼트가 left와 right에 딱 들어오게되는경우에 리턴
		if(left <= start && end <= right) {
			return segtree[node];
		}
		
		int mid = (start+end)/2;
		
		return sum(start, mid, node*2, left, right) + sum(mid+1, end, node*2+1, left, right);
	}
	
	//segtree 수정해서 차이나는 값만 노드별로 업데이트
	public static void update(int start, int end, int node, int index, int dif) {
		if(index < start || index > end) {
			return;
		}
		
		segtree[node] += dif;
		if(start == end) {
			return;
		}
		
		int mid = (start+end)/2;
		
		update(start, mid, node*2, index, dif);
		update(mid+1, end, node*2+1, index, dif);
	}

}
