package beginner.day01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution04 {
	
	static int R;
	static int C;
	static int[][] arr;
	static int jub=0;
	static String minMax="";
	
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int i=0; i<T; i++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			
			jub = 0;
			
			arr = new int[R][C];
			for(int j=0; j<R; j++) {
				st = new StringTokenizer(br.readLine());
				for(int k=0; k<C; k++) {
					arr[j][k] = Integer.parseInt(st.nextToken());
				}
			}
			
			System.out.println("#"+(i+1)+" "+jub+" "+minMax);
			
			minMax="";
			
		}
	}
	
	public static int[][] foldArray(int[][] arr, int R, int C){
		int[] r = minMax(arr);
		minMax += r[1]+" "+r[0]+" ";
		
		if(R%2==1 && C%2==1) {	//µÑ´ÙÈ¦¼ö
			return arr;
		} else {
			if(R%2==0 && C%2==0) {	//µÑ´ÙÂ¦¼ö
				for(int i=0; i<R; i++) {
					for(int j=0; j<C/2; j++) {
						arr[i][j] += arr[i][C/2+j];
					}
				}
				for(int i=0; i<R/2; i++) {
					for(int j=0; j<C/2; j++) {
						arr[i][j] += arr[R/2+i][j];
					}
				}
				jub+=2;
				return foldArray(arr, R/2, C/2);
			}
			if(R%2==1 && C%2==0) {	//ÇàÀÌ È¦¼ö
				for(int i=0; i<R; i++) {
					for(int j=0; j<C/2; j++) {
						arr[i][j] += arr[i][C/2+j];
					}
				}
				jub+=1;
				return foldArray(arr, R, C/2);
			}
			if(R%2==0 && C%2==1) {	//¿­ÀÌ È¦¼ö
				for(int i=0; i<R/2; i++) {
					for(int j=0; j<C/2; j++) {
						arr[i][j] += arr[R/2+i][j];
					}
				}
				jub+=1;
				return foldArray(arr, R/2, C);
			}
		}
		return arr;
		
	}
	
	public static int[] minMax(int[][] temp) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for(int i=0; i<temp.length; i++) {
			for(int j=0; j<temp[i].length; j++) {
				if(min>temp[i][j]) {
					min = temp[i][j];
				}
				if(max<temp[i][j]) {
					max = temp[i][j];
				}
			}
		}
		int[] ret = new int[2];
		ret[0] = min;
		ret[1] = max;
		return ret;
	}
	
}
