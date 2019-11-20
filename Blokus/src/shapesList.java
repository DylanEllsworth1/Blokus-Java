/*
  @author: Atul Mehla
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
class shapesList extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final JPanel[] shapesPanelContainer = new JPanel[21];
    private final shapeButton[][][] SHAPE_LIST = new shapeButton[21][7][7];
    private final int[][][] shapes = {
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
            {{1, -1}, {0, -1}, {1, 1}, {0, 1}, {0, 0}},
            {{-1, 1}, {0, 1}, {0, 0}, {0, -1}, {1, -1}},
            {{-1, 1}, {-1, 0}, {0, 0}, {0, -1}, {1, -1}},
            {{-2, 0}, {-1, 0}, {0, 0}, {0, -1}, {0, -2}},
            {{-2, 0}, {-1, 0}, {0, 0}, {0, -1}, {0, 1}},
            {{-1, 1}, {0, 1}, {0, 0}, {1, 0}, {0, -1}},
            {{-1, 0}, {1, 0}, {0, 0}, {0, 1}, {0, -1}},
            {{-1, 0}, {0, -1}, {0, 0}, {1, 0}, {2, 0}}
    };
    private int currentSelectedShapePanel = 0;
    private MouseEvent event;
    private int[][] actions = {{0, 0}};
    private GameBoard playingAtBoard;
    private JPanel ssp1;


    /**
     * Create the panel.
     * shapeList Constructor
     */
    shapesList(Color color) {
        setLayout(null);

        ImageManager.setUpImages();

        // creating shape panel
        JPanel SHAPES_LIST = new JPanel();
        SHAPES_LIST.setBounds(0, 0, 220, 433);
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
        scrollPane.setBounds(-1, -1, 190, 433);

        JPanel contentPane = new JPanel(null);
        contentPane.setBounds(-1, -1, 195, 433);
        contentPane.add(scrollPane);

        for (int i = 1; i <= 21; i++) {
            JSeparator separator = new JSeparator();
            separator.setBackground(Color.RED);
            separator.setPreferredSize(new Dimension(175, 5));
            separator.setBounds(-1, 0, 175, 10);

            shapesPanelContainer[i - 1] = new JPanel();
            shapesPanelContainer[i - 1].setLayout(new FlowLayout());
            shapesPanelContainer[i - 1].setBackground(Color.WHITE);
            shapesPanelContainer[i - 1].setPreferredSize(new Dimension(170, 170));

            ssp1 = new JPanel();
            ssp1.setLayout(new GridLayout(7, 7));
            ssp1.setBackground(Color.WHITE);
            ssp1.setPreferredSize(new Dimension(150, 150));

            // shapesPanelContainer.add(separator);
            for (int x = 0; x <= 6; x++) {
                for (int y = 0; y <= 6; y++) {
                    shapeButton button = new shapeButton(i - 1);
                    button.setPreferredSize(new Dimension(15, 15));
                    if (x != 4 || y != 4)
                        button.setVisible(false);
                    if (!((x == 6 && y == 0) || (x == 6 && y == 6) || (x == 6 && y == 4) || (x == 6 && y == 2))) {
                        button.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                AudioManager.playSelected();
                                event = e;
                                shapeButton thisButton = ((shapeButton) e.getSource());
                                actions = shapes[thisButton.getIndex()];
                                currentSelectedShapePanel = thisButton.getIndex();
                                playingAtBoard.setActions(actions);
                            }
                        });
                    }
                    if (x == 6 && y == 0) {
                        JLabel label = new JLabel(ImageManager.getFlipVerticalPNG());
                        label.setBounds(-10, -10, 14, 14);
                        button.add(label);
                        button.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                shapeButton thisButton = ((shapeButton) e.getSource());
                                actions = shapes[thisButton.getIndex()];
                                currentSelectedShapePanel = thisButton.getIndex();
                                flipV(actions);
                                hideShape(e);
                                drawShapes();
                                ssp1.repaint();
                                ssp1.revalidate();
                                playingAtBoard.setActions(actions);

                            }
                        });
                    }
                    if (x == 6 && y == 6) {
                        JLabel label = new JLabel(ImageManager.getFlipHorizontalPNG());
                        label.setBounds(-10, -10, 15, 15);
                        button.add(label);
                        button.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                shapeButton thisButton = ((shapeButton) e.getSource());
                                actions = shapes[thisButton.getIndex()];
                                currentSelectedShapePanel = thisButton.getIndex();
                                flipH(actions);
                                hideShape(e);
                                drawShapes();
                                ssp1.repaint();
                                ssp1.revalidate();
                                playingAtBoard.setActions(actions);
                            }
                        });
                    }
                    if (x == 6 && y == 4) {
                        //Setting up rotateCW
                        JLabel label = new JLabel(ImageManager.getRotateClockWisePNG());
                        label.setBounds(-10, -10, 15, 15);
                        button.add(label);
                        button.setBackground(Color.white);
                        button.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                shapeButton thisButton = ((shapeButton) e.getSource());
                                actions = shapes[thisButton.getIndex()];
                                currentSelectedShapePanel = thisButton.getIndex();
                                rotateCoordinatesCW();
                                hideShape(e);
                                drawShapes();
                                playingAtBoard.setActions(actions);
                            }
                        });
                    }
                    if (x == 6 && y == 2) {
                        //Setting up rotateCW
                        JLabel label = new JLabel(ImageManager.getRotateAntiClockWisePNG());
                        label.setBounds(-10, -10, 15, 15);
                        button.add(label);
                        button.setBackground(Color.white);
                        button.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                shapeButton thisButton = ((shapeButton) e.getSource());
                                actions = shapes[thisButton.getIndex()];
                                currentSelectedShapePanel = thisButton.getIndex();
                                rotateCoordinatesCCW();
                                hideShape(e);
                                drawShapes();
                                playingAtBoard.setActions(actions);
                            }
                        });
                    }
                    button.setBorder(null);
                    button.setVisible(false);
                    button.setFocusable(false);
                    button.setBackground(color);
                    SHAPE_LIST[i - 1][x][y] = button;

                    ssp1.add(button);
                }
            }

            // ssp1.add(t1);
            shapesPanelContainer[i - 1].add(separator);

            shapesPanelContainer[i - 1].add(ssp1);
            panel.add(shapesPanelContainer[i - 1]);
        }
        drawShapes();
        SHAPES_LIST.add(contentPane);
    }

    private void hideShapePanel() {
        shapesPanelContainer[currentSelectedShapePanel].setVisible(false);
    }


    public void removePanel() {
        hideShapePanel();
    }

    void hideShapePanelFromIndex(int x) {
        shapesPanelContainer[x].setVisible(false);
    }

    int[][] getShapeFromIndex(int i) {
        currentSelectedShapePanel = i;
        actions = this.shapes[i];
        return this.shapes[i];
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

    public void rotateClockWise() {
        rotateCoordinatesCW();
    }

    void flipForAI() {
        flipV(actions);
    }

    //rotate clockwise
    private void rotateCoordinatesCW() {

        // take coordinates and rotate them
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
        // rotate coordinates clock wise and negate all the coordinates to inverse them
        // this implements the functionality of counter clockwise rotaton
        for (int i = 0; i < actions.length; i++) {
            actions[i][0] = -actions[i][0];
            actions[i][1] = -actions[i][1];
        }
    }

    // hides all the buttons of a shape
    private void hideShape(MouseEvent e) {
        int index = ((shapeButton) e.getSource()).getIndex();
        for (int j = 0; j < SHAPE_LIST[index].length; j++)
            for (int k = 0; k < SHAPE_LIST[index][j].length; k++) {
                SHAPE_LIST[index][j][k].setVisible(false);
            }
    }

    //Sets the reference to the board being played at
    public void setPlayingAtBoard(GameBoard playingAtBoard) {
        this.playingAtBoard = playingAtBoard;
    }

    GameBoard getPlayingAtBoard() {
        return playingAtBoard;
    }

    // draw the shapes shapes according to the current action
    private void drawShapes() {
        for (int i = 0; i < SHAPE_LIST.length; i++)
            for (int j = 0; j < SHAPE_LIST.length; j++)
                for (int k = 0; k < SHAPE_LIST.length; k++) {
                    if (j == 3 && k == 3) {
                        for (int l = 0; l < shapes[i].length; l++) {
                            SHAPE_LIST[i][shapes[i][l][1] + k][shapes[i][l][0] + j].setVisible(true);
                        }
                    } else if ((j == 6 && k == 0) || (j == 6 && (k == 2 || k == 4)) || (j == 6 && k == 6)) {
                        SHAPE_LIST[i][j][k].setVisible(true);
                        SHAPE_LIST[i][j][k].setBackground(Color.white);
                    }
                }
    }

    int getCurrentSelectedShapePanel() {
        return currentSelectedShapePanel;
    }

    public void scrollActionHide() {
        try {
            hideShape(event);
        } catch (Exception ignored) {

        }
    }

    public void scrollActionDraw() {
        drawShapes();
    }

    public void rightClickFlipH() {
        flipH(actions);
        hideShape(event);
        drawShapes();
    }

    public void rightClickFlipV() {
        flipV(actions);
        hideShape(event);
        drawShapes();
    }

    public String getColorName() {
        Color color = getColor();
        if (Color.red.toString().equals(color.toString()))
            return "Red";
        else if (Color.green.toString().equals(color.toString()))
            return "Green";
        else if (Color.blue.toString().equals(color.toString()))
            return "Blue";
        else if (Color.orange.toString().equals(color.toString()))
            return "Yellow";
        return "";
    }


    //left for AI
    public void doAction() {
    }

    public String getPlacedShapesIndexes() {
        return "";
    }

    // Methods to be overriden in children classes
    public boolean hasTakenCorner() {
        return true;
    }

    public Color getColor() {
        return Color.white;
    }

    public void setHasTakenCorner(boolean b) {
    }

    public void addPlacedShapeIndex() {
    }

    public boolean isStillPlaying() {
        return false;
    }

    public void setStillPlaying(boolean stillPlaying) {
    }

    public void removePanelsBasedOnString(String s) {
    }
}
