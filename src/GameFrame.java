import javax.swing.*;

public class GameFrame extends JFrame {
    /**
     * Constructs and initializes a game frame
     * add - adding  game Panel
     * setTitle - set title of the frame
     * setDefaultCloseOperation - operation that occurs when the user initiates a "close" on this frame
     * setResizable - resize the frame
     * pack - causes this Window to be sized to fit the preferred size and layouts of its subcomponents
     * setVisible - set visibility of the frame
     * setLocationRelativeTo - set frame in the center of screen
     */
    GameFrame(){
        GamePanel gamePanel = new GamePanel();
        this.add(gamePanel);
        this.setTitle("SnakeGame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

}
