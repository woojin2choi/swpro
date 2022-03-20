package beginner.day02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution02 {

    static boolean visit[];
    static int[] result;
    static ArrayList<Integer>[] al;
    static int maxDepth;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            al = new ArrayList[N+1];
            visit = new boolean[N+1];
            result = new int[N+1];

            for (int i = 0; i < N + 1; i++) {
                al[i] = new ArrayList<>();
            }

            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                al[from].add(to);
            }
            
            for (int i = 1; i < N+1; i++) {
            	maxDepth=1;
                dfs(i, 1);
                result[i] = maxDepth;
            }

            StringBuilder sb = new StringBuilder();
			sb.append("#"+tc);
			for(int j=1;j<result.length;j++) {
				sb.append(" "+result[j]);
			}
			sb.append("\n");
			System.out.println(sb);
        }

    }

    static void dfs(int idx, int depth) {
        if(maxDepth < depth) {
        	maxDepth = depth;
        }
                
        for (int next : al[idx]) {
        	dfs(next, depth+1);
        }
        
    }

}