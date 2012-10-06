
public class Solver {
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
	public static int[] solve(int[][] mat) {
		if(mat.length==0){
			return null;
		}
		
		print(mat);
		
		int[] rtn = new int[mat[0].length];
		
		/**
		 * 首先使用高斯消元法对矩阵进行初等变化转为上三角矩阵
		 */
		int row_num = mat.length, col_num = mat[0].length;
		for(int r=0;r<row_num;r++) {
			
			//System.out.println("-----row:"+ r +"-------");
			
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
					if(r==col_num-1) {
						/**
						 * 如果r==col_num-1说明从r行开始到最后每一行都全为0，则说明消元提前结束。
						 */
						break;
					} else {
						/**
						 * 否则出现没有预料到的问题，还待研究解决
						 */
						System.err.println("高斯消元不能进行到底");
						return null;
					}
				} else {
					/**
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
			
			//print(mat);
		}
		
		
		
		System.out.println("--------------");
		
		print(mat);
		
		return rtn;
		
	}
}
