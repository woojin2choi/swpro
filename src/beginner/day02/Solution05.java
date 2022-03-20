package beginner.day02;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Solution04의 다른버전?
 * @author woojin2.choi
 *
 */
public class Solution05 {
	
	static int[] N;
	static int[][] R;
	static int[] check;
	static int sum = 0;
	static Map<Integer, Integer> skip;
	

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		//long start= System.currentTimeMillis();
		
		
		int t = sc.nextInt();
		
		for(int i=0; i<t; i++) {
			int nlength = sc.nextInt()+1;
			N = new int[nlength];
			R = new int[nlength][nlength];
			int M = sc.nextInt();
			for(int j=1; j<nlength; j++) {
				N[j] = sc.nextInt();
			}
			for(int k=0; k<M; k++) {
				int x = sc.nextInt();
				int y = sc.nextInt();
				R[x][y] = 1;
				R[y][x] = 1;
			}
			
			int max=0;
			skip = new HashMap<Integer, Integer>();
			for(int z=1; z<nlength; z++) {
				//z와 연결된 애들중에서 같은색이면서 연결된 모든 리스트를 뽑아서 얘는 제외하자
				if(!skip.containsKey(z)) {
					check = new int[nlength];
					process(z);
					if(max<sum) max = sum;
					
				}
				sum = 0;
			}
			System.out.println("#"+(i+1)+" "+max);
			
		}
		//long end = System.currentTimeMillis();
		//System.out.println("경과시간 : "+String.format("%d msec", end-start));
	}
	
	public static void process(int n) {
		check[n]=1;
		sum++;
		skip.put(n, 1);
		for(int i=1; i<R[n].length; i++) {
			if(R[n][i] == 1 && check[i] != 1 && n!=i) {
				if(N[n] != N[i]) {
					if(N[n] == 2) {
						process(i);
					}
					else if(N[n] == 0 && N[i] == 2 || N[n] == 1 && N[i] == 2 ) {
						process(i);
					} else {
						continue;
					}
				} else {
					process(i);
				}
				
			}
		}
	}
}
