package advanced.day04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * [기출P-0070] 조약돌 게임 
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AXQoWJ7wZJyojUHh&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 위상정렬로 푸는것이라는데 난 못품
 */
public class Solution03 {
    static int N, M, K, a, b, p, q, nodeK, cnt, cur;
    static long result;
    static ArrayList<Integer> adjList[];
    static int indegree[];
 
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
 
        int T = Integer.parseInt(br.readLine().trim());
 
        for (int testCase = 1; testCase <= T; testCase++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
 
            adjList = new ArrayList[N + 1];
            indegree = new int[N + 1];
 
            for (int i = 1; i <= N; i++) {
                adjList[i] = new ArrayList<>();
            }
 
            result = 0;
 
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                a = Integer.parseInt(st.nextToken());
                b = Integer.parseInt(st.nextToken());
                p = Integer.parseInt(st.nextToken());
                q = Integer.parseInt(st.nextToken());
                // 연결선에서 더 작은 점수를 획득하는 경우 들어오는 선, 더 큰 점수를 획득하는 경우 나가는 선으로 구분
                // 간선이 큰 점수 -> 작은 점수를 향하게 되는 유향간선으로 일괄 변환 
                // when p < q then a <- b
                // when p > q then a -> b
                if (p < q) {
                    // a <- b
                    indegree[a]++;
                    adjList[b].add(a);
                    result += q;
                } else if (p > q) {
                    // a -> b
                    indegree[b]++;
                    adjList[a].add(b);
                    result += p;
                } else {
                    // p,q가 동일할 경우 하면 결과값에 합산 (어떤 조약돌을 뽑아도 그 값을 가지므로, 최대 점수에 처음부터 합산)
                    result += p;
                }
            }
 
             
            PriorityQueue<Integer> pq = new PriorityQueue<>();
 
            for (int i = 1; i <= N; i++) {
                //indegree 0인 노드들 pq에 추가
                if (indegree[i] == 0) {
                    pq.add(i);
                }
            }
 
            cnt = 0;
            while (!pq.isEmpty()) {
                cur = pq.poll();
                cnt++;
                if (cnt == K) {
                    nodeK = cur;    //k번째 조약돌 정보
                    break;
                }
                //현재노드의 연결된 간선을 탐색하며 연결된 노드의 indegree 값을 감소, indegree 0인 노드들 pq에 추가
                for (int next : adjList[cur]) { 
                    if (--indegree[next] == 0) {
                        pq.offer(next);
                    }
                }
            }
            System.out.println("#" + testCase + " " + result + " " + nodeK);
        }
    }
}
