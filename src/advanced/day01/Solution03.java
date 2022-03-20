package advanced.day01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * [기출P-0088] [2021년 01월 24일] 화살 
 * http://182.193.11.65/common/practice/problem/view.do?problemId=AXc2jKH2YajBC0Gg
 * @author woojin2.choi
 * @hint 인덱스트리로 풀되, 빌딩을 높은순+번호가낮은순으로 정렬하고나서 빌딩이 높은순으로 인덱스 트리에 인서트한다
 *       인서트한다음에 자신보다 작은 빌딩의 갯수+힘+1의 순번의 빌딩이 점수가 되는것
 */
public class Solution03 {
	
	private static int T;
	private static int N; //건물의수
	private static int offset = 1;
	private static Building[] Buildings;
	private static int[] P;
	private static int[] tree;
		
	private static class Building{
		int index;
		int height;
		Building(int index, int height){
			this.index = index;
			this.height = height;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			
			
			
			//건물갯수, 건물갯수의 마지막 2의제곱수가 오프셋이고 이 값부터 리프노드값을 넣으면 된다.
			N = Integer.parseInt(br.readLine());
			
			Buildings = new Building[N];	//건물높이
			Buildings[0] = new Building(0,0);
			P = new int[N+1];	//화살파워
			
			// 건물높이와 번호 배열 저장
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<N; i++) {
				Buildings[i] = new Building(i, Integer.parseInt(st.nextToken()));
			}
			// 화살파워 배열 저장
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<N; i++) {
				P[i] = Integer.parseInt(st.nextToken());
			}
			
			// 인덳스트리의 오프셋을 구함
			while(offset < N) {
				offset = offset * 2;
			}
		
			
			tree = new int[2*offset];	//인덱스트리생성
			
			// Buildings배열을 정렬하는데, 키가큰것, 키가 같으면 인덱스가 작은것 순으로 정렬!
			Arrays.sort(Buildings, new Comparator<Building>() {
				@Override
				public int compare(Building o1, Building o2) {
					if(o1.height == o2.height) {
                        return o1.index - o2.index;
                    } else {
                        return o2.height - o1.height;
                    }

				}
			});
			
			long ans = 0;
			
			for(int i=0; i<N; i++) {
                Building building = Buildings[i];
                int min = getLowerBuilding(offset, offset + building.index);
                ans = ans + getBuilding(min + P[building.index] + 1);
                update(offset + building.index);
            }
			
			bw.append("#").append(String.valueOf(tc)).append(" ").append(String.valueOf(ans));
			bw.newLine();
		}
		bw.flush();
		bw.close();
		br.close();  
		
	}
	
	private static void update(int pos) {
		while(pos >= 1) {
			 tree[pos] = tree[pos] + 1;
			 pos = (pos / 2);
		}
	}

	// 인덱스 트리에서 나보다 구간의 갯수를 구하는것
	// offset~offset+building.index까지의 갯수를 구하는것은 현재 나보다 작은 빌딩이 몇개인가를 알기위함이다.
	// 나보다 작은 빌딩이 있으면, 그 지점빌딩 부터 화살파워로 뚫고 가는 갯수 + 1개 하면 점수가 되는것이다
	// 작은빌딩은 화살이 안맞으니까 나 자신보다 높은 빌딩만 화살파워로 뚫고 가는거임 -> 인덱스 트리에 높은빌딩부터 삽입하는 이유가 그것임
	private static int getLowerBuilding(int left, int right) {
		int ans = 0;
		for(;left<=right;left/=2,right/=2) {
			if(left%2 == 1) {
				ans = ans + tree[left++];
			}
			if(right%2 == 0) {
				ans = ans + tree[right--];
			}
		}
		return ans;
	}
	
	// 인덱스 트리에서 현재 삽입된 빌딩들 중에서 몇번째 빌딩의 포지션이 무엇인가?
	private static int getBuilding(int nth) {
		int pos = 1;
		if(tree[pos] < nth) {
			return 0;
		} else {
			while(pos < offset) {
				if(tree[pos*2] >= nth) {
					pos = pos*2;
				} else {
					nth -= tree[pos*2];
					pos = pos*2+1;
				}
			}
			return pos-offset+1;
		}
	}
	
}
