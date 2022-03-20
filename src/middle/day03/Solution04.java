package middle.day03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [교육P-0031] 최대와 최소 
 * http://182.193.11.65/common/satti/practiceProblem.do?problemId=AWWt-lFA-hGojUGz
 * @author woojin2.choi
 * @hint 구간의 최대 최소 합 이런것은 무조건 세그먼트 트리임
 */
public class Solution04 {
	
	private static int N;
	private static int Q;
	private static int[] minSegTree;
	private static int[] maxSegTree;
	//private static int[] sumSegTree;
	private static int SIZE;
	
	public static void main(String args[]) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int T = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<T; i++) {
			
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			Q = Integer.parseInt(st.nextToken());
			// 완전 이진트리를 만들어야 하기 때문에 다음번 2의 제곱수를 구하는 방법, 밑이 2인 로그는 없기때문에 log e를 위아래로 넣어줘서 log 2를 만들어 쓴다
			SIZE = (int)Math.pow(2, (int)(Math.log(N)/Math.log(2))+1);	
			
			int v=0;
			int cnt=1;
			minSegTree = new int[2*SIZE];
			maxSegTree = new int[2*SIZE];
			
			st = new StringTokenizer(br.readLine());
			for(int x=SIZE; x<2*SIZE; x++) {
				minSegTree[x] = Integer.MAX_VALUE;
				maxSegTree[x] = Integer.MIN_VALUE;
				if(cnt <= N) {					// 세그먼트 트리는 초기화 메소드 같은거 필요없이 그냥 update로 진행해도 성능상 좋음
					v = Integer.parseInt(st.nextToken());
					updateMinSegTree(cnt, v);	// cnt번째의 순서에다가 v값으로 update하라, cnt가 N보다 커지면 초기화만 하라
					updateMaxSegTree(cnt, v);	// cnt번째의 순서에다가 v값으로 update하라, cnt가 N보다 커지면 초기화만 하라
					cnt++;
				}
			}
			
			int q, a, b;
			int min = 0;
			int max = 0;
			for(int j=1; j<=Q; j++) {
				st = new StringTokenizer(br.readLine());
				q = Integer.parseInt(st.nextToken());
				a = Integer.parseInt(st.nextToken());
				b = Integer.parseInt(st.nextToken());
				
				if(q==0) {	// a~b 폐구간의 최대최소 구하고
					min += queryMinSegTree(a, b);
					max += queryMaxSegTree(a, b);
				} else {	// a를 b로 업데이트
					updateMinSegTree(a, b);
					updateMaxSegTree(a, b);
				}
			}
			
			bw.append("#").append(String.valueOf(i+1)).append(" ").append(String.valueOf(max)).append(" ").append(String.valueOf(min));
			bw.newLine();
		}
		bw.flush();
		bw.close();
		br.close();
	}

	// x번째 노드에 v라는 값은 넣고 세그먼트 트리를 업데이트 한다
	private static void updateMinSegTree(int x, int v) {
		int node = x+SIZE-1;	// ex) 2가 들어오면 실제 segTree에서는 9번 노드가 됨
		minSegTree[node] = v;
		for(int i=node/2; i>=1; i=i/2) {
			minSegTree[i] = Math.min(minSegTree[i*2], minSegTree[i*2+1]);
		}
	}
	
	// left부터 right까지의 구간합을 구하는것인데
	// left가 홀수이면 자기자신의 값을 더하고 오른쪽으로 한칸이동 -> 홀/짝 둘중 하나인데, 홀이면 값이 갈라지는 경우이다
	// right가 짝수이면 마찬가지로 자기자신의 값을 더하고 왼쪽으로 한칸이동 -> 이것도 홀/짝 둘중 하나인데, 짝수라는것은 오른쪽값이 세그먼트에 걸쳐있다는 소리임
    private static int queryMinSegTree(int l, int r) {
		int left = l+SIZE-1;
		int right = r+SIZE-1;
		int res = Integer.MAX_VALUE;
		for(; left<=right; left/=2,right/=2) {	// 왼쪽과 오른쪽이 같아질때까지 하는데, 반씩 나눠가면 이진트리 부모로 이동하게 되므로, 감탄을 금치 못하겠음
			if(left%2 == 1) {
				res = Math.min(res, minSegTree[left++]);
			}
			if(right%2 == 0) {
				res = Math.min(res, minSegTree[right--]);
			}
		}
		return res;
	}
	
	// x번째 노드에 v라는 값은 넣고 세그먼트 트리를 업데이트 한다
	private static void updateMaxSegTree(int x, int v) {
		int node = x+SIZE-1;	// ex) 2가 들어오면 실제 segTree에서는 9번 노드가 됨
		maxSegTree[node] = v;
		for(int i=node/2; i>=1; i=i/2) {
			maxSegTree[i] = Math.max(maxSegTree[i*2], maxSegTree[i*2+1]);
		}
	}
	private static int queryMaxSegTree(int l, int r) {
		int left = l+SIZE-1;
		int right = r+SIZE-1;
		int res = Integer.MIN_VALUE;
		for(; left<=right; left/=2,right/=2) {
			if(left%2 == 1) {
				res = Math.max(res, maxSegTree[left++]);
			}
			if(right%2 == 0) {
				res = Math.max(res, maxSegTree[right--]);
			}
		}
		return res;
	}
	
	/* sum할때는 이거 쓰면됨 min, max, sum 모두 방법이 똑같다, 만약 mod연산이 있어도 분배법칙이 되니까 그냥 쓰면되고.. */
	/*
	// x번째 노드에 v라는 값은 넣고 세그먼트 트리를 업데이트 한다
	private static void updateSumSegTree(int x, int v) {
		int node = x+SIZE-1;	// ex) 2가 들어오면 실제 segTree에서는 9번 노드가 됨
		sumSegTree[node] = v;
		for(int i=node/2; i>=1; i=i/2) {
			sumSegTree[i] = sumSegTree[i*2] + sumSegTree[i*2+1];
		}
	}
	
	private static int querySumSegTree(int l, int r) {
		int left = l+SIZE-1;
		int right = r+SIZE-1;
		int res = 0;
		for(; left<=right; left/=2,right/=2) {
			if(left%2 == 1) {
				res = res + sumSegTree[left++];
			}
			if(right%2 == 0) {
				res = res + sumSegTree[right--];
			}
		}
		return res;
	}
	*/
		
}


