package game;

import java.awt.List;
import java.util.ArrayList;

public class OthelloAI {
	
	char board[][];
	int MAX_DEPTH = 5;
	MinMax minmax;
	static int currlev;
	static int cnt=0;
	static ArrayList<Integer> array = new ArrayList<Integer>();
	
	public OthelloAI(char board[][]) {
		// TODO Auto-generated constructor stub
		this.board = new char[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				this.board[i][j]= board[i][j];
			}
		}
		minmax= new MinMax(this.board,"max");
		
		//temp.display();
		
		
	}
	
	public void run(GamePlay gp) {
		
		minmax.createTree(gp);
		//minmax.getHeuristic();
		MinMax temp = null;
		float max = Float.MIN_VALUE;
		for(MinMax a : this.minmax.tree.root.children) {
			float val = a.getHeuristic();
			//System.out.println(val);
			if(val > max) {
				max=val;
				System.out.println(max);
				temp = a;
			}else {
				System.out.print("-");
			}
		}
		int index = minmax.tree.root.children.indexOf(temp);
		int i = array.get(index*2);
		int j = array.get((index*2)+1);
		System.out.println(i+ " " + j);
		if(i >=0 && j>=0)
		{
			gp.markMove(i, j, 'w');
			array.clear();
		}
		
		//System.out.println("-------------------------------------------------------------------");
		//temp.displayNode();
		//gp.display();
	}

}

class MinMax{
	Tree tree;
	char [][] board;
	String mmax;
	
	public MinMax(char[][] board,String mmax) {
		// TODO Auto-generated constructor stub
		this.board = board;
		this.tree = new Tree(this.board);
		this.mmax=mmax;//max or min node
	}
	
	
	
	public void createChildren(GamePlay gp) {
		GamePlay gpp = new GamePlay(this.board);
		//this.display();
		boolean flag = false;
		char playerDice='a';
		if(mmax.equals("max")) {
			playerDice='w';
		}else {
			playerDice='b';
		}
		for(int i=0;i<8 && !flag;i++) {
			for(int j=0;j<8 && !flag;j++) {
				if(gpp.isPlausible(i, j, playerDice) && gpp.boardStatus[i][j]=='a' && !flag) {
					
					char [][]tempboard = new char[8][8];
					for(int k=0;k<8;k++) {
						for(int l=0;l<8;l++) {
							tempboard[k][l] = this.board[k][l];
						}
					}
					
					GamePlay gp1 = new GamePlay(tempboard);
					
					gp1.markChildMove(i, j, playerDice);
					//gp1.display();
					//System.out.println("---------------------");
					if(this.mmax.equals("max")) {
						OthelloAI.array.add(i);
						OthelloAI.array.add(j);
						this.tree.addChildren(new MinMax(gp1.boardStatus,"min"));
					}else {
						OthelloAI.array.add(i);
						OthelloAI.array.add(j);
						this.tree.addChildren(new MinMax(gp1.boardStatus,"max"));
					}
					
					//gp.markMove(i, j, 'w');
					//System.out.println("AI played");
					//gp.display();
					//flag = true;
					
				}
			}
			if(flag) break;
		}
		//display();
		
	}
	
	public void exploreBoard() {
		
	}
	public float getHeuristic() {
		MinMax minmax;
		float min = Float.MAX_VALUE;
		float max = Float.MIN_VALUE;
		if(this.tree.root.children.isEmpty()) {
			OthelloAI.cnt++;
			//display();
			int bcount=0,wcount=0;
			for(int i=0;i<8;i++) {
				for(int j=0;j<8;j++) {
					if(this.tree.root.data[i][j] == 'b') {
						bcount++;
					}else if(this.tree.root.data[i][j] == 'w'){
						wcount++;
					}
				}
			}
			
			//System.out.println("ratio is : "+(wcount/(float)bcount));
			return wcount/(float)bcount;
		}else {
			max=Float.MIN_VALUE;
			min=Float.MAX_VALUE;
			for(MinMax a:this.tree.root.children) {
				if(mmax.equals("max")) {
					float val = a.getHeuristic();
					if(max<val) {
						max=val;
					}
				}else {
					float val = a.getHeuristic();
					if(min>val) {
						min=val;
					}
				}
				//a.getHeuristic();
				//System.out.println();
			}
			if(mmax.equals("max")) {
				return max;
			}else {
				return min;
			}
		}
	}
	
	public void createTree(GamePlay gp) {
		
		if(OthelloAI.currlev == 4) {
			
		}else {
			createChildren(gp);
			OthelloAI.currlev++;
			for(MinMax a : this.tree.root.children) {
				a.createTree(gp);
			}
			OthelloAI.currlev--;
		}
		
		//displayTree();
		
	}
	public void display() {
		for(MinMax a: this.tree.root.children) {
			for(int i=0;i<8;i++) {
				for(int j=0;j<8;j++) {
					System.out.print(a.tree.root.data[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
		
		
	}
	
	public void displayNode() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				System.out.print(this.tree.root.data[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void displayTree() {
		if(this.tree.root.children.isEmpty()) {
			this.display();
		}else {
			for(MinMax a : (ArrayList<MinMax>) this.tree.root.children) {
				a.display();
			}
			this.display();
		}
	}
	
}

class Tree {
	Node root;
	
	Tree(char[][] data){
		root = new Node();
		root.data = data;
		root.children = new ArrayList<MinMax>();
	}
	
	
	
	public void addChildren(MinMax mmax) {
		//set char data beforehand
		root.children.add(mmax);
	}
	
	
	
}

class Node{
	
	char[][] data;
	ArrayList<MinMax> children;
	Node parent;
	
	Node(){
		
	}
	
	
	
}
