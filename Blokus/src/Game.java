import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Game extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private class customButton extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private int x, y, linien = 1;

		private boolean taken = false;

		public customButton(String name, int x, int y) {
			// super(name);
			this.x = x;
			this.y = y;
		}

		public int[] getPos() {
			int[] pos = {this.x, this.y};
			return pos;
		}

		public boolean isTaken() {
			return this.taken;
		}

		public void setTaken(boolean taken) {
			this.taken = taken;
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int d = 0;
			int h = 0;
			for (int i = 0; i < this.linien; i++) {
				g.setColor(Color.gray);
				g.drawLine(0, h, getWidth(), h);
				g.drawLine(d, 0, d, getHeight());
				h += getHeight() / this.linien;
				d += getWidth() / this.linien;
			}
		}
	}

	private class shapeButton extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int index;

		public shapeButton(int i) {
			this.index = i;
		}

		public int getIndex() {
			return this.index;
		}
	}

	private int GRID_SIZE = 16, seconds;
	JLabel lblTimeLeft;
	private int[][] actions = {{0, 0}};
	private Dictionary<Object,Object> map;
	JPanel ssp1;Timer timer;
	private customButton[][] button = new customButton[GRID_SIZE][GRID_SIZE];
	private shapeButton[][][] SHAPE_LIST = new shapeButton[21][7][7];
	private int[][][] shapes = {
			{{0, 0}},

			{{0, 0}, {1, 0}},

			{{-1, 0}, {0, 0}, {1, 0}},
			{{-1, 0}, {0, 0}, {0, 1}},

			{{-2, 0}, {-1, 0}, {0, 0}, {1, 0}},
			{{-1, 0}, {0, 0}, {1, 0}, {1, 1}},
			{{-1, 0}, {0, 0}, {1, 0}, {0, -1}},
			{{0, 0}, {1, 0}, {0, -1}, {1, -1}},
			{{-1, 1}, {-1, 0}, {0, 0}, {0, -1}},

			{{-2, 0}, {-1, 0}, {0, 0}, {1, 0}, {2, 0}},
			{{-2, 0}, {-1, 0}, {0, 0}, {1, 0}, {1, 1}},
			{{-1, 1}, {-1, 0}, {0, 0}, {0, -1}, {0, -2}},
			{{-1, 0}, {0, 0}, {-1, -1}, {0, -1}, {0, -2}},
			{{1, -1}, {0, -1}, {1, 1}, {0, 1}, {0, 0}},//
			{{-1, 1}, {0, 1}, {0, 0}, {0, -1}, {1, -1}},
			{{-1, 1}, {-1, 0}, {0, 0}, {0, -1}, {1, -1}},
			{{-2, 0}, {-1, 0}, {0, 0}, {0, -1}, {0, -2}},
			{{-2, 0}, {-1, 0}, {0, 0}, {0, -1}, {0, 1}},
			{{-1, 1}, {0, 1}, {0, 0}, {1, 0}, {0, -1}},
			{{-1, 0}, {1, 0}, {0, 0}, {0, 1}, {0, -1}},
			{{-1, 0}, {0, -1}, {0, 0}, {1, 0}, {2, 0}}
	};

	private boolean isPlaceable(int x, int y, int[][] actions) {
		try {
			for (int i = 0; i < actions.length; i++) {
				if (button[x + actions[i][0]][y + actions[i][1]].isTaken()) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}


	private void hideShape(MouseEvent e) {
		int index = ((shapeButton) e.getSource()).getIndex();
		for (int j = 0; j < SHAPE_LIST[index].length; j++)
			for (int k = 0; k < SHAPE_LIST[index][j].length; k++) {
				SHAPE_LIST[index][j][k].setVisible(false);
			}
	}

	private void drawShapes() {
		for (int i = 0; i < SHAPE_LIST.length; i++)
			for (int j = 0; j < SHAPE_LIST.length; j++)
				for (int k = 0; k < SHAPE_LIST.length; k++) {
					if (j == 3 && k == 3) {
						for (int l = 0; l < shapes[i].length; l++) {
							SHAPE_LIST[i][shapes[i][l][1] + k][shapes[i][l][0] + j].setVisible(true);
						}
					}
					if ((j == 6 && k == 0) || (j == 6 && (k == 2 || k == 4)) || (j == 6 && k == 6)) {
						SHAPE_LIST[i][j][k].setVisible(true);
						SHAPE_LIST[i][j][k].setBackground(Color.white);
					}
				}
	}

	// flip horizontally
	private void flipH(int[][] actions) {
		for (int i = 0; i < actions.length; i++)
			actions[i][0] = -actions[i][0];
	}

	//flip vertically
	private void flipV(int[][] actions) {
		for (int i = 0; i < actions.length; i++)
			actions[i][1] = -actions[i][1];
	}
	//rotate clockwise

	private void rotateCoordinatesCW() {
		for (int i = 0; i < actions.length; i++) {
			if (actions[i][0] == 0) {
				if (actions[i][1] == 0) {
					actions[i][0] = 0;
					actions[i][1] = 0;
				} else if (actions[i][1] == 1) {
					actions[i][0] = -1;
					actions[i][1] = 0;
				} else if (actions[i][1] == -1) {
					actions[i][0] = 1;
					actions[i][1] = 0;
				} else if (actions[i][1] == 2) {
					actions[i][0] = -2;
					actions[i][1] = 0;
				} else if (actions[i][1] == -2) {
					actions[i][0] = 2;
					actions[i][1] = 0;
				}
			} else if (actions[i][0] == 1) {
				if (actions[i][1] == 0) {
					actions[i][0] = 0;
					actions[i][1] = 1;
				} else if (actions[i][1] == 1) {
					actions[i][0] = -1;
					actions[i][1] = 1;
				} else if (actions[i][1] == -1) {
					actions[i][0] = 1;
					actions[i][1] = 1;
				} else if (actions[i][1] == 2) {
					actions[i][0] = 1;
					actions[i][1] = 2;
				} else if (actions[i][1] == -2) {
					actions[i][0] = 1;
					actions[i][1] = 2;
				}
			} else if (actions[i][0] == -1) {
				if (actions[i][1] == 0) {
					actions[i][0] = 0;
					actions[i][1] = -1;
				} else if (actions[i][1] == 1) {
					actions[i][0] = -1;
					actions[i][1] = -1;
				} else if (actions[i][1] == -1) {
					actions[i][0] = 1;
					actions[i][1] = -1;
				} else if (actions[i][1] == 2) {
					actions[i][0] = -1;
					actions[i][1] = -2;
				} else if (actions[i][1] == -2) {
					actions[i][0] = 1;
					actions[i][1] = -2;
				}
			} else if (actions[i][0] == 2) {
				if (actions[i][1] == 0) {
					actions[i][0] = 0;
					actions[i][1] = 2;
				} else if (actions[i][1] == 1) {
					actions[i][0] = -2;
					actions[i][1] = 1;
				} else if (actions[i][1] == -1) {
					actions[i][0] = 2;
					actions[i][1] = 1;
				} else if (actions[i][1] == 2) {
					actions[i][0] = -2;
					actions[i][1] = 2;
				} else if (actions[i][1] == -2) {
					actions[i][0] = 2;
					actions[i][1] = 2;
				}
			} else if (actions[i][0] == -2) {
				if (actions[i][1] == 0) {
					actions[i][0] = 0;
					actions[i][1] = -2;
				} else if (actions[i][1] == 1) {
					actions[i][0] = -2;
					actions[i][1] = -1;
				} else if (actions[i][1] == -1) {
					actions[i][0] = 2;
					actions[i][1] = -1;
				} else if (actions[i][1] == 2) {
					actions[i][0] = -2;
					actions[i][1] = -2;
				} else if (actions[i][1] == -2) {
					actions[i][0] = 2;
					actions[i][1] = -2;
				}

			}
		}
	}

	private void rotateCoordinatesCCW() {
		rotateCoordinatesCW();
		for (int i = 0; i < actions.length; i++) {
			actions[i][0] = -actions[i][0];
			actions[i][1] = -actions[i][1];
		}
	}

	/**
	 * Create the panel.
	 *
	 * @param frame
	 */
	public Game(GUI frame)  {


		map = new Hashtable<Object, Object>();

		setBackground(Color.LIGHT_GRAY);
		frame.setBounds(0, 0, 709, 608);
		frame.setLocationRelativeTo(null);
		setBounds(0, 0, 709, 608);
		setLayout(null);

		JPanel SHAPES_LIST = new JPanel();
		SHAPES_LIST.setBounds(10, 114, 170, 241);
		SHAPES_LIST.setLayout(null);
		add(SHAPES_LIST);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setAutoscrolls(true);
		SHAPES_LIST.add(panel, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(8);
		scrollPane.setBounds(-1, -1, 190, 241);

		JPanel contentPane = new JPanel(null);
		contentPane.setBounds(-1, -1, 175, 241);
		contentPane.add(scrollPane);

		for (int i = 1; i <= 21; i++) {
			JSeparator separator = new JSeparator();
			separator.setBackground(Color.RED);
			separator.setPreferredSize(new Dimension(175, 5));
			separator.setBounds(-1, 0, 175, 10);

			JPanel sp1 = new JPanel();
			sp1.setLayout(new FlowLayout());
			sp1.setBackground(Color.WHITE);
			sp1.setPreferredSize(new Dimension(170, 170));

			ssp1 = new JPanel();
			ssp1.setLayout(new GridLayout(7, 7));
			ssp1.setBackground(Color.WHITE);
			ssp1.setPreferredSize(new Dimension(150, 150));

			// sp1.add(separator);
			for (int x = 0; x <= 6; x++) {
				for (int y = 0; y <= 6; y++) {
					shapeButton button = new shapeButton(i - 1);
					button.setPreferredSize(new Dimension(15, 15));
					if (x != 4 || y != 4)
						button.setVisible(false);
					if (!((x == 6 && y == 0) || (x == 6 && y == 6))) {
						button.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								actions = shapes[((shapeButton) e.getSource()).getIndex()];
							}
						});
					}
					if (x == 6 && y == 0) {
						BufferedImage img = null;
						try {
							img = ImageIO.read(new File("flipVertical.png"));
						} catch (IOException e) {
							e.printStackTrace();
						}
						Image dimg = img.getScaledInstance(15, 15,
								Image.SCALE_SMOOTH);
						JLabel label = new JLabel(new ImageIcon(dimg));
						label.setBounds(-10, -10, 14, 14);
						button.add(label);
						button.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								actions = shapes[((shapeButton) e.getSource()).getIndex()];
								flipV(actions);
								hideShape(e);
								drawShapes();
								ssp1.repaint();
								ssp1.revalidate();
							}
						});
					}
					if (x == 6 && y == 6) {
						BufferedImage img = null;
						try {
							img = ImageIO.read(new File("flipHorizontal.png"));
						} catch (IOException e) {
							e.printStackTrace();
						}
						Image dimg = img.getScaledInstance(15, 15,
								Image.SCALE_SMOOTH);
						JLabel label = new JLabel(new ImageIcon(dimg));
						label.setBounds(-10, -10, 15, 15);
						button.add(label);
						button.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								actions = shapes[((shapeButton) e.getSource()).getIndex()];
								flipH(actions);
								hideShape(e);
								drawShapes();
								ssp1.repaint();
								ssp1.revalidate();
							}
						});
					}
					if (x == 6 && y == 4) {
						//Setting up rotateCW
						BufferedImage img = null;
						try {
							img = ImageIO.read(new File("rotateClockWise.png"));
						} catch (IOException e) {
							e.printStackTrace();
						}
						Image dimg = img.getScaledInstance(17, 17,
								Image.SCALE_SMOOTH);
						JLabel label = new JLabel(new ImageIcon(dimg));
						label.setBounds(-10, -10, 15, 15);
						button.add(label);
						button.setBackground(Color.white);
						button.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {

								shapeButton thisButton = ((shapeButton) e.getSource());
								actions = shapes[thisButton.getIndex()];
								rotateCoordinatesCW();
								hideShape(e);
								drawShapes();
							}
						});
					}
					if (x == 6 && y == 2) {
						//Setting up rotateCW
						BufferedImage img = null;
						try {
							img = ImageIO.read(new File("rotateAntiClockWise.png"));
						} catch (IOException e) {
							e.printStackTrace();
						}
						Image dimg = img.getScaledInstance(17, 17,
								Image.SCALE_SMOOTH);
						JLabel label = new JLabel(new ImageIcon(dimg));
						label.setBounds(-10, -10, 15, 15);
						button.add(label);
						button.setBackground(Color.white);
						button.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {

								shapeButton thisButton = ((shapeButton) e.getSource());
								actions = shapes[thisButton.getIndex()];
								rotateCoordinatesCCW();
								hideShape(e);
								drawShapes();
							}
						});
					}
					button.setBorder(null);
					button.setVisible(false);
					button.setFocusable(false);
					button.setBackground(Color.red);
					SHAPE_LIST[i - 1][x][y] = button;

					ssp1.add(button);
				}
			}
			// ssp1.add(t1);
			if (i != 0)
				sp1.add(separator);

			sp1.add(ssp1);
			panel.add(sp1);

		}

		SHAPES_LIST.add(contentPane);
		drawShapes();

		JPanel surrender = new JPanel();
		surrender.setBounds(20, 366, 148, 40);
		add(surrender);
		surrender.setLayout(null);
		
		JLabel lblSurrender = new JLabel("Surrender");
		lblSurrender.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblSurrender.setBounds(36, 11, 77, 18);
		surrender.add(lblSurrender);

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

		JPanel GAME_BOARD = new JPanel();
		GAME_BOARD.setBounds(190, 89, 500, 500);
		add(GAME_BOARD);
		GAME_BOARD.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));

		for (int i = 0; i < GRID_SIZE; i++)
			for (int j = 0; j < GRID_SIZE; j++) {
				button[j][i] = new customButton("", j, i);
				button[j][i].setBackground(Color.white);
				button[j][i].setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
				button[j][i].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						customButton thisButton = ((customButton) e.getSource());
						int x = thisButton.getPos()[0];
						int y = thisButton.getPos()[1];
						try {
							for (int i = 0; i < actions.length; i++) {
								if (!button[x + actions[i][0]][y + actions[i][1]].isTaken()
										&& isPlaceable(x, y, actions)) {
									((customButton) e.getSource()).setBackground(Color.red);
									((customButton) e.getSource()).getPos();
									button[x + actions[i][0]][y + actions[i][1]].setBackground(Color.red);
									button[x + actions[i][0]][y + actions[i][1]].setTaken(true);
									thisButton.setTaken(true);
									for (int j = 0; j < actions.length; j++) {
										map.put(x + actions[j][0] + "_" + y + actions[j][1], true);
										button[x + actions[j][0]][y + actions[j][1]].setTaken(true);
									}

								}
							}
						} catch (Exception s) {
							button[x][y].setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
						}

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						customButton thisButton = ((customButton) e.getSource());
						int x = thisButton.getPos()[0];
						int y = thisButton.getPos()[1];
						try {
							for (int i = 0; i < actions.length; i++) {
								if (!button[x + actions[i][0]][y + actions[i][1]].isTaken()
										&& isPlaceable(x, y, actions)) {
									button[x + actions[i][0]][y + actions[i][1]].setBackground(Color.red);
								}
							}
						} catch (Exception s) {
							button[x][y].setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
						}

					}

					@Override
					public void mouseExited(MouseEvent e) {
						customButton thisButton = ((customButton) e.getSource());
						int x = thisButton.getPos()[0];
						int y = thisButton.getPos()[1];
						try {
							for (int i = 0; i < actions.length; i++) {
								if (isPlaceable(x, y, actions)
										&& !button[x + actions[i][0]][y + actions[i][1]].isTaken() && map.get(
										x + actions[i][0] + "_" + x + actions[i][1]) == null) {
									for (int j = 0; j < actions.length; j++) {
										button[x + actions[j][0]][y + actions[j][1]].setBackground(Color.white);
									}

								}
							}
						} catch (Exception s) {
							button[x][y].setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
						}

					}
				});
				GAME_BOARD.add(button[j][i]);

			}

		lblTimeLeft = new JLabel("Time left: 10:00");
		seconds=0;
		timer = new Timer(1000, new ActionListener() {

	        public void actionPerformed(ActionEvent e) {
	        	if (seconds>600)
	        		timer.stop();
	        	seconds+=1;
	        	lblTimeLeft.setText("Time left:"+( String.format("%02d", (600-seconds)/60))+":"+( String.format("%02d", (600-seconds)%60)));
	        	
	        }
	    });
	    timer.setRepeats(true);
	    timer.start();
		lblTimeLeft.setBounds(608, 64, 89, 14);
		add(lblTimeLeft);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		menuBar.setBounds(95, 11, 99, 22);s
		menuBar.setBorder(null);
		add(menuBar);
		
		JMenu mnOptions = new JMenu("Options");
		mnOptions.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		menuBar.add(mnOptions);
		
		JMenuItem mntmOption = new JMenuItem("Option1");
		mntmOption.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		mnOptions.add(mntmOption);

	}
}
