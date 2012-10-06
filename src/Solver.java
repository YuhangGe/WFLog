
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
		 * ����ʹ�ø�˹��Ԫ���Ծ�����г��ȱ仯תΪ�����Ǿ���
		 */
		int row_num = mat.length, col_num = mat[0].length;
		for(int r=0;r<row_num;r++) {
			
			//System.out.println("-----row:"+ r +"-------");
			
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
					if(r==col_num-1) {
						/**
						 * ���r==col_num-1˵����r�п�ʼ�����ÿһ�ж�ȫΪ0����˵����Ԫ��ǰ������
						 */
						break;
					} else {
						/**
						 * �������û��Ԥ�ϵ������⣬�����о����
						 */
						System.err.println("��˹��Ԫ���ܽ��е���");
						return null;
					}
				} else {
					/**
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
			
			//print(mat);
		}
		
		
		
		System.out.println("--------------");
		
		print(mat);
		
		return rtn;
		
	}
}
