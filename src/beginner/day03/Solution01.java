package beginner.day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;


/**
 * BFS는 처음에 큐에 넣고, 큐에서 꺼내서방문, 인접노드를 추가(이미 발견된 노드는 추가하지 않는다) 다시 큐에서 빼서 방문, 인접노드 추가 이런식
 * 이미 방문한 노드를 체크하는건, 큐에 들어갈때 비지티드에 넣어야한다 꼭, 단순히 큐에 있는지만 확인하면 안되지
 * DFS가 좋을때 -> 갈수있는 길이 되게 많은데 한가지 방법을 찾아내려고 할때(최단경로 찾을때 DFS보다 훨좋음, DFS는 다 찾아봐야하는데(물론 운좋게 찾을수도있고, 약간의 최적화도 할수는 있지만 느림)
 * BFS가 좋을때 -> 시작 지점에서 가장 가까운것? 을 구하고 싶을때, 어느정도 구하고 싶은 해가 근처에 있을때
 * @author woojin2.choi
 *
 */

public class Solution01 {
	
	static int[][] nodes;
	static Queue<Integer> q;
	static int step;
	static int now;
	
	public static void main(String args[]) throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<T; i++) {
			
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken()); //섬의 개수
			int L = Integer.parseInt(st.nextToken()); //운항 한계거리
			L = L*L; // 제곱해두고
			
			nodes = new int[N+1][4];	// x,y,visited
			q = new ArrayDeque<>();
			step = 0;
			now = 0;
			
			for(int j=1; j<=N; j++) {
				st = new StringTokenizer(br.readLine());
				nodes[j][0] = Integer.parseInt(st.nextToken());
				nodes[j][1] = Integer.parseInt(st.nextToken());
				nodes[j][2] = 0;	//visited
				nodes[j][3] = 0;	//step
			}
			
			q.offer(1);
			nodes[1][2] = 1;	// 1번노드 방문했음
			boolean isPos = false; 
			
			while(!q.isEmpty()) {
				now = q.poll();
				
				// 도착했을때
				if(nodes[N][0]==nodes[now][0] && nodes[N][1]==nodes[now][1]) {
					isPos = true;
					break;
				} else {
					for(int k = 1; k<=N; k++) {
                        if(nodes[k][2] == 0
                                && L >= (nodes[now][0]-nodes[k][0])*(nodes[now][0]-nodes[k][0])
                                        + (nodes[now][1]-nodes[k][1])*(nodes[now][1]-nodes[k][1])) {
                            nodes[k][2] = 1;
                            nodes[k][3] = nodes[now][3]+1;
                            q.offer(k);
                        }
                    }
					
				}
			}
			int result = -1;
			if(isPos) {
				result = nodes[now][3];
			} else {
				result = -1;
			}
			
			System.out.println("#" + (i+1) + " " + result);
			
		}
		
	}

}
