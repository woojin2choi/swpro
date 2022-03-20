package beginner.day01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.StringTokenizer;

public class Solution03 {

	static int[][][] o1 = {
			{
				{0,0,1,0,0},
				{0,0,1,0,0},
				{0,0,1,0,0},
				{0,0,1,0,0},
				{0,0,1,0,0}
			},
			{
				{0,0,0,0,0},
				{0,0,0,0,0},
				{1,1,1,1,1},
				{0,0,0,0,0},
				{0,0,0,0,0}
			}
	};
	static int[][][] o2 = {
			{
				{1,1,1,1,1},
				{0,0,0,0,1},
				{1,1,1,1,1},
				{1,0,0,0,0},
				{1,1,1,1,1}
			},
			{
				{1,1,1,0,1},
				{1,0,1,0,1},
				{1,0,1,0,1},
				{1,0,1,0,1},
				{1,0,1,1,1}
			}
	};
	static int[][][] o3 = {
			{
				{1,1,1,1,1},
				{0,0,0,0,1},
				{1,1,1,1,1},
				{0,0,0,0,1},
				{1,1,1,1,1}
			},
			{
				{1,0,1,0,1},
				{1,0,1,0,1},
				{1,0,1,0,1},
				{1,0,1,0,1},
				{1,1,1,1,1}
			},
			{
				{1,1,1,1,1},
				{1,0,0,0,0},
				{1,1,1,1,1},
				{1,0,0,0,0},
				{1,1,1,1,1}
			},
			{
				{1,1,1,1,1},
				{1,0,1,0,1},
				{1,0,1,0,1},
				{1,0,1,0,1},
				{1,0,1,0,1}
			}
	};
	static int[][][] o4 = {
			{
				{1,0,1,0,0},
				{1,0,1,0,0},
				{1,1,1,1,1},
				{0,0,1,0,0},
				{0,0,1,0,0}
			},
			{
				{0,0,1,1,1},
				{0,0,1,0,0},
				{1,1,1,1,1},
				{0,0,1,0,0},
				{0,0,1,0,0}
			},
			{
				{0,0,1,0,0},
				{0,0,1,0,0},
				{1,1,1,1,1},
				{0,0,1,0,1},
				{0,0,1,0,1}
			},
			{
				{0,0,1,0,0},
				{0,0,1,0,0},
				{1,1,1,1,1},
				{0,0,1,0,0},
				{1,1,1,0,0}
			}
	};
	static int[][][] o5 = {
			{
				{1,1,1,1,1},
				{1,0,0,0,0},
				{1,1,1,1,1},
				{0,0,0,0,1},
				{1,1,1,1,1}
			},
			{
				{1,0,1,1,1},
				{1,0,1,0,1},
				{1,0,1,0,1},
				{1,0,1,0,1},
				{1,1,1,0,1}
			}
	};
	
	
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int i=0; i<T; i++) {
			int[][] temp = new int[5][5];
			for(int j=0; j<5; j++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int k=0; k<5; k++) {
					String num = st.nextToken();
					temp[j][k] = Integer.parseInt(num);
				}
			}

			int result=0;
			if(compareArray(o1, temp)) {
				result=1;
			} 
			if(compareArray(o2, temp)) {
				result=2;
			}
			if(compareArray(o3, temp)) {
				result=3;
			}
			if(compareArray(o4, temp)) {
				result=4;
			}
			if(compareArray(o5, temp)) {
				result=5;
			}
			
			System.out.println("#"+(i+1)+" "+result);
		}
		
	}
	
	public static boolean compareArray(int[][][] a, int[][] b) {
		for(int i=0; i<a.length; i++) {
			if(Objects.deepEquals(a[i], b)) {
				return true;
			}
		}
		return false;
	}
	
}
