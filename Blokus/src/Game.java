import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JList;
import java.awt.GridBagLayout;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import java.awt.ScrollPane;



public class Game extends JPanel {
	private class customButton extends JButton{
		private int x,y;
		private boolean taken=false;
		public customButton(String name,int x,int y) {
			super(name);
			this.x=x;
			this.y=y;
		}
		public int[] getPos() {
			int[] pos= {this.x,this.y};
			return pos;
		}
		public boolean isTaken() {
			return this.taken;
		}
		public void setTaken(boolean taken) {
			this.taken=taken;
		}
	}
	private class shapeButton extends JButton{
		private int i;
		public shapeButton(int i) {
			this.i=i;
		}
		public int getIndex() {
			return this.i;
		}
	}
	private int GRID_SIZE= 20,currentButtonActionAdder;
	private int[][] actions={{0,0}};
	private customButton button[][]=new customButton[GRID_SIZE][GRID_SIZE];
	private shapeButton[][][] SHAPE_LIST=new shapeButton[21][5][5];
	private int[][][] shapes= {
			
			{{0,0}},
			
			{{0,0},{1,0}},
			
			{{-1,0},{0,0},{1,0}},
			{{-1,0},{0,0},{0,1}},
			
			{{-2,0},{-1,0},{0,0},{1,0}},
			{{-1,0},{0,0},{1,0},{1,1}},
			{{-1,0},{0,0},{1,0},{0,-1}},
			{{0,0},{1,0},{0,-1},{1,-1}},
			{{-1,1},{-1,0},{0,0},{0,-1}},
			
			
			{{-2,0},{-1,0},{0,0},{1,0},{2,0}},
			{{-2,0},{-1,0},{0,0},{1,0},{1,1}},
			{{-1,1},{-1,0},{0,0},{0,-1},{0,-2}},
			{{-1,0},{0,0},{-1,-1},{0,-1},{0,-2}},
			{{0,-2},{-1,-2},{-1,-1},{-1,0},{0,0}},
			{{-1,1},{0,1},{0,0},{0,-1},{1,-1}},
			{{-1,1},{-1,0},{0,0},{0,-1},{1,-1}},
			{{-2,0},{-1,0},{0,0},{0,-1},{0,-2}},
			{{-2,0},{-1,0},{0,0},{0,-1},{0,1}},
			{{-1,1},{0,1},{0,0},{1,0},{0,-1}},
			{{-1,0},{1,0},{0,0},{0,1},{0,-1}},
			{{-1,0},{0,-1},{0,0},{1,0},{2,0}},
	};

