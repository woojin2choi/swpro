package middle.day05;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [연습A-0031] 최대 힙 
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVztZzigzGCojUGE&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint nCr은 미리 만들어 놓자, 최상단 루트는 무조건 max여야 하므로 7개로 구성된 트리를 예를 들면,
 *       높이 2짜리 7개노드 트리가 생기는데, 루트는 맥스니까 빠지고
 *       (나머지6개중에서3개를 골라 왼쪽 높이1짜리 서브트리 만드는 경우의수)*(3개중의3개를 골라 오른쪽 높이 1짜리 서브트리 만드는 경우의수)
 *       이걸 재귀로 부른다
 */
public class Solution02 {
	
	private static int T;
	private static int n;
	private static int[][] nCr;
	
	private static final int M = 100000123;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		T = Integer.parseInt(br.readLine());
		
		// 미리 파스칼의 삼각형을 만들어 놓자
		initBionomial(4095);
		
		for(int i=1; i<=T; i++) {
			n = Integer.parseInt(new StringTokenizer(br.readLine()).nextToken());
			
			int depth = n+1;
			long res=1;
			
			res = getCount(depth);
			
			bw.append("#").append(String.valueOf(i)).append(" ").append(String.valueOf(res));
			bw.newLine();
			
		}
		
		bw.flush();
		bw.close();
		br.close();
	}

	
	// 파스칼의 삼각형을 미리만들어 놓자, 문제의 제한조건에서 n이 11이하이기 때문에 n이 최대 4096-1개
	private static void initBionomial(int n) {
		
		nCr = new int[n+1][n+1];
		
		nCr[1][0] = nCr[0][1] = 1;
		
		for(int i=1; i<=n; i++) {
			for(int j=0; j<=i; j++) {
				if(i==j || j==0) {
					nCr[i][j] = 1;
				} else {
					nCr[i][j] = nCr[i-1][j-1]%M + nCr[i-1][j]%M;
				}
			}
		}
	}
	
	// 최대로 만들수 있는 MAX힙의 경우의 수를 구한다.
	private static long getCount(int depth) {
		long res=1;
		
		if(depth==1) {
			return 1;
		}
		
		int newN = (int)Math.pow(2, depth)-2;
		
		//왼쪽 써브트리의 조합개수 * 오른쪽 써브트리 조합개수 * 아래로depth를 1 내리면서 왼쪽한번 오른쪽한번 재귀로
		//D[i] = 높이i인최대힙의개수 = ((2^depth–2) C(2^depth–2) / 2) * D[i–1] * D[i–1]
		//재귀의 값들이 커질수있어서 리턴쪽에도 %M을 넣어준다
		res = (nCr[newN][newN/2]*getCount(depth-1)%M*getCount(depth-1)%M)%M;
		
		if(res<0) {
			res += M;
		}
		return res;
	}

}
