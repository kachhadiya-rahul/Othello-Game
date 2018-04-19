package game;

public class GamePlay {
	
	char boardStatus[][];
	int wCount,bCount;
	static OthelloAI AI ;
	
	public GamePlay() {
		// TODO Auto-generated constructor stub
		boardStatus = new char[8][8];
		
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				boardStatus[i][j]='a';
			}
		}
		boardStatus[3][3]='w';
		boardStatus[3][4]='b';
		boardStatus[4][3]='b';
		boardStatus[4][4]='w';
		wCount=2;
		bCount=2;
		//display();
		
	}
	public void display() {
		for(int i=0;i<8;i++) {
			System.out.print("row : ");
			for(int j=0;j<8;j++) {
				System.out.print( (char)boardStatus[i][j] +  " " );
			}
			System.out.println();
		}
	}
	
	public GamePlay(char [][]board) {
		boardStatus = new char[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				this.boardStatus[i][j] = board[i][j];
			}
		}
		AI = new OthelloAI(board);
	}
	
	public  boolean isPlausible(int row,int col,char playerDice) {
		
		char oppDice='a';
		if(playerDice == 'w') {
			oppDice='b';
		}else if(playerDice=='b') {
			oppDice='w';
		}
		boolean flag = false;
		//check if near opposite player's dice
		if(row-1 >= 0) {
			if(col-1 >= 0) {
				if(boardStatus[row-1][col-1] == oppDice){
					flag=true;
				}	
			}
			if(boardStatus[row-1][col] == oppDice) {
				flag=true;
			}
			if(col+1 < 8) {
				if(boardStatus[row-1][col+1] == oppDice) {
					flag=true;
				}
			}
		}
		if(col-1 >=0) {
			if(boardStatus[row][col-1]==oppDice) {
				flag=true;
			}
		}
		if(col+1 < 8) {
			if(boardStatus[row][col+1] == oppDice) {
				flag = true;
			}
		}
		if(row+1 < 8) {
			if(col-1 >= 0) {
				if(boardStatus[row+1][col-1] == oppDice){
					flag=true;
				}	
			}
			if(boardStatus[row+1][col] == oppDice) {
				flag=true;
			}
			if(col+1 < 8) {
				if(boardStatus[row+1][col+1] == oppDice) {
					flag=true;
				}
			}
		}
		if(!flag) {
			System.out.println("not in proximity");
			return false;
		}
		
		//check row
		if(col <= 3) {
			int tempCol = col;
			tempCol++;
			while(tempCol <8 &&boardStatus[row][tempCol]==oppDice) {
				tempCol++;
			}
			if(tempCol <8 &&boardStatus[row][tempCol]==playerDice  && tempCol != col + 1) {
				//System.out.println("found in from left");
				return true;
			}
		}else if(col >=4 && col <8) {
			int tempCol = col;
			tempCol--;
			while(tempCol >=0 && boardStatus[row][tempCol]==oppDice) {
				tempCol--;
			}
			if(tempCol >=0 &&boardStatus[row][tempCol]==playerDice && tempCol != col - 1) {
				//System.out.println("found in row from right");
				return true;
			}
		}
		System.out.println("row failed");
		
		//check col
		if(row <= 3) {
			int temprow = row;
			temprow++;
			while(temprow <8  &&boardStatus[temprow][col]==oppDice) {
				temprow++;
			}
			if(temprow <8  &&boardStatus[temprow][col]==playerDice && temprow != row +1) {
				return true;
			}
		}else if(row >=4 && row <8) {
			int temprow = row;
			temprow--;
			while( temprow >=0 &&boardStatus[temprow][col]==oppDice) {
				//System.out.println("above");
				temprow--;
			}
			if(temprow >=0 && boardStatus[temprow][col]==playerDice && temprow != row-1) {
				return true;
			}
		}
		System.out.println("column failed");
		
		
		//check diagonals
		int temprow = row-1;
		int tempcol = col-1;
		flag=false;
		while(temprow >= 0 && tempcol >= 0 && boardStatus[temprow][tempcol] == oppDice) {
			flag=true;
			temprow--;
			tempcol--;
		}
		if(temprow >= 0 && tempcol >= 0 && boardStatus[temprow][tempcol] == playerDice && flag) {
			System.out.println("dia from left-top");
			return true;
		}
		flag=false;
		temprow = row-1;
		tempcol = col+1;
		while(temprow >= 0 && tempcol <8  && boardStatus[temprow][tempcol] == oppDice) {
			flag=true;
			temprow--;
			tempcol++;
		}
		if(temprow >= 0 && tempcol <8  && boardStatus[temprow][tempcol] == playerDice && flag) {
			System.out.println("dia from top-right");
			return true;
		}
		flag = false;
		temprow = row+1;
		tempcol = col+1;
		while(temprow < 8 && tempcol <8  && boardStatus[temprow][tempcol] == oppDice) {
			flag =true;
			temprow++;
			tempcol++;
		}
		if(temprow < 8 && tempcol <8  && boardStatus[temprow][tempcol] == playerDice && flag) {
			System.out.println("player dice is : "+playerDice);
			System.out.println("dia from bottom right");
			return true;
		}
		flag = false;
		temprow = row+1;
		tempcol = col-1;
		while(temprow < 8 && tempcol >=0  && boardStatus[temprow][tempcol] == oppDice) {
			flag = true;
			temprow++;
			tempcol--;
		}
		if(temprow < 8 && tempcol >= 0  && boardStatus[temprow][tempcol] == playerDice && flag) {
			System.out.println("dia from bottom-left");
			return true;
		}
		System.out.println("diagonal failed");
		
		return false;
	}
	
	//AI = new OthelloAI(boardStatus);
	//playAI(1, 1, 'w');
	public  boolean markMove(int row,int col,char playerDice) {
		
		char oppDice='a';
		if(playerDice == 'w') {
			oppDice = 'b';
		}else if(playerDice == 'b') {
			oppDice =  'w';
		}
		if(isPlausible(row, col, playerDice) && boardStatus[row][col] == 'a') {
			boardStatus[row][col] = playerDice;
			GameBoard.reflectChange(row, col, playerDice);
			
			//flipping the dices
			
			//flip col
			boolean flag = false;
			int top=0,bottom=7;
			while(boardStatus[top][col] != playerDice && top<row) {
				top++;
			}
			int temptop = top;
			while(temptop < row) {
				if(boardStatus[temptop][col]==oppDice) {
					flag=true;
				}
				if(boardStatus[temptop][col]== playerDice) {
					top = temptop;
				}
				temptop++;
			}
			if(top!=row-1 && flag) {
				for(int i=top;i<row;i++) {
					GameBoard.reflectChange(i, col, playerDice);
					boardStatus[i][col]= playerDice;
				}
			}
			flag = false;
			//bottopm of col
			while(boardStatus[bottom][col] != playerDice && bottom>row) {
				bottom--;
			}
			int tempbottom = bottom;
			while(tempbottom > row) {
				if(boardStatus[tempbottom][col]==oppDice) {
					flag=true;
				}
				if(boardStatus[tempbottom][col]== playerDice) {
					bottom = tempbottom;
				}
				tempbottom--;
			}
			if(bottom!=row+1 && flag) {
				for(int i=bottom;i>row;i--) {
					GameBoard.reflectChange(i, col, playerDice);
					boardStatus[i][col]= playerDice;
				}
			}
			
			//fliping row
			flag=false;
			int left=0,right=7;
			while(boardStatus[row][left] != playerDice && left<col) {
				left++;
			}
			int templeft = left;
			while(templeft < col) {
				if(boardStatus[row][templeft]==oppDice) {
					flag=true;
				}
				if(boardStatus[row][templeft]== playerDice) {
					left = templeft;
				}
				templeft++;
			}
			if(left!=col-1 && flag) {
				for(int i=left;i<col;i++) {
					GameBoard.reflectChange(row, i, playerDice);
					boardStatus[row][i]= playerDice;
				}
			}
			//right of col
			flag = false;
			while(boardStatus[row][right] != playerDice && right>col) {
				right--;
			}
			int tempright = right;
			while(tempright> col) {
				if(boardStatus[row][tempright]==oppDice) {
					flag=true;
				}
				if(boardStatus[row][tempright]== playerDice) {
					right = tempright;
				}
				tempright--;
			}
			if(right!=col+1 && flag) {
				for(int i=right;i>col;i--) {
					GameBoard.reflectChange(row, i, playerDice);
					boardStatus[row][i]= playerDice;
				}
			}
			flag = false;
			
			//marking diagonals
			
			//top left
			int tempcol = col-1;
			int temprow = row-1;
			flag=false;
			while(temprow >=0 && tempcol >=0 && boardStatus[temprow][tempcol] == oppDice  ) {
				
				tempcol--;
				temprow--;
			}
			if(temprow >=0 && tempcol >=0 && boardStatus[temprow][tempcol] == playerDice) {
				flag=true;
			}
			tempcol = col-1;
			temprow = row-1;
			while(temprow >=0 && tempcol >=0 && boardStatus[temprow][tempcol] == oppDice && flag ) {
				GameBoard.reflectChange(temprow, tempcol, playerDice);
				boardStatus[temprow][tempcol] = playerDice;
				tempcol--;
				temprow--;
			}
			
			//top right
			
			tempcol = col+1;
			temprow = row-1;
			flag=false;
			while( tempcol<8 && temprow>=0 && boardStatus[temprow][tempcol] == oppDice ) {
				
				tempcol++;
				temprow--;
			}
			if(tempcol<8 && temprow>=0 && boardStatus[temprow][tempcol] == playerDice) {
				flag=true;
			}
			tempcol = col+1;
			temprow = row-1;
			while( tempcol<8 && temprow>=0 && boardStatus[temprow][tempcol] == oppDice && flag ) {
				GameBoard.reflectChange(temprow, tempcol, playerDice);
				boardStatus[temprow][tempcol] = playerDice;
				tempcol++;
				temprow--;
			}
			
			//bottom right
			tempcol = col+1;
			temprow = row+1;
			flag=false;
			while(temprow<8 && tempcol<8 && boardStatus[temprow][tempcol] == oppDice  ) {
				
				tempcol++;
				temprow++;
			}
			if(temprow<8 && tempcol<8 && boardStatus[temprow][tempcol] == playerDice) {
				flag=true;
			}
			tempcol = col+1;
			temprow = row+1;
			while(temprow<8 && tempcol<8 && boardStatus[temprow][tempcol] == oppDice &&flag  ) {
				GameBoard.reflectChange(temprow, tempcol, playerDice);
				boardStatus[temprow][tempcol] = playerDice;
				tempcol++;
				temprow++;
			}
			
			//bottom left
			tempcol = col-1;
			temprow = row+1;
			flag=false;
			while( tempcol>=0 && temprow<8 && boardStatus[temprow][tempcol] == oppDice  ) {
				
				tempcol--;
				temprow++;
			}
			if( tempcol>=0 && temprow<8 && boardStatus[temprow][tempcol] == playerDice) {
				flag=true;
			}
			tempcol = col-1;
			temprow = row+1;
			while( tempcol>=0 && temprow<8 && boardStatus[temprow][tempcol] == oppDice  && flag ) {
				GameBoard.reflectChange(temprow, tempcol, playerDice);
				boardStatus[temprow][tempcol] = playerDice;
				tempcol--;
				temprow++;
			}
			
			
			System.out.println("player played");
			return true;
		}else {
			//System.out.println("move is not plausible");
			return false;
		}
	}
	
	//playAI(1, 1, 'w');
	public  boolean markChildMove(int row,int col,char playerDice) {
		
		char oppDice='a';
		if(playerDice == 'w') {
			oppDice = 'b';
		}else if(playerDice == 'b') {
			oppDice =  'w';
		}
		if(isPlausible(row, col, playerDice) && boardStatus[row][col] == 'a') {
			boardStatus[row][col] = playerDice;
			
			//flipping the dices
			
			//flip col
			boolean flag = false;
			int top=0,bottom=7;
			while(boardStatus[top][col] != playerDice && top<row) {
				top++;
			}
			int temptop = top;
			while(temptop < row) {
				if(boardStatus[temptop][col]==oppDice) {
					flag=true;
				}
				if(boardStatus[temptop][col]== playerDice) {
					top = temptop;
				}
				temptop++;
			}
			if(top!=row-1 && flag) {
				for(int i=top;i<row;i++) {
					
					boardStatus[i][col]= playerDice;
				}
			}
			flag = false;
			//bottopm of col
			while(boardStatus[bottom][col] != playerDice && bottom>row) {
				bottom--;
			}
			int tempbottom = bottom;
			while(tempbottom > row) {
				if(boardStatus[tempbottom][col]==oppDice) {
					flag=true;
				}
				if(boardStatus[tempbottom][col]== playerDice) {
					bottom = tempbottom;
				}
				tempbottom--;
			}
			if(bottom!=row+1 && flag) {
				for(int i=bottom;i>row;i--) {
					
					boardStatus[i][col]= playerDice;
				}
			}
			
			//fliping row
			flag=false;
			int left=0,right=7;
			while(boardStatus[row][left] != playerDice && left<col) {
				left++;
			}
			int templeft = left;
			while(templeft < col) {
				if(boardStatus[row][templeft]==oppDice) {
					flag=true;
				}
				if(boardStatus[row][templeft]== playerDice) {
					left = templeft;
				}
				templeft++;
			}
			if(left!=col-1 && flag) {
				for(int i=left;i<col;i++) {
					
					boardStatus[row][i]= playerDice;
				}
			}
			//right of col
			flag = false;
			while(boardStatus[row][right] != playerDice && right>col) {
				right--;
			}
			int tempright = right;
			while(tempright> col) {
				if(boardStatus[row][tempright]==oppDice) {
					flag=true;
				}
				if(boardStatus[row][tempright]== playerDice) {
					right = tempright;
				}
				tempright--;
			}
			if(right!=col+1 && flag) {
				for(int i=right;i>col;i--) {
					
					boardStatus[row][i]= playerDice;
				}
			}
			flag = false;
			
			//marking diagonals
			
			//top left
			int tempcol = col-1;
			int temprow = row-1;
			flag=false;
			while(temprow >=0 && tempcol >=0 && boardStatus[temprow][tempcol] == oppDice  ) {
				tempcol--;
				temprow--;
			}
			if(temprow >=0 && tempcol >=0 && boardStatus[temprow][tempcol] == playerDice  ) {
				flag=true;
			}
			tempcol = col-1;
			temprow = row-1;
			while(temprow >=0 && tempcol >=0 && boardStatus[temprow][tempcol] == oppDice && flag ) {

				boardStatus[temprow][tempcol] = playerDice;
				tempcol--;
				temprow--;
			}
			
			//top right
			tempcol = col+1;
			temprow = row-1;
			flag=false;
			while( tempcol<8 && temprow>=0 && boardStatus[temprow][tempcol] == oppDice ) {
				
				tempcol++;
				temprow--;
			}
			if(tempcol<8 && temprow>=0 && boardStatus[temprow][tempcol] == playerDice) {
				flag=true;
			}
			tempcol = col+1;
			temprow = row-1;
			while( tempcol<8 && temprow>=0 && boardStatus[temprow][tempcol] == oppDice && flag ) {
				
				boardStatus[temprow][tempcol] = playerDice;
				tempcol++;
				temprow--;
			}
			
			//bottom right
			tempcol = col+1;
			temprow = row+1;
			flag=false;
			while(temprow<8 && tempcol<8 && boardStatus[temprow][tempcol] == oppDice  ) {
				
				
				tempcol++;
				temprow++;
			}
			if(temprow<8 && tempcol<8 && boardStatus[temprow][tempcol] == playerDice) {
				flag=true;
			}
			tempcol = col+1;
			temprow = row+1;
			while(temprow<8 && tempcol<8 && boardStatus[temprow][tempcol] == oppDice  && flag ) {
				
				boardStatus[temprow][tempcol] = playerDice;
				tempcol++;
				temprow++;
			}
			
			//bottom left
			tempcol = col-1;
			temprow = row+1;
			flag=false;
			while( tempcol>=0 && temprow<8 && boardStatus[temprow][tempcol] == oppDice  ) {
				
				
				tempcol--;
				temprow++;
			}
			if(tempcol>=0 && temprow<8 && boardStatus[temprow][tempcol] == playerDice) {
				flag=true;
			}
			tempcol = col-1;
			temprow = row+1;
			while( tempcol>=0 && temprow<8 && boardStatus[temprow][tempcol] == oppDice && flag ) {
				
				boardStatus[temprow][tempcol] = playerDice;
				tempcol--;
				temprow++;
			}
			flag=false;
			
			
			//System.out.println("player played");
			return true;
		}else {
			//System.out.println("move is not plausible");
			return false;
		}
	}
	

	
	
	public void playAI(int row,int col,char playerDice,GamePlay gp) {
		playerDice = 'w';
		char oppDice = 'b';
		AI = new OthelloAI(boardStatus);
		
		AI.run(gp);		
	}
	

}