	/**
	 * Create the panel.
	 * @param frame 
	 */
	public Game(GUI frame) {
		setBackground(Color.LIGHT_GRAY);
		frame.setBounds(0, 0, 745, 720-200);
		frame.setLocationRelativeTo(null);
		setBounds(0, 0, 745, 520);
		setLayout(null);
				
		JPanel SHAPES_LIST = new JPanel();
		SHAPES_LIST.setBounds(10, 114, 170, 241);
		SHAPES_LIST.setLayout(null);
		add(SHAPES_LIST);
		
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAutoscrolls(true);
        SHAPES_LIST.add(panel,BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
        scrollPane.setBounds(-1, -1, 190, 241);

        JPanel contentPane = new JPanel(null);
        contentPane.setBounds(-1,-1,175,241);
        contentPane.add(scrollPane);
        
        ActionListener shapeSetter=e->{
        	actions=shapes[((shapeButton)e.getSource()).getIndex()];
        	System.out.println(Arrays.deepToString(actions));
        };

        for(int i = 1; i <= 21; i++) {
        	
        	currentButtonActionAdder=i-1;
        	JSeparator separator = new JSeparator();
            separator.setBackground(Color.RED);
    		separator.setPreferredSize(new Dimension(175, 5));
    		separator.setBounds(-1,0,175,10);
        	
        	
            JPanel sp1 = new JPanel();
            sp1.setLayout(new FlowLayout());
            sp1.setBackground(Color.WHITE);
            sp1.setPreferredSize(new Dimension(170, 170));

            JPanel ssp1 = new JPanel();
            ssp1.setLayout(new GridLayout(5,5));
            ssp1.setBackground(Color.WHITE);
            ssp1.setPreferredSize(new Dimension(150, 150));   		

            //sp1.add(separator);
            for (int x=0;x<5;x++) {
            	for (int y=0;y<5;y++) {
            		shapeButton button=new shapeButton(i-1);
                	button.setPreferredSize(new Dimension(15, 15));
                	if(x!=2||y!=2)button.setVisible(false);
                	ssp1.add(button);
                	button.addActionListener(shapeSetter);
                	SHAPE_LIST[i-1][x][y]=button;
                }
            }
            //ssp1.add(t1);
            if(i!=0)
            	sp1.add(separator);
            


            sp1.add(ssp1);
            panel.add(sp1);
            
        }
		
        SHAPES_LIST.add(contentPane);
        for (int i=0;i<SHAPE_LIST.length;i++)
        	for (int j=0;j<SHAPE_LIST.length;j++)
        		for (int k=0;k<SHAPE_LIST.length;k++) {
        			if (j==2&&k==2) {
        				for (int l=0;l<shapes[i].length;l++) {
        					SHAPE_LIST[i][shapes[i][l][1]+k][shapes[i][l][0]+j].setVisible(true);;
        				}
        			}
        		}
        
        
		JPanel surrender = new JPanel();
		surrender.setBounds(20, 366, 148, 40);
		add(surrender);
		
		JLabel lblScore = new JLabel("Score: 0");
		lblScore.setBounds(20, 478, 48, 14);
		add(lblScore);
		
		JLabel lblBlokus = new JLabel(" Blokus");
		lblBlokus.setFont(new Font("Century Gothic", Font.PLAIN, 25));
		lblBlokus.setBounds(0, 1, 82, 32);
		add(lblBlokus);
		
		JLabel lblTurn = new JLabel("Turn");
		lblTurn.setBounds(76, 89, 31, 14);
		add(lblTurn);
		
		JLabel lblOptions = new JLabel("Options");
		lblOptions.setBounds(92, 11, 48, 14);
		add(lblOptions);
		
		JPanel GAME_BOARD = new JPanel();
		GAME_BOARD.setBounds(190, 89, 545, 420);
		add(GAME_BOARD);
		GAME_BOARD.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
		
		
		for (int i=0;i<GRID_SIZE;i++)
			for (int j=0;j<GRID_SIZE;j++) {
				button[j][i] = new customButton("",j,i);
				button[j][i].setBackground(Color.white);
				button[j][i].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						customButton thisButton=((customButton)e.getSource());
						int x=thisButton.getPos()[0];
						int y=thisButton.getPos()[1];
//						if(!thisButton.isTaken()&&!button[x+1][y].isTaken()) {
//							((customButton)e.getSource()).setBackground(Color.red);
//							((customButton)e.getSource()).getPos();
//							
//							button[x+1][y].setBackground(Color.red);
//							button[x+1][y].setTaken(true);
//							thisButton.setTaken(true);
//						}
						for(int i=0;i<actions.length;i++) {
							if(!button[x+actions[i][0]][y+actions[i][1]].isTaken()) {
								((customButton)e.getSource()).setBackground(Color.red);
								((customButton)e.getSource()).getPos();
								button[x+actions[i][0]][y+actions[i][1]].setBackground(Color.red);
								button[x+actions[i][0]][y+actions[i][1]].setTaken(true);
								thisButton.setTaken(true);
							}
						}
						
					}
					@Override
					public void mouseEntered(MouseEvent e) {
						customButton thisButton=((customButton)e.getSource());
						int x=thisButton.getPos()[0];
						int y=thisButton.getPos()[1];
						for(int i=0;i<actions.length;i++) {
							if(!button[x+actions[i][0]][y+actions[i][1]].isTaken()) {
								button[x+actions[i][0]][y+actions[i][1]].setBackground(Color.red);
							}
						}
						
					}
					@Override
					public void mouseExited(MouseEvent e) {
						customButton thisButton=((customButton)e.getSource());
						int x=thisButton.getPos()[0];
						int y=thisButton.getPos()[1];
						for(int i=0;i<actions.length;i++) {
							if(!button[x+actions[i][0]][y+actions[i][1]].isTaken()) {
								button[x+actions[i][0]][y+actions[i][1]].setBackground(Color.white);
							}
						}
					}
				});
				GAME_BOARD.add(button[j][i]);
				
			}
		
		JLabel lblTimeLeft = new JLabel("Time left: 00:00");
		lblTimeLeft.setBounds(646, 64, 89, 14);
		add(lblTimeLeft);
		
		
	}
}
