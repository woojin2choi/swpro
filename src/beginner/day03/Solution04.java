package beginner.day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Æ÷±â
 * @author woojin2.choi
 *
 */
public class Solution04 {
	
	static int[][] map;
	static int[][] visited;
	static Stack<String> stack;
	static int[] dx = {-1, -1, -1, 0, 1, 1, 1,  0};
	static int[] dy = {-1,  0,  1, 1, 1, 0, 1, -1};
	static int N;
	
	public static void main(String[] args) throws IOException {

		BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(sb.readLine());
		
		int T = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<T; i++) {
			st = new StringTokenizer(sb.readLine());
			N = Integer.parseInt(st.nextToken());
			
			map = new int[N+1][N+1];
			visited = new int[N+1][N+1];
			stack = new Stack<>();
			for(int j=1; j<=N; j++) {
				st = new StringTokenizer(sb.readLine());
				for(int k=1; k<=N; k++) {
					map[j][k] = Integer.parseInt(st.nextToken());
				}
			}
			
			dfs(1, 1);
			
			StringBuilder stb = new StringBuilder();
			stb.append("#"+(i+1));
			while(!stack.isEmpty()) {
				stb.append(" "+stack.pop());
			}
			System.out.println(stb);
		}
	}
	
	public static void dfs(int x, int y) {
		visited[y][x] = 1;
		stack.push(y+" "+x);
		if(x == N && y == N) {
			return;
		}
		
		for(int i=0; i<8; i++) {
			int new_y = y+dy[i];
			int new_x = x+dx[i];
			if(!(new_x > N || new_y > N) && !(new_x < 1 || new_y <1)
					&& map[new_y][new_x] != 1 && visited[new_y][new_x] != 1) {
				dfs(new_x, new_y);
			}
		}
	}

}
