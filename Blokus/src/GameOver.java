/*
  @author: Atul Mehla
 */

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * The type Game over.
 */
class GameOver extends JPanel {

    private final int[] playerScores = {0,0,0,0};
    private String winner = "";
    private int winnerIndex;

    /**
     * Instantiates a new Game over.
     *
     * @param placedBlocks the placed blocks
     * @param frame        the frame
     */
    GameOver(String[] placedBlocks, GUI frame) {
        calGameState(placedBlocks);
        findWinner();

        setBackground(new Color(63, 71, 204));
        setBounds(0, 0, 600, 600);
        setLayout(null);

        JLabel lblNewLabel = new JLabel("Game Over");
        lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 30));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setHorizontalAlignment(SwingConstants.LEADING);
        lblNewLabel.setBounds(130, 43, 189, 64);
        add(lblNewLabel);

        JLabel lblPlayerWins = new JLabel("This game is a draw.");
        if (!winner.equals("draw"))
            lblPlayerWins.setText(String.format("%s wins the game with %d points.", winner, playerScores[winnerIndex]));
        lblPlayerWins.setForeground(new Color(255, 255, 255));
        lblPlayerWins.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblPlayerWins.setHorizontalAlignment(SwingConstants.LEADING);
        lblPlayerWins.setBounds(130, 118, 300, 26);
        add(lblPlayerWins);

        JLabel lblWouldLikeTo = new JLabel("Would like to play again?");
        lblWouldLikeTo.setForeground(new Color(255, 255, 255));
        lblWouldLikeTo.setHorizontalAlignment(SwingConstants.LEADING);
        lblWouldLikeTo.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblWouldLikeTo.setBounds(130, 155, 212, 24);
        add(lblWouldLikeTo);

        JButton btnNewButton = new JButton("Yes");
        btnNewButton.addActionListener(e -> {
            GUI gui = new GUI();
            frame.setVisible(false);
            gui.main(null);
        });
        btnNewButton.setBounds(130, 190, 82, 36);
        btnNewButton.setBackground(new Color(255, 200, 0));
        add(btnNewButton);

        JButton btnNo = new JButton("No");
        btnNo.setBackground(new Color(255, 200, 0));
        btnNo.addActionListener(e -> System.exit(0));
        btnNo.setBounds(243, 190, 82, 36);
        add(btnNo);
    }

    /**
     * @param arr
     * @param t
     * @return
     */
    //taken from geeksforgeeks.org
    private static int findIndex(int[] arr, int t) {

        // if array is Null
        if (arr == null) {
            return -1;
        }

        // find length of array
        int len = arr.length;
        int i = 0;

        // traverse in the array
        while (i < len) {

            // if the i-th element is t
            // then return the index
            if (arr[i] == t) {
                return i;
            } else {
                i = i + 1;
            }
        }
        return -1;
    }

    private void calGameState(String[] placedBlocksArray) {
        for (int i = 0; i < placedBlocksArray.length; i++) {
            if (placedBlocksArray[i] != null)
                playerScores[i] = placedBlocksArray[i].length();
        }
    }

    /**
     *
     */
    private void findWinner() {
        int max = 0;
        for (int i : playerScores) {
            if (max < i) {
                max = i;
                winnerIndex = findIndex(playerScores, i);
                switch (winnerIndex) {
                    case 0:
                        winner = "Red";
                        break;
                    case 1:
                        winner = "Green";
                        break;
                    case 2:
                        winner = "Blue";
                        break;
                    case 3:
                        winner = "Yellow";
                        break;
                }
            } else if (max == i && max != 0) {
                winner = "draw";
                break;
            }
        }
    }
}
