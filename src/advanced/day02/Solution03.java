package advanced.day02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 알고리즘 경진대회
 * @author woojin2.choi
 *
 */
public class Solution03 {
	
	private static int T;
	private static int N;
	private static int[] Gi;
	private static int offset=1;
	private static int[] tree;
	private static long[] sum;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			
			N = Integer.parseInt(br.readLine());
			
			//인덱스 트리를 구하기 위한 오프셋을 구한다.
			while(offset < N) {
				offset = offset*2;
			}
			
			tree = new int[offset*2];	//트리생성=오프셋의두배
			
			sum = new long[N+1];
			
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<N; i++) {
				int ai = Integer.parseInt(st.nextToken());
				updateGcdTree(i, ai);	//입력받으면서 트리를 구성
				sum[i+1] = ai + sum[i];
			}
			
			Gi = new int[N];
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<N; i++) {
				Gi[i] = Integer.parseInt(st.nextToken());
			}
			
			long ans=0;
			for(int i=0; i<N; i++) {
				int l = Math.max(i-Gi[i], 0);
				int r = Math.min(i+Gi[i], N-1);
				int gcd = queryGcdTree(l, r);
				ans += (sum[r+1]-sum[l])/gcd;
			}
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(ans));
			bw.newLine();
		}
		bw.flush();
		bw.close();
		br.close();  
		
	}
	
	
	// 유클리드 호제법
	// a와 b중에서 큰수를 a로 작은수를 b로 하고 계속 나눌때, 나머지가 0이되면 나누는수(b)가 최대공약수이다
	private static int euclid(int a, int b) {
		int gcd = 0;
		if(a==0 && b!=0) return gcd=b;
		if(a!=0 && b==0) return gcd=a;
		
		if(a<b) {
			int temp = a;
			a = b;
			b = temp;
		}
		int res = 0;
		
		while(true) {
			res = a%b;
			if(res == 0) {
				gcd = b;
				break;
			} else {
				a = b;
				b = res;
			}
		}
		return gcd;
	}
	
	// x번째 노드에 v라는 값은 넣고 세그먼트 트리를 업데이트 한다
	private static void updateGcdTree(int x, int v) {
		int node = offset+x;	// ex) 2가 들어오면 실제 segTree에서는 9번 노드가 됨
		tree[node] = v;
		for(int i=node/2; i>=1; i=i/2) {
			tree[i] = euclid(tree[i*2], tree[i*2+1]);
		}
	}
	
	private static int queryGcdTree(int l, int r) {
		int left = l+offset;
		int right = r+offset;
		int res = 0;
		for(; left<=right; left/=2,right/=2) {	// 왼쪽과 오른쪽이 같아질때까지 하는데, 반씩 나눠가면 이진트리 부모로 이동하게 되므로, 감탄을 금치 못하겠음
			if(left%2 == 1) {
				res = euclid(res, tree[left++]);
			}
			if(right%2 == 0) {
				res = euclid(res, tree[right--]);
			}
		}
		return res;
	}
}
