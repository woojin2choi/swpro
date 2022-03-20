package advanced.day03;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
/**
 * [기출P-0077] 서버교체 
 * @author woojin2.choi
 * @hint 포기함
 */
public class Solution03 {
 
    static int T, N, M, Q ;
    static long Result;
    static int[][] map;
    static char[][] server;
    static int[] Parent, cnt, watt;
    static ArrayList<Query> qlist;
    static int[] dx = { -1, 1, 0, 0 };
    static int[] dy = { 0, 0, -1, 1 };
 
    static class Query {
        int q, r, c, k;
 
        public Query(int q, int r, int c, int k) {
            this.q = q;
            this.r = r;
            this.c = c;
            this.k = k;
        }
    }
 
    public static void main(String[] args) throws Exception {
 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
 
        st = new StringTokenizer(br.readLine());
        T = Integer.parseInt(st.nextToken());
 
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            Q = Integer.parseInt(st.nextToken());
 
            Result = 0;
            map = new int[N + 1][M + 1];
            server = new char[N + 1][M + 1];
            qlist = new ArrayList<>();
            Parent = new int[N * M + 1];
            cnt = new int[N * M + 1];
            watt = new int[500001];
 
            for (int i = 1; i <= N * M; i++) {
                Parent[i] = i;
                cnt[i] = 1;
            }
            for (int i = 1; i <= N; i++) {
                int idx = 0;
                String str = br.readLine();
                for (int j = 1; j <= M; j++) {
                    server[i][j] = str.charAt(idx++);
                }
            }
 
            int node = 1;
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= M; j++) {
                    if (server[i][j] == 'B') {
                        map[i][j] = node++;
                        watt[1] += 1;
                        Union_server(i, j, 1);
                    }
                }
            }
 
            for (int i = 0; i < Q; i++) {
                st = new StringTokenizer(br.readLine());
 
                int q = Integer.parseInt(st.nextToken());
                if (q == 1) {
                    int r = Integer.parseInt(st.nextToken());
                    int c = Integer.parseInt(st.nextToken());
 
                    qlist.add(new Query(q, r, c, 0));
                } else if (q == 2) {
                    int k = Integer.parseInt(st.nextToken());
                    qlist.add(new Query(q, 0, 0, k));
                }
            }
 
            // B만 처리
            for (Query q : qlist) {
                if (q.q == 1) {
                    map[q.r][q.c] = node++;
                    watt[1] += 1;
                    server[q.r][q.c] = 'B';
                    Union_server(q.r, q.c, 1);
                } else {
                    Result += watt[q.k];
                }
            }
 
            // A만 처리 역순
            map = new int[N + 1][M + 1];
            watt = new int[500001];
            for (int i = 1; i <= N * M; i++) {
                Parent[i] = i;
                cnt[i] = 1;
            }
            node = 1;
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= M; j++) {
                    if (server[i][j] == 'A') {
                        map[i][j] = node++;
                        watt[2] += 1;
                        Union_server(i, j, 2);
                    }
                }
            }
 
            for (int i = qlist.size(); i >= 1; i--) {
                Query q = qlist.get(i - 1);
                if (q.q == 1) {
                    map[q.r][q.c] = node++;
                    watt[2] += 1;
                    server[q.r][q.c] = 'A';
                    Union_server(q.r, q.c, 2);
                } else {
                    Result += watt[q.k];
                }
            }
 
            System.out.println("#" + tc + " " + Result);
        }
 
    }
 
    private static void Union_server(int r, int c, int m) {
        for (int i = 0; i < 4; i++) {
            if (r + dx[i] > 0 && r + dx[i] <= N && c + dy[i] > 0 && c + dy[i] <= M)
                if (map[r + dx[i]][c + dy[i]] != 0) {
                    Union(map[r][c], map[r + dx[i]][c + dy[i]], m);
                }
        }
    }
 
    private static void Union(int a, int b, int m) {
 
        int x = find(a);
        int y = find(b);
 
        if (x != y) {
            Parent[y] = x;
            watt[cnt[x] * m] = watt[cnt[x] * m] - 1;
            watt[cnt[y] * m] = watt[cnt[y] * m] - 1;
            cnt[x] += cnt[y];
            watt[cnt[x] * m] = watt[cnt[x] * m] + 1;
        }
    }
 
    private static int find(int x) {
 
        if (x == Parent[x]) {
            return x;
        } else {
            return Parent[x] = find(Parent[x]);
        }
 
    }
 
}
