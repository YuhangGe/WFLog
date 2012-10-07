import java.util.ArrayList;


public class Solver {
	private static int[][] mat = null;
	private static ArrayList<int[]> ans = null;
	
	public static void print(int[][] mat) {
		for(int i=0;i<mat.length;i++) {
			int[] _m = mat[i];
			for(int j=0;j<_m.length;j++) {
				System.out.print(_m[j]);
				System.out.print(",");
			}
			System.out.println();
		}
	}
	public static ArrayList<int[]> solve(int[][] mat) {
		
		if(mat.length==0){
			return null;
		}
		
		print(mat);
		
		Solver.mat = mat;
		ans = new ArrayList<int[]>();
		/*
		 * ����ʹ�ø�˹��Ԫ���Ծ�����г��ȱ仯תΪ�����Ǿ���
		 */
		int row_num = mat.length, col_num = mat[0].length;
		
		int r = 0;
		for(;r<row_num;r++) {
			
			System.out.println("-----row:"+ r +"-------");
			
			//���mat[r][r]=0������һ��r2>r����mat[r2][r]!=0,���ҽ���������
			//���û���ҵ�r2��˵����˹��Ԫ���ܽ��е��ס����� null
			if(mat[r][r]==0) {
				int r2 = -1;
				for(int j=r+1;j<row_num;j++) {
					if(mat[j][r]!=0){
						r2 = j;
						break;
					}
				}
				if(r2<0) {
					if(r== Math.min(col_num, row_num)-1) {
						/*
						 * ���r== Math.min(col_num, row_num)-1˵����r�п�ʼ�����ÿһ�ж�ȫΪ0����˵����Ԫ��ǰ������
						 */
						break;
					} else {
						/*
						 * �������û��Ԥ�ϵ������⣬�����о����
						 */
						System.err.println("��˹��Ԫ���ܽ��е���");
						return null;
					}
				} else {
					/*
					 * ����r��r2����
					 */
					for(int j=0;j<col_num;j++){
						int _t = mat[r][j];
						mat[r][j] = mat[r2][j];
						mat[r2][j] = _t;
					}
				}
			}
			for(int j=r+1;j<row_num;j++) {
				double d = (double)mat[j][r] / (double)mat[r][r];
				for(int k=r;k<col_num;k++) {
					mat[j][k] -= mat[r][k] * d;
					int _check = mat[j][k];
					if(_check!=0 && _check!=1 && _check!=-1) {
						System.err.println("�����м䲻Ϊ0,-1,1�����");
					}
				}
			}
			
			int mr = mat[r][r];
			for(int j=r;j<col_num;j++) {
				mat[r][j] /= mr;
			}
			print(mat);
		}
		
		System.out.println("--------------");
		
		print(mat);
		
		System.out.println("last row:" + (r-1));
		/*
		 * Ȼ��
		 */
		for(int r2 = r-1;r2>=0;r2--) {
			if(r2 < row_num - 1) {
				/*
				 * ���������Ԫ�����һ�У����������еĽ���ϵ������С�
				 */
				for(int r3 = r2+1;r3<r;r3++) {
					int _t = mat[r][r3];
					for(int j=r;j<col_num;j++) {
						mat[r2][j] += _t * mat[r3][j];
					}
				}
				
			} else {
				System.out.println("something strange. it's not last row");
			}
			for(int j=r;j<col_num;j++) {
				mat[r2][j] = -mat[r2][j];
			}
		}
		
		
		System.out.println("--------------");
		
		print(mat);
		
		if(r>=col_num) {
			System.err.println("ȫΪ0...");
			return null;
		}
		/*
		 * Ȼ�� col_num - r �������Ĳ�ȷ��δ֪��ȡ0��1�������  
		 */
		System.out.println("unknown var:" + (col_num - r));
		
		/*
		 * ʹ��������������õ������������
		 */
		temp  = new int[col_num];
		var_start_idx = r;
		calc(r, r, col_num);
		
		for(int i=0;i<ans.size();i++) {
			int[] _t = ans.get(i);
			System.out.print("ans-"+i+"\t:");
			for(int j=0;j<_t.length;j++){
				System.out.print(_t[j]);
				System.out.print(',');
			}
			System.out.println("\n");
		}
		return ans;
		
	}
	private static int[] temp;
	private static int var_start_idx;
	private static void calc(int start, int cur, int end) {
		if(cur>=end) {
			for(int r=var_start_idx-1;r>=0;r--) {
				for(int i=var_start_idx;i<end;i++) {
					temp[r] = mat[r][i] * temp[i];
					if(temp[r]!=0 && temp[r] !=1) {
						return;
					}
				}
			}
			
			boolean _all_zero = true;
			for(int i=0;i<end;i++) {
				if(temp[i] == 1){
					_all_zero = false;
					break;
				}
			}
			if(_all_zero)
				return;
			int[] _n = temp.clone();
			ans.add(_n);
		} else {
			temp[cur] = 0;
			calc(start, cur+1,end);
			temp[cur] = 1;
			calc(start, cur+1, end);
		}
	}
}
