package beginner.day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

//http://182.193.11.65/common/practice/problem/view.do?problemId=AWeCFeHgm_2ojUGl&_menuId=AVUU732mAAHBC0c9&_menuF=true
class Solution02 {
	
	static int[] check;
	static int[][] keyList;
	static List<int[]> result;
	static int[] resu;
	static int binomTD[][] = new int[11][11]; 
	

	public static void main(String[] args) throws IOException {
		
		keyList = new int[36][6];
		
		init();
		        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int t = Integer.parseInt(st.nextToken());
        
		resu = new int[t];
		
		
		for(int i=0; i<t; i++) {
			
			for(int j=0; j<=9; j++) {
				for(int k=0; k<=9; k++) {
					binomTD[j][k] = -1;
				}
			}
			
			check = new int[36];
			result = new ArrayList<int[]>();
			
			String date = br.readLine();
			date = date.replaceAll(":", "").replace("/","").replace(" ","");
			int start = (Integer.parseInt(date.substring(2, 4))+Integer.parseInt(date.substring(8, 10)))%36;
			int end = (Integer.parseInt(date.substring(0, 2))+Integer.parseInt(date.substring(4, 6))+Integer.parseInt(date.substring(6, 8)))%36;
			int numcnt = Integer.parseInt(date.substring(6, 7));
			int uppercnt = Integer.parseInt(date.substring(8, 9));
			
			int[] temp = new int[11];
			findway(start, end, 9, temp, numcnt, uppercnt, i);
			
			System.out.println("#"+(i+1)+" "+resu[i]);
			
			

		}
	}
	
	public static int binomialTD(int n, int r) {
		if(n == r || r == 0) {
			return 1;
		}
		else {
			binomTD[n][r] = binomialTD(n-1, r-1) + binomialTD(n-1, r);
			return binomTD[n][r];
		}
	}
	
	public static boolean findway(int start, int end, int length, int[] temp, int numcnt, int uppercnt, int t) {
		
		temp[9-length] = start;
		check[start]++;
		
		if(length <= 0) {
			check[start]--;
			if(start == end) {
				int[] c = new int[10];
				int cnt=0;
				int ucnt=0;
				int dup=0;
				for(int i=0; i<10; i++) {
					c[i]=temp[i];
					if(temp[i]>=0 && temp[i]<=9) {
						cnt++;
					}
					if(temp[i]>=10 && temp[i]<=35) {
						for(int j=i+1; j<10; j++) {
							if(temp[j]==temp[i]) {
								dup++;
							}
						}
						ucnt++;
					}
				}
				if(cnt>=numcnt && ucnt-dup >= uppercnt) {
					if(dup <= uppercnt) {
						if(dup==0) {
							resu[t] += binomialTD((10-cnt),uppercnt);
							result.add(c);
							return true;
						}
						else {
							resu[t] += binomialTD((10-cnt)-(dup*2),uppercnt-dup)*Math.pow(2, dup);
							result.add(c);
							return true;
						}
					}
					else {
						return false;
					}
				}
				else {
					return false;
				}
			} else {
				return false;
			}
		}
		
		boolean res = false;
		boolean rest[] = new boolean[6];
		for(int i=0; i<6; i++) {
			if(keyList[start][i] >= 0 && keyList[start][i] <= 9) {
				if(keyList[start][i] != -1 && check[keyList[start][i]]<1) {
					int next = keyList[start][i];
					res = findway(next, end, length-1, temp, numcnt, uppercnt, t);
				}
			}
			else if(keyList[start][i] >= 10 && keyList[start][i] <= 35) {
				if(keyList[start][i] != -1 && check[keyList[start][i]]<2) {
					int next = keyList[start][i];
					res = findway(next, end, length-1, temp, numcnt, uppercnt, t);
				}
			}
			rest[i] = res;
		}
		
		check[start]--;
		
		if(rest[0]==false && rest[1]==false && rest[2]==false && rest[3]==false && rest[4]==false && rest[5]==false) {
			return false;
		} else {
			return true;
		}
		
	}
	
	
	public static void init() {
		//-1초기화
		for(int i=0; i<=35; i++) {
			Arrays.fill(keyList[i], -1);
		}
		
		//좌우넣기
		for(int i=0; i<=35; i++) {
			if(i==0 || i==10 || i==20 || i==29) {
				keyList[i][0] = i+1;
			} else if(i==9 || i==19 || i == 28 || i==35) {
				keyList[i][0] = i-1;
			}
			else {
				keyList[i][0] = i-1;
				keyList[i][1] = i+1;
			}
		}
		//위쪽두개 넣기
		for(int i=10; i<36; i++) {
			if(i>=10 && i<=19) {
				if(i==19) {
					keyList[i][2] = i-10;
				} else {
					keyList[i][2] = i-10;
					keyList[i][3] = i-9;
				}
			} else if(i>=20 && i<=28) {
				keyList[i][2] = i-10;
				keyList[i][3] = i-9;
			} else {
				keyList[i][2] = i-9;
				keyList[i][3] = i-8;
			}
		}
		
		//아래쪽 두개 넣기
		for(int i=0; i<29; i++) {
			if(i>=0 && i<=19) {
				if(i==0||i==10||i==20) {
					keyList[i][4] = i+10;
				} else if(i==19) {
					keyList[i][4] = i+9;
				}
				else {
					keyList[i][4] = i+9;
					keyList[i][5] = i+10;
				}
			}
			if(i>=20 && i<=28) {
				if(i==20) {
					keyList[i][4] = i+9;
				} else if(i==27) {
					keyList[i][4] = i+8;
				} else if(i==28) {
					continue;
				} else {
					keyList[i][4] = i+8;
					keyList[i][5] = i+9;
				}
			}
		}
	}

}
