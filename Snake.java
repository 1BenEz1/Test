import java.util.*;
public class Snake{

	// Cloning 2d Arry

	public static void cloneLine(int[] arrA, int j, int[] arrB){ // cloning 1d array
		if( j == 0 ){
			arrB[j] = arrA[j];
		}
		else{
			arrB[j] = arrA[j];
			cloneLine(arrA, j-1, arrB);
		}
	}

	public static void cloneArr(int[][] arrA, int i, int[][] arrB){
		if(i==0){
			cloneLine(arrA[i], arrA[i].length-1, arrB[i]);
		}
		else{
			cloneLine(arrA[i], arrA[i].length-1, arrB[i]);
			cloneArr(arrA, i-1, arrB);
		}
	}

	// End of Cloning methods

	// Minimum positive value
	public static int getMin(int[]arr , int i, int min){

		if( i == arr.length-1 ){
			if( arr[i]>0 && min>0 ){
				if( arr[i]<min )
					min = arr[i];
			}

			if(  arr[i]>0 && min<0 )
				min = arr[i];

			if( arr[i]<0 && min<0 )
				min = 0;

			return min;
		}

		else{
			if( arr[i]>0 && min>0 ){
				if( arr[i]<min )
					min = arr[i];
			}

			if(  arr[i]>0 && min<0 )
				min = arr[i];

			return(getMin(arr, i+1, min));
		}
	}

	// Minimum diagonally sum
	public static int deadEnd( int[][] a,int[][] mat ,int i, int j, boolean uL , boolean uR, boolean dL, boolean dR ){
		
		uL = uR = dL = dR = false;
		cloneArr(a, a.length-1, mat);

		if( i-1 >= 0 ){ // Check if we can go up
			if( j-1 >= 0 && Math.abs(mat[i][j] - mat[i-1][j-1]) <= 1 ) { uL = true; } // Can Up Left
			//else{  uL = false; }

			if( j+1 < mat[i].length && Math.abs(mat[i][j] - mat[i-1][j+1]) <= 1 ) { uR = true; } // Can Up Right
			//else { uR = false; }
		}

		if( i+1 < mat.length ){ // Check if we can go down
			if( j-1 >= 0 && Math.abs(mat[i][j] - mat[i+1][j-1]) <= 1 ) { dL = true; } // Can Down Left
			//else{ dL = false; }

			if( j+1 < mat[i].length && Math.abs(mat[i][j] - mat[i+1][j+1]) <= 1 ) { dR = true; } // Can Down Right
			//else{ dR = false; }
		}

		int cellVal = mat[i][j];
		mat[i][j] = -1;

		int sumUL, sumUR, sumDL, sumDR;
		sumUL = sumUR = sumDL = sumDR = -1;

		if( !uL && !uR && !dL && !dR ){
			return cellVal;
		}

		else{
			int[][] b = new int[a.length][a[0].length];
			int[][] c = new int[a.length][a[0].length];
			int[][] d = new int[a.length][a[0].length];
			int[][] e = new int[a.length][a[0].length];

			if(uL){ sumUL = cellVal + deadEnd( mat, b, i-1, j-1, uL, uR, dL, false ); } // calculating UL route
			if(uR){ sumUR = cellVal + deadEnd( mat, c, i-1, j+1, uL, uR, false, dR ); } // calculating UR route
			if(dL){ sumDL = cellVal + deadEnd( mat, d, i+1, j-1, uL, false, dL, dR ); } // calculating DL route
			if(dR){ sumDR = cellVal + deadEnd( mat, e, i+1, j+1, false, uR, dL, dR ); } // calculating DR route

			int[] minArr = { sumUL , sumUR , sumDL , sumDR };
			int min = getMin( minArr, 0, minArr[0]); // lowest positive route

			return(min);

		}

	}

	public static int minSumDiagDeadEndSnake(int[][] mat){
		int[][] clone = new int[mat.length][mat[0].length];
		int min = deadEnd(mat,clone,0,0,false,false,false,false);
		return min;
	}

	public static void main(String[]args){
		int[][] mat = {
						{4,3,6,7,1},
						{3,5,1,7,4},
						{4,5,6,7,8},
						{3,4,5,7,9},
						{3,2,2,7,6}
						};

		int min = minSumDiagDeadEndSnake(mat);

		System.out.println(min);

		for(int i=0; i<mat.length; i++){
			for(int j=0; j<mat[i].length;j++){
				System.out.print(mat[i][j]+" ");
				if(j==mat[i].length-1)
					System.out.println();
			}
		}
	}

}