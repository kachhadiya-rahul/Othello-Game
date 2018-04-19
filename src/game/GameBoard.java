package game;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

/*
  <applet code="GameBoard" width=800 height=800 >
  </applet>
 */



public class GameBoard extends JApplet {
	
	public static GameDice grid[][] = new GameDice[8][8];
	JMenu menu = new JMenu("Actions");
	JMenuItem item = new JMenuItem("Pass");
	JMenuBar menubar = new JMenuBar();
	
	
	public void init() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for (int i=0;i<8;i++) {
						for(int j=0;j<8;j++) {
							grid[i][j]= new GameDice(i,j);
						}
					}
					
					makeGUI();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//handle the gui drwaing
	ImageIcon blackdice = new ImageIcon("../blackdice.png");
	ImageIcon whitedice = new ImageIcon("../whitedice.png");
	protected void makeGUI() {
		
		this.setSize(650,650);		
		setLayout(new GridLayout(8, 8));
		//setBackground(Color.GREEN);
		
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				JLabel temp = grid[i][j].getContent();
				temp.setBorder(BorderFactory.createEtchedBorder());
				temp.setBackground(Color.GREEN);
				add(temp);
			}
		}
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GameDice.gp.playAI(1, 1, 'w', GameDice.gp);
			}
		});
		menu.add(item);
		menubar.add(menu);
		setJMenuBar(menubar);
		setVisible(true);
		
		
		//initialize the game 
		changeGrid(3,3,whitedice); 
		changeGrid(3,4,blackdice);
		changeGrid(4, 3, blackdice);
		changeGrid(4, 4, whitedice);
		
	}
	
	public void changeGrid(int row, int col,ImageIcon icon) {
		grid[row][col].setContent(icon);
		repaint();
		
	}
	
	public static void reflectChange(int row,int col,char playerDice) {
		ImageIcon blackdice = new ImageIcon("../blackdice.png");
		ImageIcon whitedice = new ImageIcon("../whitedice.png");
		if(playerDice == 'b') {
			grid[row][col].setContent(blackdice);
			//System.out.println("changes reflected");
		}else if(playerDice == 'w') {
			grid[row][col].setContent(whitedice);
			//System.out.println("changes reflected");
		}
	}

}


class GameDice{
	boolean enabled = false;
	JLabel jlabel;
	ImageIcon blackdice = new ImageIcon("../blackdice.png");
	int row,col;
	public static GamePlay gp = new GamePlay();
	
	GameDice(int row,int col){
		this.row = row;
		this.col= col;
		jlabel = new JLabel();
		jlabel.addMouseListener(new MouseAdapter() {        
			public void mouseClicked(MouseEvent me) {
				System.out.println("mouse clicked");
				if(gp.markMove(row, col, 'b')) {
					((JLabel)me.getSource()).setIcon(blackdice);
					
					OthelloAI AI = new OthelloAI(gp.boardStatus);
					gp.playAI(1, 1, 'w',gp);
					//gp.display();
					
				}
				
			}
		});
		
	}
	public JLabel getContent() {
		return this.jlabel;
	}
	public void setContent(ImageIcon icon) {
		//this.jlabel.setIcon(icon);
		this.jlabel.setIcon(icon);
	}
	
}
