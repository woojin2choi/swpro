package middle.day09;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * [기출P-0027] 기름 값
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AWGIpxTgwQOojUEb&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 이번 도시에 왔을때 지난번 도시에서 넉넉하게 채워온 기름값이랑 지금 도시에서 추가로 기름을 넣었을때랑 
 *       기름통 L에 채워진 용량별로 최소값을 구해나가고, 이 최소값을 이용해서 또 최소값을 구하고... 반복하자
 */
public class Solution03 {
	
	private static int T;
	private static int N;	// 도시의 수
	private static int L;	// 기름통 용량
	private static int[] cost;	// 도시별 기름값
	private static int[] dist;	// 도시의간 이동거리 dist[0] = 0이다 첫번째 도시에 올때까지의 거리니까 0으로 세팅, dist[1] -> 0번 도시에서 1번 도시로의 거리
	private static int[][] DP;
	
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc<=T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());
			
			cost = new int[N+1];	// 1번도시부터 시작
			dist = new int[N+1];
			
			st = new StringTokenizer(br.readLine());
			for(int i=1; i<N; i++) {
				if(i!=N) {
					cost[i] = Integer.parseInt(st.nextToken());
					dist[i] = Integer.parseInt(st.nextToken());
				} else {
					cost[i] = Integer.parseInt(st.nextToken());
				}
			}
			
			DP = new int[N+1][L+1];
			for(int i=1; i<=L; i++) {
				DP[1][i] = cost[1]*i;
			}
			
			for(int i=2; i<=N; i++) {
				if(dist[i-1] > L) {	// 다음 도시로 갈수없는 경우
					DP[N][0] = -1;
					break;
				}
				DP[i][0] = DP[i-1][dist[i-1]];
				
				for(int j=1; j<=L; j++) {
					DP[i][j] = DP[i][j-1]+cost[i];
					if(j+dist[i-1] <= L) {
						DP[i][j] = Math.min(DP[i][j], DP[i-1][j+dist[i-1]]);
					}
				}
			}
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(DP[N][0]));
			bw.newLine();
			
		}
		bw.flush();
		bw.close();
		br.close();  
	}
}

/*
샘플인풋 아래를 넣으면 25가 나와야함
1
4 8
3 5 1 3 2 6 7 

위의 예제를 기준으로 표를 그리자, 먼저 기름통을 최대로 넣을수있을때에 따른 도시별 기름값 최소값을 갱신한다고 생각하자
왜냐, 예를들어 2번 도시에 도착했을때 기름이 1L 남는경우 어디서 넣는게 최소비용인가?
그럼 이전 1번도시에서 1L를 더 넣는것이랑 2번도시까지 와서 기름이 앵꼬나고 2번도시에서 1L를 넣는것 둘중의 하나에서 더 적은 기름값이 드는것인데
그걸 점화식으로 표현하는것이다.

DP[현재도시][남은기름] = DP[이전도시][1L초과하여주유] vs DP[이전도시][엥꼬나게주유] + 현재도시에서 1L넣기
이 값중에서 작은값으로 DP배열을 업데이트 해나간다, 나중에 DP의 DP[i][j]는 돌이켜봤을때 무조건 최소가된다
왜냐면 최소로 구한거 vs DP[이전도시][엥꼬나게주유] + 현재도시에서 1L넣기 이기 때문이다 -> DP의 성질

이 문제의 표를 구하자면
	0	1	2	3	4	5	6	7	8
1	0	3	6	9	12	15	18	21	24	
2	15	16	17	18	19	20	21	22	23
3	18	19	20	21	22	23	25	27	29
4	25

DP[1][i]는 첫 도시의 주유소 금액별로 1~8L까지 채웠을때 금액을 쭉 넣은것이다.
DP[2][0]은 두번째 도시에 도착했을때 "첫번째도시에서 5L만 채웠을때의 값"이다.
DP[2][1]은 첫번째 도시에서 6L넣었을때 vs 첫번째도시에서 5L넣고 두번째도시에서 1L넣었을때
                  즉, DP[1][6] vs DP[2][0]+두째도시cost
DP[2][2]는 첫번째 도시에서 7L넣었을때 vs 첫번째도시에서 5L넣고 두번째도시에서 2L넣었을때
                  근데 이건 바로 앞에 계산한 DP[2][1]에서 cost한번 더한거랑 같으니까
  DP[i][j] = DP[i][j-1]+cost[i];
  if(j+dist[i-1] <= L) {
	DP[i][j] = Math.min(DP[i][j], DP[i-1][j+dist[i-1]]);
  }
  이런 점화식이 나올수있는것이다
 때에따라 j+dist[i-1] 이 값이 L을 넘어서는 경우가 있따
 이건 주유용량을 초과하는 최소값이 없기때문에 이건 그냥 바로 앞의 값을 쓰면되는것이다. 
 DP[N][0]값을 구하면 N번째 도시에 왔을때 기름이 하나도 없는 상태면 최소로 지출하고 온것이다
*/
