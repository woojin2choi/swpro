package middle.day04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;


/**
 * [연습A-0022] 소수경로
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AVgE01LQ7nyojUEZ&_menuId=AVUU732mAAHBC0c9&_menuF=true
 * @author woojin2.choi
 * @hint 소수 리스트는 먼저 구해놓자(에라토스테네스의 체), 1000의자리 숫자부터 숫자를 0-9까지 모두 바꿔가면서 소수이면 큐에넣고 bfs를 돌린다. 모든 해를 구해보고 depth를 구하면된다.
 * @hint A부터 B까지 depth를 구할때에 visited배열에서 이전값+1의 방식으로 depth를 구하자
 */
public class Solution01 {
	
	private static int T;
	private static int A;
	private static int B;
	
	private static List<Boolean> primeList;
	private static int[] visited;
	

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine());
		
		makePrimeList(1000, 9999);
		
		
		for(int i=1; i<=T; i++) {
			
			visited = new int[9999+1];
			for(int j=0; j<visited.length; j++) {
				visited[j] = -1; 
			}
			
			st = new StringTokenizer(br.readLine());
			
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			
			bfs(A);
			
			bw.append("#").append(String.valueOf(i)).append(" ").append(String.valueOf(visited[B]));
			bw.newLine();
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
	
	
	private static void bfs(int node) {
		Deque<Integer> queue = new ArrayDeque<>();
		queue.add(node);
		visited[node] = 0;
		
		while(!queue.isEmpty()) {
			int prime = queue.poll();
			
			for(int i=0; i<4; i++) {
				for(int j=0; j<10; j++) {	
					int newPrime = getNewNum(prime, i, j);
					if(primeList.get(newPrime)==true && newPrime >= 1000 && visited[newPrime] == -1) {
						queue.add(newPrime);
						visited[newPrime] = visited[prime]+1;	//비지티드를 이용해서 depth를 구하자
					}
				}
			}
		}
	}
	
	
	private static int getNewNum(int num, int index, int newNum) {
		
		int[] res = new int[4];
		res[0] = num/1000;
		res[1] = (num/100)%10;
		res[2] = ((num/10)%100)%10;
		res[3] = (num%10);
		
		if(index == 0) {
			res[0] = newNum;
		}
		if(index == 1) {
			res[1] = newNum;
		}
		if(index == 2) {
			res[2] = newNum;
		}
		if(index == 3) {
			res[3] = newNum;
		}
		
		return res[0]*1000+res[1]*100+res[2]*10+res[3];
	}
	
	//에라토스테네스의 체
	private static void makePrimeList(int start, int end) {
		primeList = new ArrayList<>();
		
		primeList.add(0, false);
		primeList.add(1, false);
		
		for(int i=2; i<=end; i++) {
			primeList.add(i, true);	//우선 모두 소수라고 하고 지우자
		}
		
		// 각각의 배수들을 지워간다.
		for(int i=2; (i*i)<=end; i++){
			if(primeList.get(i)){
				for(int j = i*i; j<=end; j+=i) {
					primeList.set(j, false);	//소수가 아닌 배수들은 지운다
				}
			}
		}
		
	}
	
}
