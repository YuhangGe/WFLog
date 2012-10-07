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
		 * 首先使用高斯消元法对矩阵进行初等变化转为上三角矩阵
		 */
		int row_num = mat.length, col_num = mat[0].length;
		
		int r = 0;
		for(;r<row_num;r++) {
			
			System.out.println("-----row:"+ r +"-------");
			
			//如果mat[r][r]=0，则找一行r2>r的且mat[r2][r]!=0,并且交换这两行
			//如果没有找到r2，说明高斯消元不能进行到底。返回 null
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
						 * 如果r== Math.min(col_num, row_num)-1说明从r行开始到最后每一行都全为0，则说明消元提前结束。
						 */
						break;
					} else {
						/*
						 * 否则出现没有预料到的问题，还待研究解决
						 */
						System.err.println("高斯消元不能进行到底");
						return null;
					}
				} else {
					/*
					 * 交换r和r2两行
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
						System.err.println("出现中间不为0,-1,1的情况");
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
		 * 然后
		 */
		for(int r2 = r-1;r2>=0;r2--) {
			if(r2 < row_num - 1) {
				/*
				 * 如果不是消元的最后一行，则把下面的行的结果合到该行中。
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
			System.err.println("全为0...");
			return null;
		}
		/*
		 * 然后将 col_num - r 个数量的不确定未知量取0，1排列组合  
		 */
		System.out.println("unknown var:" + (col_num - r));
		
		/*
		 * 使用深度优先搜索得到所有排列组合
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
