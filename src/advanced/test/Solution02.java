package advanced.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [연습P-0019] 구간합 
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVgFI19QAViojUEZ&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 그냥 평범한 인덱스트리
 */
public class Solution02 {
	
	private static int T;
	private static int N;
	private static int Q;
	private static int offset=1;
	private static long[] tree;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			
			N = Integer.parseInt(br.readLine());	//정수의 개수
			Q = Integer.parseInt(br.readLine());	//질의의 개수
			
			//인덱스트리의 offset을 구한다
			while(offset<N) {
				offset = offset*2;
			}
			
			tree = new long[offset*2];	//트리를 생성한다
			
			//트리의 초기값을 세팅한다.
			for(int i=1; i<=N; i++) {
				update(i-1, i);	//i-1리프노드에다가 1,2,3,4이런식으로 업데이트한다
			}
			
			long ans=0;
			for(int i=1; i<=Q; i++) {
				st = new StringTokenizer(br.readLine());
				int q = Integer.parseInt(st.nextToken());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				if(q==0) {
					update(x-1, y);
				} else {
					ans=(ans+query(x-1, y-1))%1000000007;
				}
			}
			
			if(ans<0) {
				ans = (ans%1000000007)+1000000007;
            } else {
            	ans = ans%1000000007;
            }
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(ans));
			bw.newLine();
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
	
	private static void update(int x, int v) {
		int l = x+offset;
		tree[l] = v;
		for(int i=l/2; i>=1; i/=2) {
			tree[i] = tree[i*2]+tree[i*2+1];
		}
	}
	
	private static long query(int l, int r) {
		int left = l+offset;
		int right = r+offset;
		long res = 0;
		for(; left<=right; left/=2, right/=2) {
			if(left%2==1) {
				res += tree[left++];
			}
			if(right%2==0) {
				res += tree[right--];
			}
		}
		return res;
	}

}
